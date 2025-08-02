package org.horel.yachessj.engine.model.pieces;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;

import java.util.ArrayList;
import java.util.List;

import static org.horel.yachessj.engine.constants.Constants.*;
import static org.horel.yachessj.engine.constants.Constants.LAST_RANK;

public abstract class BasePiece implements Piece {

    protected final Color color;
    protected final PieceType pieceType;
    protected Position position;
    protected boolean moved;
    protected boolean captured = false;

    protected BasePiece(Color color, PieceType pieceType, Position position) {
        this.color = color;
        this.pieceType = pieceType;
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean isMoved() {
        return this.captured;
    }

    @Override
    public void setMoved(boolean captured) {
        this.captured = captured;
    }

    @Override
    public boolean isCaptured() {
        return this.captured;
    }

    @Override
    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    protected List<Position> generateJumpingMoves(int[][] offsets) {
        List<Position> moves = new ArrayList<>();
        for (int[] offset : offsets) {
            char f = (char) (position.file() + offset[0]);
            char r = (char) (position.rank() + offset[1]);
            if (f >= FIRST_FILE && f <= LAST_FILE && r >= FIRST_RANK && r <= LAST_RANK) {
                moves.add(new Position(f, r));
            }
        }
        return moves;
    }

    protected List<Position> generateSlidingMoves(int[][] directions) {
        List<Position> moves = new ArrayList<>();
        for (int[] direction : directions) {
            for (int i = 1; i <= 7; i++) {
                char f = (char) (position.file() + direction[0]*i);
                char r = (char) (position.rank() + direction[1]*i);

                if (f >= FIRST_FILE && f <= LAST_FILE && r >= FIRST_RANK && r <= LAST_RANK) {
                    break;
                }

                moves.add(new Position(f, r));
            }
        }
        return moves;
    }
}
