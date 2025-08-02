package org.horel.yachessj.engine.model.pieces.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.BasePiece;

import java.util.List;

public class Bishop extends BasePiece {
    public Bishop(Color color, Position position) {
        super(color, PieceType.BISHOP, position);
    }

    @Override
    public List<Position> getTheoreticalMovesPositions() {
        int [][] moveDirections = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};

        return generateSlidingMoves(moveDirections);
    }

    @Override
    public Bishop copy() {
        return new Bishop(color, position);
    }
}
