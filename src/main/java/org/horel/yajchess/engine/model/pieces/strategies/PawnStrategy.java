package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy extends BaseMoveStrategy {
    private static final int[] REGULAR_MOVE = {0, 1};
    private static final int[] LONG_FIRST_MOVE = {0, 2};
    private static final int[][] DIAGONALS = {{1, 1}, {-1, 1}};

    @Override
    public List<Position> generateAttacks(Board b, Position from){
        List<Position> attacks = new ArrayList<>();
        Piece p = requirePiece(b, from);
        int direction = p.getColor() == Color.WHITE ? 1 : -1;
        
        for (int[] offset : DIAGONALS) {
            Position posTo = getStepPosition(from, applyDirection(offset, direction));
            if (posTo != null){
                attacks.add(posTo);
            }
        }
        return attacks;
    }
    
    @Override
    public List<Position> generateMoves(Board b, Position from){
        List<Position> moves = new ArrayList<>();
        Piece p = requirePiece(b, from);
        Color c = p.getColor();
        int direction = c == Color.WHITE ? 1 : -1;

        Position oneSquareMove = getStepPosition(from, applyDirection(REGULAR_MOVE, direction));
        if (isEmpty(b, oneSquareMove)) {
            moves.add(oneSquareMove);
            
            Position twoSquaresMove = getStepPosition(from, applyDirection(LONG_FIRST_MOVE, direction));
            boolean isOnStartRank = c == Color.WHITE && from.rank() == 2 ||
                    c == Color.BLACK && from.rank() == 7;
            
            if (isOnStartRank && isEmpty(b, twoSquaresMove)) {
                moves.add(twoSquaresMove);
            }
        }
        
        for (int[] offset : DIAGONALS) {
            Position posTo = getStepPosition(from, applyDirection(offset, direction));
            if (isOccupiedByEnemyPiece(b, c, posTo)){
                moves.add(posTo);
            }
        }

        return moves;
    }

    private int[] applyDirection(int[] offset, int direction){
        return new int[]{offset[0], offset[1] * direction};
    }
}
