package org.horel.yachessj.engine.model.pieces.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yachessj.engine.constants.Constants.KING_MOVE_OFFSETS;

public class King extends BasePiece {
    public King(Color color, Position position) {
        super(color, PieceType.KING, position);
    }

    @Override
    public List<Position> getTheoreticalMovesPositions() {
        return generateJumpingMoves(KING_MOVE_OFFSETS);
    }

    @Override
    public King copy() {
        return new King(color, position);
    }
}
