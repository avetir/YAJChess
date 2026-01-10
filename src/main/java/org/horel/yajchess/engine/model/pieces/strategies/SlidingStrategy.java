package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.List;

public class SlidingStrategy extends BaseMoveStrategy {

    private final int[][] directions;

    public SlidingStrategy(int[][] directions) {
        this.directions = directions;
    }

    @Override
    public List<Position> generateMoves(Board b, Position from) {
        return collectSlidingMoves(b, from, directions);
    }
}
