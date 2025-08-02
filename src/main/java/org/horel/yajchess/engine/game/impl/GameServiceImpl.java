package org.horel.yajchess.engine.game.impl;

import org.horel.yajchess.engine.board.BoardService;
import org.horel.yajchess.engine.game.GameService;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.Game;
import org.horel.yajchess.engine.model.Move;
import org.horel.yajchess.engine.move.MoveService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final MoveService moveService;

    private final BoardService boardService;

    public GameServiceImpl(MoveService moveService, BoardService boardService) {
        this.moveService = moveService;
        this.boardService = boardService;
    }

    public Game createNewGame() {
        Board board = boardService.createNewBoard();
        Game game = new Game(board);

        game.setLegalMoves(moveService.generateLegalMoves(board));
        return game;
    }

    @Override
    public void makeMove(Game game, Move move) {

    }

    @Override
    public void updateLegalMoves(Game game) {

    }
}
