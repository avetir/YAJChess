package org.horel.yajchess.engine.model.pieces;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.enums.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceFactoryTest {

    @Test
    void createSinglePiece() {
        var r = PieceFactory.create(Color.WHITE, PieceType.ROOK);

        assertEquals(Color.WHITE, r.getColor());
        assertEquals(PieceType.ROOK, r.getType());
    }

}
