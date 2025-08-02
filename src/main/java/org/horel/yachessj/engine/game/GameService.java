package org.horel.yachessj.engine.game;

import org.horel.yachessj.engine.model.Game;
import org.horel.yachessj.engine.model.Move;

public interface GameService {
    Game createNewGame();
    void makeMove(Game game, Move move);
    void updateLegalMoves(Game game);
}
