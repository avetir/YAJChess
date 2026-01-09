package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.pieceset.PieceSet;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.horel.yajchess.engine.constants.Constants.BLACK_PAWN_RANK;
import static org.horel.yajchess.engine.constants.Constants.BLACK_PIECE_RANK;
import static org.horel.yajchess.engine.constants.Constants.KING_BISHOP_FILE;
import static org.horel.yajchess.engine.constants.Constants.KING_FILE;
import static org.horel.yajchess.engine.constants.Constants.KING_KNIGHT_FILE;
import static org.horel.yajchess.engine.constants.Constants.KING_ROOK_FILE;
import static org.horel.yajchess.engine.constants.Constants.QUEEN_BISHOP_FILE;
import static org.horel.yajchess.engine.constants.Constants.QUEEN_FILE;
import static org.horel.yajchess.engine.constants.Constants.QUEEN_KNIGHT_FILE;
import static org.horel.yajchess.engine.constants.Constants.QUEEN_ROOK_FILE;
import static org.horel.yajchess.engine.constants.Constants.WHITE_PAWN_RANK;
import static org.horel.yajchess.engine.constants.Constants.WHITE_PIECE_RANK;

public class PieceFactory {

    public static Piece create(Color c, PieceType type) {
        return new Piece(c, type);
    }

    public static Map<Color, PieceSet> createInitialPieceSets(Function<List<Piece>, PieceSet> pieceSetFactory) {
        Map<Color, PieceSet> pieceSets = new HashMap<>();
        for (Color c : Color.values()){
            List<Piece> pieces = createInitialPieces(c);
            pieceSets.put(c, pieceSetFactory.apply(pieces));
        }
        return pieceSets;
    }

    private static List<Piece> createInitialPieces(Color c) {
        List<Piece> pieces = new ArrayList<>();
        int pawnRank = (c == Color.WHITE) ? WHITE_PAWN_RANK : BLACK_PAWN_RANK;
        int pieceRank = (c == Color.WHITE) ? WHITE_PIECE_RANK : BLACK_PIECE_RANK;
        char[] files = "abcdefgh".toCharArray();

        for (char f : files) {
            pieces.add(create(
                    c,
                    PieceType.PAWN,
                    PositionPool.get(f, pawnRank))
            );

            pieces.add(create(
                    c,
                    switch (f) {
                        case QUEEN_ROOK_FILE, KING_ROOK_FILE -> PieceType.ROOK;
                        case QUEEN_KNIGHT_FILE, KING_KNIGHT_FILE -> PieceType.KNIGHT;
                        case QUEEN_BISHOP_FILE, KING_BISHOP_FILE -> PieceType.BISHOP;
                        case QUEEN_FILE -> PieceType.QUEEN;
                        case KING_FILE -> PieceType.KING;
                        default -> throw new IllegalArgumentException("Wrong file: " + f);
                    },
                    PositionPool.get(f, pieceRank))
            );
        }

        return pieces;
    }

}
