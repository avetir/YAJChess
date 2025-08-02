package org.horel.yajchess.engine.game;

import org.horel.yajchess.engine.model.Game;
import org.horel.yajchess.engine.model.Move;

public interface GameService {
    Game createNewGame();
    void makeMove(Game game, Move move);
    void updateLegalMoves(Game game);
}
