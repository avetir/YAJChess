package org.horel.yajchess.engine.model;

import org.horel.yajchess.engine.constants.Castling;
import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.pieces.MoveStrategy;
import org.horel.yajchess.engine.model.pieces.MoveStrategyRegistry;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.PieceRegistry;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;

import java.util.function.Consumer;

import static org.horel.yajchess.engine.constants.Constants.*;

public class Board{
    final Piece[][] squares;
    Position whiteKingPos = PositionPool.get('e', 1);
    Position blackKingPos = PositionPool.get('e', 8);
    Position enPassantTarget = null;
    private int castlingRights = Castling.ALL;
    
    private Board(){
        this.squares = new Piece[8][8];
    }
    
    public static Board newBoard(){
        return new Board();
    }
    
    public void placeInitialPieces(){
        PieceRegistry.assembleStartingPosition().forEach(this::placePiece);
    }
    
    public Piece[][] getSquares(){
        return this.squares;
    }
    
    public Position getKingPos(Color c){
        return c == Color.WHITE
                ? whiteKingPos
                : blackKingPos;
    }
    
    public Position getEnPassantTarget() {
        return enPassantTarget;
    }
    
    public boolean isEmpty(Position pos){
        return pos != null && getPiece(pos) == null;
    }
    
    public boolean isOccupiedByOwnPiece(Color color, Position posTo){
        Piece targetPiece = posTo == null ? null : getPiece(posTo);
        return targetPiece != null && targetPiece.getColor() == color;
    }
    
    public boolean isOccupiedByEnemyPiece(Color color, Position posTo){
        Piece targetPiece = posTo == null ? null : getPiece(posTo);
        return targetPiece != null && targetPiece.getColor() != color;
    }
    
    public Piece getPiece(Position pos){
        return squares[pos.file() - 'a'][pos.rank() - 1];
    }
    
    public Piece requirePiece(Position from) {
        Piece p = getPiece(from);
        if (p == null) throw new IllegalArgumentException("No piece at " + from);
        return p;
    }
    
    public void placePiece(Position pos, Piece piece){
        squares[pos.file() - 'a'][pos.rank() - 1] = piece;
    }
    
    public void removePiece(Position pos){
        squares[pos.file() - 'a'][pos.rank() - 1] = null;
    }
    
    public void movePiece(Position from, Position to){
        Piece pFrom = getPiece(from);
        Piece pTo = getPiece(to);
        placePiece(to, pFrom);
        removePiece(from);
        
        if(pFrom.getType() == PieceType.KING){
            if(pFrom.getColor() == Color.WHITE){
                whiteKingPos = to;
            }else{
                blackKingPos = to;
            }
        }
        
        revokeCastlingRights(pFrom.getColor(), pFrom.getType(), from);
        if(pTo != null && pTo.getType() == PieceType.ROOK){
            revokeCastlingRightsOnCapture(pTo.getColor(), to);
        }
    }
    
    public void castle(Color color, boolean kingSide){
        int rank = color == Color.WHITE ? 1 : 8;
        
        Position kingFrom = color == Color.WHITE ? whiteKingPos : blackKingPos;
        Position kingTo = PositionPool.get(kingSide ? 'g' : 'c', rank);
        
        Position rookFrom = PositionPool.get(kingSide ? 'h' : 'a', rank);
        Position rookTo = PositionPool.get(kingSide ? 'f' : 'd', rank);
        
        movePiece(kingFrom, kingTo);
        movePiece(rookFrom, rookTo);
    }
    
    public void forEachPiece(Consumer<Position> consumer){
        for(char f = 'a'; f <= 'h'; f++){
            for(int r = 1; r <= 8; r++){
                Piece p = squares[f - 'a'][r - 1];
                if(p != null){
                    consumer.accept(PositionPool.get(f, r));
                }
            }
        }
    }
    
    public void forEachPiece(Color c, Consumer<Position> consumer){
        forEachPiece((pos) -> {
            Piece p = getPiece(pos);
            if(p.getColor() == c){
                consumer.accept(pos);
            }
        });
    }
    
    public boolean canCastleKingSide(Color c){
        int rank  = (c == Color.WHITE) ? WHITE_PIECE_RANK : BLACK_PIECE_RANK;
        
        if (!hasCastlingRight(c, Castling.WHITE_KINGSIDE, Castling.BLACK_KINGSIDE) || isInCheck(c)){
            return false;
        }
        
        Piece theRook = getPiece(PositionPool.get(KING_ROOK_FILE, rank));
        Piece king = getPiece(c == Color.WHITE ? whiteKingPos : blackKingPos);
        if (theRook == null || theRook.getType() != PieceType.ROOK || theRook.getColor() != c ||
                king == null || king.getType() != PieceType.KING || king.getColor() != c){
            return false;
        }
        
        Position fFile = PositionPool.get(KING_BISHOP_FILE, rank);
        Position gFile = PositionPool.get(KING_KNIGHT_FILE, rank);
        if (!isEmpty(fFile) || !isEmpty(gFile)){
            return false;
        }
        
        Color opp = c.opposite();
        if (isSquareAttacked(opp, fFile) || isSquareAttacked(opp, gFile)){
            return false;
        }
        
        return true;
    }
    
