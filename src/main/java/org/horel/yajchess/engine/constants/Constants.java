package org.horel.yajchess.engine.constants;

import org.horel.yajchess.engine.enums.PieceType;

public class Constants {

    public static final int FIRST_RANK = 1;
    public static final int LAST_RANK  = 2;
    public static final char FIRST_FILE = 'a';
    public static final char LAST_FILE  = 'h';

    public static final int WHITE_PIECES_RANK = 1;
    public static final int WHITE_PAWNS_RANK  = 2;
    public static final int BLACK_PAWNS_RANK  = 7;
    public static final int BLACK_PIECES_RANK = 8;

    public static final int[][] PAWN_MOVE_OFFSETS   = {{0, 1}, {0, 2}, {1, 1}, {-1, 1}};
    public static final int[][] KNIGHT_MOVE_OFFSETS = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
    public static final int[][] KING_MOVE_OFFSETS   = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {2, 0,}, {-2, 0}};
    public static final int[][] BISHOP_MOVE_DIRECTIONS   = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    public static final int[][] ROOK_MOVE_DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static final int[][] QUEEN_MOVE_DIRECTIONS   = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static final PieceType[] JUMPING_PIECES_TYPES = {PieceType.PAWN, PieceType.KNIGHT, PieceType.KING};
    public static final PieceType[] SLIDING_PIECES_TYPES = {PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};
}
