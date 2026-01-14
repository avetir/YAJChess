package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.MoveStrategyRegistry;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.PieceRegistry;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;
import org.horel.yajchess.engine.testutils.TestBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SteppingStrategyTest {

    Board testBoard;
    Piece testPiece;
    Position testPiecePos = PositionPool.get('e', 4);

    @BeforeEach
    void setUp() {
        testPiece = PieceRegistry.get(Color.WHITE, PieceType.KNIGHT);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(testPiecePos, testPiece);
        testBoard = TestBoardFactory.withPieces(pieces);
    }

    @Test
    public void testGenerateMoves_KnightAtE4_EmptyBoard() {

        List<Position> moves = MoveStrategyRegistry.get(PieceType.KNIGHT).generateMoves(testBoard, testPiecePos);
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

        Position whiteRookPos = PositionPool.get('d', 6);
        Position blackPawnPos = PositionPool.get('f', 6);

        Piece whiteRook = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        Piece blackPawn = PieceRegistry.get(Color.BLACK, PieceType.PAWN);

        testBoard.placePiece(whiteRookPos, whiteRook);
        testBoard.placePiece(blackPawnPos, blackPawn);
        
        List<Position> moves = MoveStrategyRegistry.get(PieceType.KNIGHT).generateMoves(testBoard, testPiecePos);
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
