package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Position;

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
