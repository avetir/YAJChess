package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.PieceFactory;
import org.horel.yajchess.engine.model.pieces.PieceFactoryTest;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;
import org.horel.yajchess.engine.testutils.TestBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SteppingStrategyTest {

    Board testBoard;
    Piece testPiece;

    @BeforeEach
    void setUp() {
        Position pos = PositionPool.get('e', 4);
        testPiece = PieceFactory.create(Color.WHITE, PieceType.KNIGHT);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(pos, testPiece);
        testBoard = TestBoardFactory.withPieces(pieces);
    }

    @Test
    public void testGenerateMoves_KnightAtE4_EmptyBoard() {

        List<Position> moves = testPiece.getMoveStrategy().generateMoves(testBoard, testPiece);
        assertEquals(8, moves.size());

        assertTrue(moves.contains(PositionPool.get('d', 6)));
        assertTrue(moves.contains(PositionPool.get('f', 6)));
        assertTrue(moves.contains(PositionPool.get('g', 5)));
        assertTrue(moves.contains(PositionPool.get('g', 3)));
        assertTrue(moves.contains(PositionPool.get('f', 2)));
        assertTrue(moves.contains(PositionPool.get('d', 2)));
        assertTrue(moves.contains(PositionPool.get('c', 3)));
        assertTrue(moves.contains(PositionPool.get('c', 5)));
    }

    @Test
    public void testGenerateMoves_KnightAtE4_WhiteRookAtD6_BlackPawnAtF6() {

        Piece whiteRook = PieceFactory.create(Color.WHITE, PieceType.ROOK, PositionPool.get('d', 6));
        Piece blackPawn = PieceFactory.create(Color.BLACK, PieceType.PAWN, PositionPool.get('f', 6));

        testBoard.placePiece(whiteRook.getPosition(), whiteRook);
        testBoard.placePiece(blackPawn.getPosition(), blackPawn);

        List<Position> moves = testPiece.getMoveStrategy().generateMoves(testBoard, testPiece);
        assertEquals(7, moves.size());

        assertFalse(moves.contains(PositionPool.get('d', 6)));
        assertTrue(moves.contains(PositionPool.get('f', 6)));
        assertTrue(moves.contains(PositionPool.get('g', 5)));
        assertTrue(moves.contains(PositionPool.get('g', 3)));
        assertTrue(moves.contains(PositionPool.get('f', 2)));
        assertTrue(moves.contains(PositionPool.get('d', 2)));
        assertTrue(moves.contains(PositionPool.get('c', 3)));
        assertTrue(moves.contains(PositionPool.get('c', 5)));
    }

}
