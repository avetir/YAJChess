package org.horel.yachessj.engine.model;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.model.pieces.Piece;
import org.horel.yachessj.engine.model.pieceset.PieceSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    final Tile[][] tileSet;
    final Map<Color, PieceSet> pieceSets;

    public Board() {
        tileSet = new Tile[8][8];
        pieceSets = new HashMap<>();
    }

    public Tile[][] getTileSet(){
        return this.tileSet;
    }

    public Tile getTile(Position pos) {
        return tileSet[pos.file() - 'a'][pos.rank() - 1];
    }

    public Map<Color, PieceSet> getPieceSets(){
        return this.pieceSets;
    }

    public List<Piece> getAllPieces(){
        return this.pieceSets.values().stream().map(PieceSet::getAllPieces).flatMap(List::stream).collect(Collectors.toList());
    }

    public Piece getPiece(Position pos) {
        return getTile(pos).getPiece();
    }

    public void placePiece(Position pos, Piece piece) {
        getTile(pos).placePiece(piece);
    }

    public void movePiece(Position from, Position to) {
        Tile fromTile = getTile(from);
        Tile toTile = getTile(to);
        Piece piece = fromTile.getPiece();

        fromTile.removePiece();
        piece.setPosition(to);
        toTile.placePiece(piece);
    }

    public Board copy(){
        Board board = new Board();
        for (int i = 0; i < 8; i++) {
            System.arraycopy(this.tileSet[i], 0, board.tileSet[i], 0, 8);
        }
        this.pieceSets.forEach((color, pieceSet) -> board.pieceSets.put(color, pieceSet.copy()));
        return board;
    }

}
