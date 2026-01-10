package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.List;

public class SteppingStrategy extends BaseMoveStrategy {

    private final int[][] offsets;

    public SteppingStrategy(int[][] offsets) {
        this.offsets = offsets;
    }

    @Override
    public List<Position> generateMoves(Board b, Position from) {
        return collectSteppingMoves(b, from, offsets);
    }
}