    public boolean canCastleQueenSide(Color c){
        int rank = c == Color.WHITE ? WHITE_PIECE_RANK : BLACK_PIECE_RANK;
        
        if (!hasCastlingRight(c, Castling.WHITE_QUEENSIDE, Castling.BLACK_QUEENSIDE) || isInCheck(c)){
            return false;
        }
        
        Piece theRook = getPiece(PositionPool.get(QUEEN_ROOK_FILE, rank));
        Piece king = getPiece(c == Color.WHITE ? whiteKingPos : blackKingPos);
        if (theRook == null || theRook.getType() != PieceType.ROOK || theRook.getColor() != c ||
            king == null || king.getType() != PieceType.KING || king.getColor() != c){
            return false;
        }
        
        Position dFile = PositionPool.get(QUEEN_FILE, rank);
        Position cFile = PositionPool.get(QUEEN_BISHOP_FILE, rank);
        Position bFile = PositionPool.get(QUEEN_KNIGHT_FILE, rank);
        if (!isEmpty(dFile) || !isEmpty(cFile) || !isEmpty(bFile)){
            return false;
        }
        
        Color opp = c.opposite();
        if (isSquareAttacked(opp, dFile) || isSquareAttacked(opp, cFile)){
            return false;
        }
        
        return true;
    }
    
    private boolean hasCastlingRight(Color c, int whiteMask, int blackMask) {
        int mask = (c == Color.WHITE) ? whiteMask : blackMask;
        return (castlingRights & mask) != 0;
    }
    
    private void revokeCastlingRights(Color c, PieceType pt, Position from){
        if(c == Color.WHITE){
            if(pt == PieceType.KING){
                castlingRights &= ~(Castling.WHITE_KINGSIDE | Castling.WHITE_QUEENSIDE);
            }else if(pt == PieceType.ROOK){
                if(from.file() == 'h' && from.rank() == 1){
                    castlingRights &= ~Castling.WHITE_KINGSIDE;
                }
                if(from.file() == 'a' && from.rank() == 1){
                    castlingRights &= ~Castling.WHITE_QUEENSIDE;
                }
            }
        }else{
            if(pt == PieceType.KING){
                castlingRights &= ~(Castling.BLACK_KINGSIDE | Castling.BLACK_QUEENSIDE);
            }else if(pt == PieceType.ROOK){
                if(from.file() == 'h' && from.rank() == 8){
                    castlingRights &= ~Castling.BLACK_KINGSIDE;
                }
                if(from.file() == 'a' && from.rank() == 8){
                    castlingRights &= ~Castling.BLACK_QUEENSIDE;
                }
            }
        }
    }
    
    private void revokeCastlingRightsOnCapture(Color c, Position to){
        if(c == Color.WHITE){
            if(to.file() == 'h' && to.rank() == 1){
                castlingRights &= ~Castling.WHITE_KINGSIDE;
            }
            if(to.file() == 'a' && to.rank() == 1){
                castlingRights &= ~Castling.WHITE_QUEENSIDE;
            }
        }else{
            if(to.file() == 'h' && to.rank() == 8){
                castlingRights &= ~Castling.BLACK_KINGSIDE;
            }
            if(to.file() == 'a' && to.rank() == 8){
                castlingRights &= ~Castling.BLACK_QUEENSIDE;
            }
        }
    }
    
    public String castlingRightsToFen() {
        StringBuilder sb = new StringBuilder();
        
        if ((castlingRights & Castling.WHITE_KINGSIDE) != 0) {
            sb.append('K');
        }
        if ((castlingRights & Castling.WHITE_QUEENSIDE) != 0) {
            sb.append('Q');
        }
        if ((castlingRights & Castling.BLACK_KINGSIDE) != 0) {
            sb.append('k');
        }
        if ((castlingRights & Castling.BLACK_QUEENSIDE) != 0) {
            sb.append('q');
        }
        
        return sb.isEmpty() ? "-" : sb.toString();
    }
    
    public boolean isSquareAttacked(Color byColor, Position posTarget){
        for (char f = 'a'; f <= 'h'; f++) {
            for (int r = 1; r <= 8; r++) {
                Position from = PositionPool.get(f, r);
                Piece p = getPiece(from);
                if (p == null || p.getColor() != byColor) continue;
                
                MoveStrategy ms = MoveStrategyRegistry.get(p.getType());
                if (ms.generateAttacks(this, from).contains(posTarget)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isInCheck(Color color){
        Position kingPos = color == Color.WHITE ? whiteKingPos : blackKingPos;
        Color opponentColor = color.opposite();
        
        return isSquareAttacked(opponentColor, kingPos);
    }
    
    public boolean moveLeavesKingInCheck(Color color, Position posFrom, Position posTo){
        Board copy = copy();
        copy.movePiece(posFrom, posTo);
        return copy.isInCheck(color);
    }
    
    public String getBoardFen(){
        StringBuilder fen = new StringBuilder();
        
        for (int r = 8; r >= 1; r--) {
            int empty = 0;
            for (char f = 'a'; f <= 'h'; f++){
                Piece piece = getPiece(PositionPool.get(f, r));
                if (piece == null){
                    empty++;
                } else {
                    if (empty > 0){
                        fen.append(empty);
                        empty = 0;
                    }
                    char pieceFen = piece.getType().fen();
                    fen.append(piece.getColor() == Color.WHITE ? pieceFen : Character.toLowerCase(pieceFen));
                }
            }
            if (empty > 0){
                fen.append(empty);
            }
            if (r > 1){
                fen.append("/");
            }
        }
        
        return fen.toString();
    }

    public Board copy(){
        Board copyBoard = new Board();
        
        this.forEachPiece(pos -> {
            copyBoard.placePiece(pos, this.getPiece(pos));
        });
        
        copyBoard.whiteKingPos = this.whiteKingPos;
        copyBoard.blackKingPos = this.blackKingPos;
        
        copyBoard.castlingRights = this.castlingRights;
        
        return copyBoard;
    }
}
