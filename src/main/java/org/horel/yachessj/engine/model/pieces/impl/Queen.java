package org.horel.yachessj.engine.model.pieces.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yachessj.engine.constants.Constants.QUEEN_MOVE_DIRECTIONS;

public class Queen extends BasePiece {
    public Queen(Color color, Position position) {
        super(color, PieceType.QUEEN, position);
    }

    @Override
    public List<Position> getTheoreticalMovesPositions() {
        return generateSlidingMoves(QUEEN_MOVE_DIRECTIONS);
    }

    @Override
    public Queen copy() {
        return new Queen(color, position);
    }
}
