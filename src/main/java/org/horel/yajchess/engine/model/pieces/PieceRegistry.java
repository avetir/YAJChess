package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;

import java.util.HashMap;
import java.util.Map;

import static org.horel.yajchess.engine.constants.Constants.*;

public final class PieceRegistry{
    public static final PieceType[] BACK_RANK_PIECE_TYPES = {
            PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
            PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
    };

    public static final Piece[][] CANON = new Piece[Color.values().length][PieceType.values().length];
    
    static {
        for (Color c : Color.values()){
            for (PieceType pt : PieceType.values()){
                CANON[c.ordinal()][pt.ordinal()] = new Piece(c, pt);
            }
        }
    }
    
    private PieceRegistry(){}
    
    public static Piece get(Color c, PieceType pt) {
        return CANON[c.ordinal()][pt.ordinal()];
    }

    public static Map<Position, Piece> assembleStartingPosition() {
        Map<Position, Piece> pieces = new HashMap<>(64);
        for (Color c : Color.values()){
            pieces.putAll(assembleStartingPositionByColor(c));
        }
        return pieces;
    }

    private static Map<Position, Piece> assembleStartingPositionByColor(Color c) {
        Map<Position, Piece> pieces = new HashMap<>(32);
        int pawnRank = (c == Color.WHITE) ? WHITE_PAWN_RANK : BLACK_PAWN_RANK;
        int pieceRank = (c == Color.WHITE) ? WHITE_PIECE_RANK : BLACK_PIECE_RANK;
        
        for (char file = 'a'; file <= 'h'; file++) {
            pieces.put(PositionPool.get(file, pawnRank), get(c, PieceType.PAWN));

            int i = file - 'a';
            pieces.put(PositionPool.get(file, pieceRank), get(c, BACK_RANK_PIECE_TYPES[i]));
        }
        return pieces;
    }
}
