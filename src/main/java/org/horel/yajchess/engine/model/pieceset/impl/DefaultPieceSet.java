package org.horel.yajchess.engine.model.pieceset.impl;

import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.pieces.impl.*;
import org.horel.yajchess.engine.model.pieceset.PieceSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPieceSet implements PieceSet {
    private final List<Pawn>   pawns;
    private final List<Knight> knights;
    private final List<Bishop> bishops;
    private final List<Rook>   rooks;
    private final List<Queen>  queens;
    private final King         king;

    public DefaultPieceSet(List<Pawn> pawns, List<Knight> knights, List<Bishop> bishops, List<Rook> rooks, List<Queen> queens, King king) {
        this.pawns = pawns;
        this.knights = knights;
        this.bishops = bishops;
        this.rooks = rooks;
        this.queens = queens;
        this.king = king;
    }

    @Override
    public List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.addAll(this.pawns);
        pieces.addAll(this.knights);
        pieces.addAll(this.bishops);
        pieces.addAll(this.rooks);
        pieces.addAll(this.queens);
        pieces.add(this.king);
        return pieces;
    }

    @Override
    public King getKing() {
        return king;
    }

    public List<? extends Piece> getNonKingPiecesByType(PieceType type) {
        return switch (type) {
            case PAWN -> pawns;
            case KNIGHT -> knights;
            case BISHOP -> bishops;
            case ROOK -> rooks;
            case QUEEN -> queens;
            default -> List.of();
        };
    }

    @Override
    public DefaultPieceSet copy() {
        List<Pawn> pawnsCopy = this.pawns.stream().map(Pawn::copy).collect(Collectors.toList());
        List<Knight> knightsCopy = this.knights.stream().map(Knight::copy).collect(Collectors.toList());
        List<Bishop> bishopsCopy = this.bishops.stream().map(Bishop::copy).collect(Collectors.toList());
        List<Rook> rooksCopy = this.rooks.stream().map(Rook::copy).collect(Collectors.toList());
        List<Queen> queensCopy = this.queens.stream().map(Queen::copy).collect(Collectors.toList());
        King kingCopy = this.king.copy();
        return new DefaultPieceSet(pawnsCopy, knightsCopy, bishopsCopy, rooksCopy, queensCopy, kingCopy);
    }
}
