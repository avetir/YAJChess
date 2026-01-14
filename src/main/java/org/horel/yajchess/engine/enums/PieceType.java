package org.horel.yajchess.engine.enums;

public enum PieceType {
    PAWN('P'),
    KNIGHT('N'),
    BISHOP('B'),
    ROOK('R'),
    QUEEN('Q'),
    KING('K');

    private final char fen;

    PieceType(char fen) {
        this.fen = fen;
    }

    public char fen() {
        return fen;
    }
}
