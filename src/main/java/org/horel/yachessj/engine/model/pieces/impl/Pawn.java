package org.horel.yachessj.engine.model.pieces.impl;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.BasePiece;

import java.util.List;

import static org.horel.yachessj.engine.constants.Constants.PAWN_MOVE_OFFSETS;

public class Pawn extends BasePiece {
    public Pawn(Color color, Position position) {
        super(color, PieceType.PAWN, position);
    }

    @Override
    public List<Position> getTheoreticalMovesPositions() {
        int[][] offsets = PAWN_MOVE_OFFSETS;
        if (color == Color.BLACK) {
            for (int i = 0; i < offsets.length; i++) {
                offsets[i][1] *= -1;
            }
        }
        return generateJumpingMoves(offsets);
    }

    @Override
    public Pawn copy() {
        return new Pawn(color, position);
    }
}
