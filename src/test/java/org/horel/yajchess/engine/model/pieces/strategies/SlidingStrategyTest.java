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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlidingStrategyTest {

    Board testBoard;

    List<Position> bishopMovesEmptyBoard =
            List.of(PositionPool.get('a', 8), PositionPool.get('h', 7),
                    PositionPool.get('h', 1), PositionPool.get('b', 1)
            );

    List<Position> bishopMovesRookPawnBoard =
            List.of(
                    PositionPool.get('d', 5), PositionPool.get('g', 6),
                    PositionPool.get('h', 1), PositionPool.get('b', 1)
            );

    List<Position> bishopBlockedMovesRookPawnBoard =
            List.of(
                    PositionPool.get('h', 7), PositionPool.get('c', 6),
                    PositionPool.get('b', 7), PositionPool.get('a', 8)
            );

    List<Position> rookMovesEmptyBoard =
            List.of(
                    PositionPool.get('e', 8), PositionPool.get('h', 4),
                    PositionPool.get('e', 1), PositionPool.get('a', 4)
            );

    List<Position> rookMovesBishopPawnBoard =
            List.of(
                    PositionPool.get('e', 6), PositionPool.get('h', 4),
                    PositionPool.get('e', 1), PositionPool.get('d', 4)
            );

    List<Position> rookBlockedMovesBishopPawnBoard =
            List.of(
                    PositionPool.get('e', 7), PositionPool.get('e', 8),
                    PositionPool.get('c', 4), PositionPool.get('b', 4),
                    PositionPool.get('a', 4)
            );

    @BeforeEach
    void setUpBoard() {
        testBoard = TestBoardFactory.emptyBoard();
    }

    @Test
    public void testGenerateMoves_BishopAtE4_EmptyBoard() {
        Position posFrom = PositionPool.get('e', 4);
        Piece testBishop = PieceRegistry.get(Color.WHITE, PieceType.BISHOP);
        testBoard.placePiece(posFrom, testBishop);

        List<Position> moves = MoveStrategyRegistry.get(PieceType.BISHOP).generateMoves(testBoard, posFrom);
        assertEquals(13, moves.size());

        assertTrue(moves.containsAll(bishopMovesEmptyBoard));
    }

    @Test
    public void testGenerateMoves_BishopAtE4_WhiteRookAtC6_BlackPawnAtG6() {
        Position bPos = PositionPool.get('e', 4);
        Piece testBishop = PieceRegistry.get(Color.WHITE, PieceType.BISHOP);
        testBoard.placePiece(bPos, testBishop);

        Position pos = PositionPool.get('c', 6);
        Piece whiteRook = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        testBoard.placePiece(pos, whiteRook);
        
        pos = PositionPool.get('g', 6);
        Piece blackPawn = PieceRegistry.get(Color.BLACK, PieceType.PAWN);
        testBoard.placePiece(pos, blackPawn);

        List<Position> moves = MoveStrategyRegistry.get(PieceType.BISHOP).generateMoves(testBoard, bPos);
        assertEquals(9, moves.size());

        assertTrue(moves.containsAll(bishopMovesRookPawnBoard));

        assertTrue(Collections.disjoint(moves, bishopBlockedMovesRookPawnBoard));
    }

    @Test
    public void testGenerateMoves_RookAtE4_EmptyBoard() {
        Position pos = PositionPool.get('e', 4);
        Piece testRook = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        testBoard.placePiece(pos, testRook);

        List<Position> moves = MoveStrategyRegistry.get(PieceType.ROOK).generateMoves(testBoard, pos);
        assertEquals(14, moves.size());

        assertTrue(moves.containsAll(rookMovesEmptyBoard));

    }

    @Test
    public void testGenerateMoves_RookAtE4_WhiteBishopAtC4_BlackPawnAtE6() {
        Position rPos = PositionPool.get('e', 4);
        Piece testRook = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        testBoard.placePiece(rPos, testRook);

        Position pos = PositionPool.get('c', 4);
        Piece whiteBishop = PieceRegistry.get(Color.WHITE, PieceType.BISHOP);
        testBoard.placePiece(pos, whiteBishop);
        
        pos = PositionPool.get('e', 6);
        Piece blackPawn = PieceRegistry.get(Color.BLACK, PieceType.PAWN);
        testBoard.placePiece(pos, blackPawn);

        List<Position> moves = MoveStrategyRegistry.get(PieceType.ROOK).generateMoves(testBoard, rPos);
        assertEquals(9, moves.size());

        assertTrue(moves.containsAll(rookMovesBishopPawnBoard));

        assertTrue(Collections.disjoint(moves, rookBlockedMovesBishopPawnBoard));
    }

    @Test
    public void testGenerateMoves_QueenAtE4_EmptyBoard() {
        Position pos = PositionPool.get('e', 4);
        Piece testQueen = PieceRegistry.get(Color.WHITE, PieceType.QUEEN);
        testBoard.placePiece(pos, testQueen);

        List<Position> moves = MoveStrategyRegistry.get(PieceType.QUEEN).generateMoves(testBoard, pos);
        assertEquals(27, moves.size());

        assertTrue(moves.containsAll(bishopMovesEmptyBoard));
        assertTrue(moves.containsAll(rookMovesBishopPawnBoard));

    }

    @Test
    public void testGenerateMoves_QueenAtE4_WhiteBishopAtC4_WhiteRookAtC6_BlackPawnAtE6_BlackPawnAtG6() {
        Position qPos = PositionPool.get('e', 4);
        Piece testQueen = PieceRegistry.get(Color.WHITE, PieceType.QUEEN);
        testBoard.placePiece(qPos, testQueen);
        
        Position pos = PositionPool.get('c', 4);
        Piece whiteBishop = PieceRegistry.get(Color.WHITE, PieceType.BISHOP);
        testBoard.placePiece(pos, whiteBishop);
        
        pos = PositionPool.get('e', 6);
        Piece blackPawnE = PieceRegistry.get(Color.BLACK, PieceType.PAWN);
        testBoard.placePiece(pos, blackPawnE);
        
        pos = PositionPool.get('c', 6);
        Piece whiteRook = PieceRegistry.get(Color.WHITE, PieceType.ROOK);
        testBoard.placePiece(pos, whiteRook);
        
        pos = PositionPool.get('g', 6);
        Piece blackPawnG = PieceRegistry.get(Color.BLACK, PieceType.PAWN);
        testBoard.placePiece(pos, blackPawnG);

        List<Position> moves = MoveStrategyRegistry.get(PieceType.QUEEN).generateMoves(testBoard, qPos);
        assertEquals(18, moves.size());

        assertTrue(moves.containsAll(bishopMovesRookPawnBoard));
        assertTrue(moves.containsAll(rookMovesBishopPawnBoard));

        assertTrue(Collections.disjoint(moves, bishopBlockedMovesRookPawnBoard));
        assertTrue(Collections.disjoint(moves, rookBlockedMovesBishopPawnBoard));
    }

}
