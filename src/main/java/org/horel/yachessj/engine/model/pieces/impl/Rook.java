package org.horel.yachessj.engine.model.pieces.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yachessj.engine.constants.Constants.ROOK_MOVE_DIRECTIONS;

public class Rook extends BasePiece {
    public Rook(Color color, Position position) {
        super(color, PieceType.ROOK, position);
    }

    @Override
    public List<Position> getTheoreticalMovesPositions() {
        return generateSlidingMoves(ROOK_MOVE_DIRECTIONS);
    }

    @Override
    public Rook copy() {
        return new Rook(color, position);
    }
}
