package org.horel.yajchess.engine.model;

import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.enums.MoveType;

public class Move {
        Position from;
        Position to;
        Piece movedPiece;
        Piece capturedPiece;
        PieceType promotionPieceType;
        MoveType moveType;
        String algebraic;

    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece, MoveType moveType) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.moveType = moveType;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public PieceType getPromotionPieceType() {
        return promotionPieceType;
    }

    public void setPromotionPieceType(PieceType promotionPieceType) {
        this.promotionPieceType = promotionPieceType;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public String getAlgebraic() {
        return algebraic;
    }

    public void setAlgebraic(String algebraic) {
        this.algebraic = algebraic;
    }

}
