package org.horel.yachessj.engine.move;

import org.horel.yachessj.engine.model.Board;
import org.horel.yachessj.engine.model.Move;
import org.horel.yachessj.engine.model.pieces.Piece;

import java.util.List;
import java.util.Map;

public interface MoveService {

    Map<Piece, List<Move>> generateLegalMoves(Board board);

}
