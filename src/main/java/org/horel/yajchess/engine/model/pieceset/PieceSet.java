package org.horel.yajchess.engine.model.pieceset;

import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.impl.King;

import java.util.List;

public interface PieceSet {
    List<Piece> getAllPieces();
    King getKing();
    PieceSet copy();
}
