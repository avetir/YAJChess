package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.pieces.strategies.CastlingStrategy;
import org.horel.yajchess.engine.model.pieces.strategies.CompositeStrategy;
import org.horel.yajchess.engine.model.pieces.strategies.PawnStrategy;
import org.horel.yajchess.engine.model.pieces.strategies.SlidingStrategy;
import org.horel.yajchess.engine.model.pieces.strategies.SteppingStrategy;

import java.util.List;

public final class MoveStrategyFactory {

    private static final int[][] KNIGHT_OFFSETS = {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
    private static final int[][] KING_OFFSETS   = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
    private static final int[][] DIAGONALS      = {{1,1},{1,-1},{-1,-1},{-1,1}};
    private static final int[][] ORTHOGONALS    = {{1,0},{-1,0},{0,1},{0,-1}};

    private static final MoveStrategy PAWN_STRATEGY   = new PawnStrategy();
    private static final MoveStrategy KNIGHT_STRATEGY = new SteppingStrategy(KNIGHT_OFFSETS);
    private static final MoveStrategy KING_STEPPING   = new SteppingStrategy(KING_OFFSETS);
    private static final MoveStrategy BISHOP_STRATEGY = new SlidingStrategy(DIAGONALS);
    private static final MoveStrategy ROOK_STRATEGY   = new SlidingStrategy(ORTHOGONALS);
    private static final MoveStrategy QUEEN_COMPOSITE = new CompositeStrategy(List.of(BISHOP_STRATEGY, ROOK_STRATEGY));
    private static final MoveStrategy CASTLE_STRATEGY = new CastlingStrategy();
    private static final MoveStrategy KING_COMPOSITE  = new CompositeStrategy(List.of(KING_STEPPING, CASTLE_STRATEGY));

    public static MoveStrategy get(PieceType type) {
        return switch (type) {
            case PAWN   -> PAWN_STRATEGY;
            case KNIGHT -> KNIGHT_STRATEGY;
            case BISHOP -> BISHOP_STRATEGY;
            case ROOK   -> ROOK_STRATEGY;
            case QUEEN  -> QUEEN_COMPOSITE;
            case KING   -> KING_COMPOSITE;
        };
    }
}
