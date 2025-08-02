package org.horel.yachessj.engine.model.pieces.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yachessj.engine.constants.Constants.KNIGHT_MOVE_OFFSETS;

public class Knight extends BasePiece {
    public Knight(Color color, Position position) {
        super(color, PieceType.KNIGHT, position);
    }

    @Override
    public List<Position> getTheoreticalMovesPositions() {
        return generateJumpingMoves(KNIGHT_MOVE_OFFSETS);
    }

    @Override
    public Knight copy() {
        return new Knight(color, position);
    }
}
