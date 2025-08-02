package org.horel.yajchess.engine.model;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.model.pieces.Piece;

public class Tile {
    private final Position position;
    private final Color color;
    private Piece piece;

    public Tile(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Tile(char file, int rank, Color color) {
        this(new Position(file, rank), color);
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}
