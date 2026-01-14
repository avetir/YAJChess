package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.MoveStrategy;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.List;

public class CompositeStrategy extends BaseMoveStrategy {

    private final List<MoveStrategy> strategies;

    public CompositeStrategy(List<MoveStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public List<Position> generateAttacks(Board b, Position from) {
        return strategies.stream()
                .flatMap(s -> s.generateAttacks(b, from).stream())
                .distinct()
                .toList();
    }

    @Override
    public List<Position> generateMoves(Board b, Position from) {
        return strategies.stream()
                .flatMap(s -> s.generateMoves(b, from).stream())
                .distinct()
                .toList();
    }
}
