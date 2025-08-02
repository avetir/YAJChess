package org.horel.yajchess.engine.model.pieces.impl;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Position;
import org.horel.yajchess.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yajchess.engine.constants.Constants.KING_MOVE_OFFSETS;

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
