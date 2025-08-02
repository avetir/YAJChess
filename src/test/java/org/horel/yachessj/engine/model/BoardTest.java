package org.horel.yachessj.engine.model;

import org.horel.yachessj.engine.model.pieces.Piece;
import org.horel.yachessj.engine.model.pieces.impl.Rook;
import org.horel.yachessj.engine.enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board();
        Tile[][] tileSet = board.getTileSet();
        int testRank = 1;
        for (char f = 'a'; f <= 'h'; f++){
            Color color = (f + testRank) % 2 == 0 ? Color.BLACK : Color.WHITE;
            tileSet[f - 'a'][testRank - 1] = new Tile(f, testRank, color);
        }
    }

    private void placeTestPiece(Position pos, Piece piece) {
        board.placePiece(pos, piece);
    }

    @Test
    public void testPlacePiece() {
        Position pos =  new Position('a', 1);
        Piece piece = new Rook(Color.WHITE, pos);
        board.placePiece(piece.getPosition(), piece);

        Tile tile = board.getTile(pos);
        assertNotNull(tile.getPiece());
        assertEquals(tile.getPiece(), piece);
        assertEquals(piece.getPosition(), pos);
    }

    @Test
    public void testMovePiece() {
        Position posFrom =  new Position('a', 1);
        Piece piece = new Rook(Color.WHITE, posFrom);
        placeTestPiece(posFrom, piece);
        Position posTo =  new Position('h', 1);
        board.movePiece(posFrom, posTo);

        Tile tileFrom = board.getTile(posFrom);
        Tile tileTo = board.getTile(posTo);
        assertNull(tileFrom.getPiece());
        assertNotNull(tileTo.getPiece());
        assertEquals(tileTo.getPiece(), piece);
        assertEquals(piece.getPosition(), posTo);
    }
}
