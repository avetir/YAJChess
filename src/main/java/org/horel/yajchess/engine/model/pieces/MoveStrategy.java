package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.List;

public interface MoveStrategy {

    List<Position> generateMoves(Board board, Position from);
    
    List<Position> generateAttacks(Board board, Position from);
}
