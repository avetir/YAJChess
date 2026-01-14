package org.horel.yajchess.engine.constants;

public final class Castling{
    
    public static final int WHITE_KINGSIDE  = 1;
    public static final int WHITE_QUEENSIDE = 1 << 1;
    public static final int BLACK_KINGSIDE  = 1 << 2;
    public static final int BLACK_QUEENSIDE = 1 << 3;
    public static final int ALL = WHITE_KINGSIDE | WHITE_QUEENSIDE |
            BLACK_KINGSIDE | BLACK_QUEENSIDE;
}
