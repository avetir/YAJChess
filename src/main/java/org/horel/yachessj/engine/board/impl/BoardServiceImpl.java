package org.horel.yachessj.engine.board.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.model.Board;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.Tile;
import org.horel.yachessj.engine.model.pieces.impl.*;
import org.horel.yachessj.engine.model.pieceset.PieceSet;
import org.horel.yachessj.engine.model.pieceset.impl.DefaultPieceSet;
import org.horel.yachessj.engine.board.BoardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.horel.yachessj.engine.constants.Constants.*;

@Service
public class BoardServiceImpl implements BoardService {

    public Board createNewBoard(){
        Board board = new Board();

        generateTileSet(board);
        generatePieceSets(board);
        placePieces(board);

        return board;
    }

    private void generateTileSet(Board board) {
        Tile[][] boardTileSet = board.getTileSet();
        for (char f = 'a'; f <= 'h'; f++) {
            for (int r = 1; r <= 8; r++) {
                Color c = (f + r) % 2 == 0 ? Color.BLACK : Color.WHITE;
                Tile tile = new Tile(f, r, c);
                boardTileSet[f - 'a'][r - 1] = tile;
            }
        }
    }

    private void generatePieceSets(Board board) {
        Map<Color, PieceSet> pieceSets = board.getPieceSets();

        pieceSets.put(Color.WHITE, generateDefaultPieceSet(Color.WHITE));
        pieceSets.put(Color.BLACK, generateDefaultPieceSet(Color.BLACK));
    }

    private PieceSet generateDefaultPieceSet(Color color) {
        int pawnRank = WHITE_PAWNS_RANK;
        int pieceRank = WHITE_PIECES_RANK;

        if (color == Color.BLACK) {
            pawnRank = BLACK_PAWNS_RANK;
            pieceRank = BLACK_PIECES_RANK;
        }

        List<Pawn>   pawns   = new ArrayList<>();
        List<Knight> knights = new ArrayList<>();
        List<Bishop> bishops = new ArrayList<>();
        List<Rook>   rooks   = new ArrayList<>();
        List<Queen>  queens  = new ArrayList<>();
        King king            = null;

        for (char f = 'a'; f <= 'h'; f++) {
            pawns.add(new Pawn(color, new Position(f, pawnRank)));

            Position piecePos = new Position(f, pieceRank);

            switch (f) {
                case 'a', 'h':
                    rooks.add(new Rook(color, piecePos));
                    break;
                case 'b', 'g':
                    knights.add(new Knight(color, piecePos));
                    break;
                case 'c', 'f':
                    bishops.add(new Bishop(color, piecePos));
                    break;
                case 'd':
                    queens.add(new Queen(color, piecePos));
                    break;
                case 'e':
                    king = new King(color, piecePos);
                    break;
            }
        }
        return new DefaultPieceSet(pawns, knights, bishops, rooks, queens, king);
    }

    private void placePieces(Board board) {
        board.getPieceSets().values().stream()
                .map(PieceSet::getAllPieces).flatMap(List::stream)
                .forEach(piece -> board.placePiece(piece.getPosition(), piece));
    }
}
