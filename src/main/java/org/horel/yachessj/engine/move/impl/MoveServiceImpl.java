package org.horel.yachessj.engine.move.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.MoveType;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Board;
import org.horel.yachessj.engine.model.Move;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.Piece;
import org.horel.yachessj.engine.move.MoveService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.horel.yachessj.engine.constants.Constants.FIRST_RANK;
import static org.horel.yachessj.engine.constants.Constants.LAST_RANK;
import static org.horel.yachessj.engine.constants.Constants.BISHOP_MOVE_DIRECTIONS;
import static org.horel.yachessj.engine.constants.Constants.QUEEN_MOVE_DIRECTIONS;
import static org.horel.yachessj.engine.constants.Constants.ROOK_MOVE_DIRECTIONS;

@Service
public class MoveServiceImpl implements MoveService {
    // TODO review move resolution logic
    @Override
    public Map<Piece, List<Move>> generateLegalMoves(Board board) {
        Map<Piece, List<Move>> legalMovesByPiece = new HashMap<>();

        board.getAllPieces()
                .forEach(piece -> {
                    List<Position> legalPositions = generateLegalMovesPositions(board, piece);
                    List<Move> moves = legalPositions.stream()
                            .map(posTo -> new Move(
                                    piece.getPosition(),
                                    posTo,
                                    piece,
                                    board.getPiece(posTo),
                                    defineMoveType(piece, board, posTo)
                            )).toList();
                    legalMovesByPiece.put(piece, moves);
                });

        return legalMovesByPiece;
    }

    private List<Position> generateLegalMovesPositions(Board board, Piece piece) {
        if (piece.getType().isSliding()){
            return generateSlidingLegalMovesPositions(board, piece);
        } else {
            return generateJumpingLegalMovesPositions(board, piece);
        }
    }

    private List<Position> generateSlidingLegalMovesPositions(Board board, Piece piece) {
        List<Position> theoretical = piece.getTheoreticalMovesPositions();
        filterBlockedPositions(board, piece, theoretical);
        return theoretical.stream()
                .filter(posTo -> leavesKingInCheck(board, piece, posTo))
                .toList();
    }

    private List<Position> generateJumpingLegalMovesPositions(Board board, Piece piece) {
        List<Position> theoretical = piece.getTheoreticalMovesPositions();
        return theoretical.stream()
                .filter(posTo -> isOccupiedByOwnPiece(board, piece, posTo))
                .filter(posTo -> leavesKingInCheck(board, piece, posTo))
                .toList();
    }

    private boolean isOccupiedByOwnPiece(Board board, Piece piece, Position pos) {
        Piece target = board.getPiece(pos);
        return target != null && target.getColor() == piece.getColor();
    }

    private void filterBlockedPositions(Board board, Piece piece, List<Position> theoretical) {
        int[][] directions = switch (piece.getType()){
            case BISHOP -> BISHOP_MOVE_DIRECTIONS;
            case ROOK -> ROOK_MOVE_DIRECTIONS;
            case QUEEN -> QUEEN_MOVE_DIRECTIONS;
            default -> throw new IllegalStateException("Unexpected piece type for a sliding piece: " + piece.getType());
        };

        for (int[] dir : directions) {
            Position current = piece.getPosition();
            boolean blocked = false;
            for (int i = 1; i < 8; i++) {
                current = new Position((char)(current.file()+dir[0]), current.rank()+dir[1]);
                if (!current.isValid()){
                    break;
                }
                if (blocked) {
                    theoretical.remove(current);
                }
                Piece currentPiece = board.getPiece(current);
                if (currentPiece != null){
                    blocked = true;
                    if(currentPiece.getColor() == piece.getColor()){
                        theoretical.remove(current);
                    }
                }
            }
        }
    }

    private boolean leavesKingInCheck(Board board, Piece piece, Position posTo) {
        Board boardCopy = board.copy();
        Piece pieceCopy = boardCopy.getPiece(piece.getPosition());
        boardCopy.movePiece(pieceCopy.getPosition(), posTo);
        return isKingInCheck(boardCopy, piece.getColor());
    }

    private boolean isKingInCheck(Board board, Color color) {
        Position kingPos = board.getPieceSets().get(color).getKing().getPosition();

        return board.getPieceSets().get(color.opposite())
                .getAllPieces().stream()
                .anyMatch(piece -> attacksSquare(board, piece, kingPos));
    }

    private boolean attacksSquare(Board board, Piece piece, Position target) {
        PieceType type = piece.getType();
        if (type == PieceType.PAWN) {
            return pawnAttacksSquare(piece, target);
        }
        if (type == PieceType.KING) {
            return piece.getTheoreticalMovesPositions().contains(target);
        }
        if (type == PieceType.KNIGHT) {
            return piece.getTheoreticalMovesPositions().contains(target);
        }
        return slidingAttacksSquare(board, piece, target);
    }

    private boolean pawnAttacksSquare(Piece pawn, Position target) {
        int direction = pawn.getColor() == Color.WHITE ? 1 : -1;
        return target.rank() == pawn.getPosition().rank() + direction
                && Math.abs(target.file() - pawn.getPosition().file()) == 1;
    }

    private boolean slidingAttacksSquare(Board board, Piece piece, Position target) {
        int[][] directions = switch (piece.getType()){
            case BISHOP -> BISHOP_MOVE_DIRECTIONS;
            case ROOK -> ROOK_MOVE_DIRECTIONS;
            case QUEEN -> QUEEN_MOVE_DIRECTIONS;
            default -> throw new IllegalStateException("Unexpected piece type for a sliding piece: " + piece.getType());
        };

        for (int[] dir : directions) {
            Position current = piece.getPosition();
            for (int i = 1; i < 8; i++) {
                current = new Position((char)(current.file()+dir[0]), current.rank()+dir[1]);
                if (!current.isValid() || board.getPiece(current) != null) {
                    break;
                }
                if (current.equals(target)) {
                    return true;
                }
            }
        }
        return false;
    }

    private MoveType defineMoveType(Piece piece, Board board, Position posTo) {
        Piece target = board.getPiece(posTo);

        if (piece.getType() == PieceType.PAWN && (posTo.rank() == FIRST_RANK || posTo.rank() == LAST_RANK)) {
            return MoveType.PROMOTION;
        }

        // TODO: en passant
        // TODO: castling

        if (target != null && target.getColor() != piece.getColor()) {
            return MoveType.CAPTURE;
        }

        return MoveType.NORMAL;
    }
}
