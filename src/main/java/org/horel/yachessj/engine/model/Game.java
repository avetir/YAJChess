package org.horel.yachessj.engine.model;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.GameStatus;
import org.horel.yachessj.engine.model.pieces.Piece;

import java.util.*;

public class Game {
    private final String id = UUID.randomUUID().toString();
    private final Board board;
    private final Map<Piece, List<Move>> legalMoves = new HashMap<>();
    private Color turn;
    private final List<Move> movesHistory = new ArrayList<>();
    private GameStatus status;

    public Game(Board board) {
        this.board = board;
        this.turn = Color.WHITE;
    }

    public String getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Piece, List<Move>> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(Map<Piece, List<Move>> legalMoves) {
        legalMoves.forEach((piece, moves) ->
            this.legalMoves.put(piece, piece.isCaptured() ? List.of() : moves)
        );
    }

    public Color getTurn() {
        return turn;
    }

    public void setTurn(Color color) {
        turn = color;
    }

    public List<Move> getMovesHistory() {
        return movesHistory;
    }

    public void addMoveToHistory(Move move) {
        movesHistory.add(move);
    }

    public void removeMoveFromHistory(Move move) {
        if (!movesHistory.isEmpty()) {
            movesHistory.removeLast();
        }
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}




