package org.horel.yachessj.engine.model.pieceset;

import org.horel.yachessj.engine.model.pieces.Piece;
import org.horel.yachessj.engine.model.pieces.impl.King;

import java.util.List;

public interface PieceSet {
    List<Piece> getAllPieces();
    King getKing();
    PieceSet copy();
}
