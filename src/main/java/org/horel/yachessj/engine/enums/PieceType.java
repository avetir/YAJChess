package org.horel.yachessj.engine.enums;

public enum PieceType {
    PAWN(false),
    KNIGHT(false),
    BISHOP(true),
    ROOK(true),
    QUEEN(true),
    KING(false);

    private final boolean isSliding;

    PieceType(boolean isSliding) {
        this.isSliding = isSliding;
    }

    public boolean isSliding() {
        return isSliding;
    }
}
