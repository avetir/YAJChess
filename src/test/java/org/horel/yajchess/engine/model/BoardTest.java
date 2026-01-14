package org.horel.yajchess.engine.model;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.PieceRegistry;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;
import org.horel.yajchess.engine.testutils.TestBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    
    private Board board;
    
    @BeforeEach
    public void setup() {
        board = TestBoardFactory.emptyBoard();
    }

    private void placeTestPiece(Position pos, Piece piece) {
        board.placePiece(pos, piece);
    }

    @Test
    public void testPlacePiece() {
        String fen = "8/8/8/8/8/8/8/R7";
        Position pos =  PositionPool.get('a', 1);
        Piece testPiece = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        board.placePiece(pos, testPiece);

        assertNotNull(board.getPiece(pos));
        assertEquals(testPiece, board.getPiece(pos));
        assertEquals(fen, board.getBoardFen());
    }

    @Test
    public void testMovePiece() {
        String fen = "8/8/8/8/8/8/8/4R3";
        
        Position posFrom =  PositionPool.get('a', 1);
        Piece piece = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        placeTestPiece(posFrom, piece);
        Position posTo =  PositionPool.get('e', 1);
        board.movePiece(posFrom, posTo);

        assertNull(board.getPiece(posFrom));
        assertNotNull(board.getPiece(posTo));
        assertEquals(piece, board.getPiece(posTo));
    }
    
    @Test
    public void testCreateStartingPositionBoard(){
        String startingBoardFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        
        board.placeInitialPieces();
        
        assertEquals(startingBoardFen, board.getBoardFen());
    }
}
