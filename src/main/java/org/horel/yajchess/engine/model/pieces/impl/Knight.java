package org.horel.yajchess.engine.model.pieces.impl;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Position;
import org.horel.yajchess.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yajchess.engine.constants.Constants.KNIGHT_MOVE_OFFSETS;

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
