package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.PieceFactory;
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
        Position pos = PositionPool.get('e', 4);
        Piece testBishop = PieceFactory.create(Color.WHITE, PieceType.BISHOP);
        testBoard.placePiece(pos, testBishop);

        List<Position> moves = testBishop.getMoveStrategy().generateMoves(testBoard, testBishop);
        assertEquals(13, moves.size());

        assertTrue(moves.containsAll(bishopMovesEmptyBoard));

    }

    @Test
    public void testGenerateMoves_BishopAtE4_WhiteRookAtC6_BlackPawnAtG6() {
        Position pos = PositionPool.get('e', 4);
        Piece testBishop = PieceFactory.create(Color.WHITE, PieceType.BISHOP, pos);
        testBoard.placePiece(testBishop.getPosition(), testBishop);

        Piece whiteRook = PieceFactory.create(Color.WHITE, PieceType.ROOK, PositionPool.get('c', 6));
        testBoard.placePiece(whiteRook.getPosition(), whiteRook);
        Piece blackPawn = PieceFactory.create(Color.BLACK, PieceType.PAWN, PositionPool.get('g', 6));
        testBoard.placePiece(blackPawn.getPosition(), blackPawn);

        List<Position> moves = testBishop.getMoveStrategy().generateMoves(testBoard, testBishop);
        assertEquals(9, moves.size());

        assertTrue(moves.containsAll(bishopMovesRookPawnBoard));

        assertTrue(Collections.disjoint(moves, bishopBlockedMovesRookPawnBoard));
    }

    @Test
    public void testGenerateMoves_RookAtE4_EmptyBoard() {
        Position pos = PositionPool.get('e', 4);
        Piece testRook = PieceFactory.create(Color.WHITE, PieceType.ROOK, pos);
        testBoard.placePiece(testRook.getPosition(), testRook);

        List<Position> moves = testRook.getMoveStrategy().generateMoves(testBoard, testRook);
        assertEquals(14, moves.size());

        assertTrue(moves.containsAll(rookMovesEmptyBoard));

    }

    @Test
    public void testGenerateMoves_RookAtE4_WhiteBishopAtC4_BlackPawnAtE6() {
        Position pos = PositionPool.get('e', 4);
        Piece testRook = PieceFactory.create(Color.WHITE, PieceType.ROOK, pos);
        testBoard.placePiece(testRook.getPosition(), testRook);

        Piece whiteBishop = PieceFactory.create(Color.WHITE, PieceType.BISHOP, PositionPool.get('c', 4));
        testBoard.placePiece(whiteBishop.getPosition(), whiteBishop);
        Piece blackPawn = PieceFactory.create(Color.BLACK, PieceType.PAWN, PositionPool.get('e', 6));
        testBoard.placePiece(blackPawn.getPosition(), blackPawn);

        List<Position> moves = testRook.getMoveStrategy().generateMoves(testBoard, testRook);
        assertEquals(9, moves.size());

        assertTrue(moves.containsAll(rookMovesBishopPawnBoard));

        assertTrue(Collections.disjoint(moves, rookBlockedMovesBishopPawnBoard));
    }

    @Test
    public void testGenerateMoves_QueenAtE4_EmptyBoard() {
        Position pos = PositionPool.get('e', 4);
        Piece testQueen = PieceFactory.create(Color.WHITE, PieceType.QUEEN, pos);
        testBoard.placePiece(testQueen.getPosition(), testQueen);

        List<Position> moves = testQueen.getMoveStrategy().generateMoves(testBoard, testQueen);
        assertEquals(27, moves.size());

        assertTrue(moves.containsAll(bishopMovesEmptyBoard));
        assertTrue(moves.containsAll(rookMovesBishopPawnBoard));

    }

    @Test
    public void testGenerateMoves_QueenAtE4_WhiteBishopAtC4_WhiteRookAtC6_BlackPawnAtE6_BlackPawnAtG6() {
        Position pos = PositionPool.get('e', 4);
        Piece testQueen = PieceFactory.create(Color.WHITE, PieceType.QUEEN, pos);
        testBoard.placePiece(testQueen.getPosition(), testQueen);

        Piece whiteBishop = PieceFactory.create(Color.WHITE, PieceType.BISHOP, PositionPool.get('c', 4));
        testBoard.placePiece(whiteBishop.getPosition(), whiteBishop);
        Piece blackPawnE = PieceFactory.create(Color.BLACK, PieceType.PAWN, PositionPool.get('e', 6));
        testBoard.placePiece(blackPawnE.getPosition(), blackPawnE);

        Piece whiteRook = PieceFactory.create(Color.WHITE, PieceType.ROOK, PositionPool.get('c', 6));
        testBoard.placePiece(whiteRook.getPosition(), whiteRook);
        Piece blackPawnG = PieceFactory.create(Color.BLACK, PieceType.PAWN, PositionPool.get('g', 6));
        testBoard.placePiece(blackPawnG.getPosition(), blackPawnG);

        List<Position> moves = testQueen.getMoveStrategy().generateMoves(testBoard, testQueen);
        assertEquals(18, moves.size());

        assertTrue(moves.containsAll(bishopMovesRookPawnBoard));
        assertTrue(moves.containsAll(rookMovesBishopPawnBoard));

        assertTrue(Collections.disjoint(moves, bishopBlockedMovesRookPawnBoard));
        assertTrue(Collections.disjoint(moves, rookBlockedMovesBishopPawnBoard));
    }

}
