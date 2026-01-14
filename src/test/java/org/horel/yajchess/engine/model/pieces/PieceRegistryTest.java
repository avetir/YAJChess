package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceRegistryTest{

    @Test
    void singlePiece() {
        var r = PieceRegistry.get(Color.WHITE, PieceType.ROOK);

        assertEquals(Color.WHITE, r.getColor());
        assertEquals(PieceType.ROOK, r.getType());
    }

    @Test
    void assembleStartingPosition() {
        var r = PieceRegistry.assembleStartingPosition();

        assertEquals(32, r.size());
        assertEquals(16, r.values().stream().filter(p -> p.getType() == PieceType.PAWN).count());
        assertEquals(4, r.values().stream().filter(p -> p.getType() == PieceType.KNIGHT).count());
        assertEquals(4, r.values().stream().filter(p -> p.getType() == PieceType.BISHOP).count());
        assertEquals(4, r.values().stream().filter(p -> p.getType() == PieceType.ROOK).count());
        assertEquals(2, r.values().stream().filter(p -> p.getType() == PieceType.QUEEN).count());
        assertEquals(2, r.values().stream().filter(p -> p.getType() == PieceType.KING).count());
        assertEquals(16, r.values().stream().filter(p -> p.getColor() == Color.WHITE).count());
        assertEquals(16, r.values().stream().filter(p -> p.getColor() == Color.WHITE).count());
    }

}
