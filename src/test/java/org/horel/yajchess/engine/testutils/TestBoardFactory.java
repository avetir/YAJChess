package org.horel.yajchess.engine.testutils;

import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.Map;

public class TestBoardFactory {

    private TestBoardFactory() {}

    public static Board emptyBoard() {
        return Board.newBoard();
    }

    public static Board withPieces(Map<Position, Piece> pieces) {
        Board b = emptyBoard();
        for (Map.Entry<Position, Piece> e : pieces.entrySet()) {
            place(b, e.getKey(), e.getValue());
        }
        return b;
    }

    private static void place(Board b, Position pos, Piece p) {
        b.placePiece(pos, p);
    }
}
