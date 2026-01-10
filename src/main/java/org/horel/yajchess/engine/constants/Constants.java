package org.horel.yajchess.engine.constants;

import org.horel.yajchess.engine.enums.PieceType;

public final class Constants{
    
    public static final int FIRST_RANK = 1;
    public static final int LAST_RANK = 8;
    public static final char FIRST_FILE = 'a';
    public static final char LAST_FILE = 'h';
    
    public static final char QUEEN_ROOK_FILE = 'a';
    public static final char QUEEN_KNIGHT_FILE = 'b';
    public static final char QUEEN_BISHOP_FILE = 'c';
    public static final char QUEEN_FILE = 'd';
    public static final char KING_FILE = 'e';
    public static final char KING_BISHOP_FILE = 'f';
    public static final char KING_KNIGHT_FILE = 'g';
    public static final char KING_ROOK_FILE = 'h';

    public static final int WHITE_PIECE_RANK = FIRST_RANK;
    public static final int WHITE_PAWN_RANK  = FIRST_RANK + 1;
    public static final int BLACK_PAWN_RANK  = LAST_RANK  - 1;
    public static final int BLACK_PIECE_RANK = LAST_RANK;

    public static final int[][] BISHOP_MOVE_DIRECTIONS = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    public static final int[][] ROOK_MOVE_DIRECTIONS   = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static final int[][] QUEEN_MOVE_DIRECTIONS  = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static final PieceType[] JUMPING_PIECES_TYPES = {PieceType.PAWN, PieceType.KNIGHT, PieceType.KING};
    public static final PieceType[] SLIDING_PIECES_TYPES = {PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};
}
