package org.horel.yachessj.engine.model.pieces;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Position;

import java.util.List;

public interface Piece {

    Color getColor();
    PieceType getType();
    Position getPosition();
    void setPosition(Position position);
    boolean isMoved();
    void setMoved(boolean captured);
    boolean isCaptured();
    void setCaptured(boolean captured);

    List<Position> getTheoreticalMovesPositions();

    Piece copy();
}
