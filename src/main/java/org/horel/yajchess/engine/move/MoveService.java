package org.horel.yajchess.engine.move;

import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.Move;
import org.horel.yajchess.engine.model.pieces.Piece;

import java.util.List;
import java.util.Map;

public interface MoveService {

    Map<Piece, List<Move>> generateLegalMoves(Board board);

}
