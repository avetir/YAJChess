package org.horel.yachessj.engine.board;

import org.horel.yachessj.engine.enums.Color;
import org.horel.yachessj.engine.enums.PieceType;
import org.horel.yachessj.engine.model.Board;
import org.horel.yachessj.engine.model.Position;
import org.horel.yachessj.engine.model.pieces.Piece;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Test
    void testInitialBoardSetup() {
        Board board = boardService.createNewBoard();

        PieceType[] piecesOrderAToH = new PieceType[]{
                PieceType.ROOK,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.QUEEN,
                PieceType.KING,
                PieceType.BISHOP,
                PieceType.KNIGHT,
                PieceType.ROOK
        };

        for (char file = 'a'; file <= 'h'; file++) {
            Piece piece = board.getPiece(new Position(file, 1));
            assertNotNull(piece, "White piece must be at " + file + "1");
            assertEquals(Color.WHITE, piece.getColor());
            assertEquals(piecesOrderAToH[file-'a'], piece.getType());
            assertEquals(piece.getPosition(), new Position(file, 1));

            Piece pawn = board.getPiece(new Position(file, 2));
            assertNotNull(pawn, "White pawn must be at " + file + "2");
            assertEquals(Color.WHITE, pawn.getColor());
            assertEquals(PieceType.PAWN, pawn.getType());
            assertEquals(pawn.getPosition(), new Position(file, 2));

            pawn = board.getPiece(new Position(file, 7));
            assertNotNull(pawn, "Black pawn must be at " + file + "7");
            assertEquals(PieceType.PAWN, pawn.getType());
            assertEquals(Color.BLACK, pawn.getColor());
            assertEquals(pawn.getPosition(), new Position(file, 7));

            piece = board.getPiece(new Position(file, 8));
            assertNotNull(piece, "Black piece must be at " + file + "8");
            assertEquals(Color.BLACK, piece.getColor());
            assertEquals(piecesOrderAToH[file-'a'], piece.getType());
            assertEquals(piece.getPosition(), new Position(file, 8));

            for (int rank = 3; rank <= 6; rank++) {
                piece = board.getPiece(new Position(file, rank));
                assertNull(piece, "Tile " + file + rank + " must be empty");
            }
        }
    }
}
