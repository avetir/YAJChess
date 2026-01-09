package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.enums.Color;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.MoveStrategy;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.positions.Position;
import org.horel.yajchess.engine.model.positions.PositionPool;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMoveStrategy implements MoveStrategy {
    
    @Override
    public List<Position> generateAttacks(Board b, Position from){
        return generateMoves(b, from);
    }

    protected boolean isEmpty(Board b, Position pos){
        return pos != null && b.getPiece(pos) == null;
    }
    
    protected Piece requirePiece(Board b, Position from) {
        Piece p = b.getPiece(from);
        if (p == null) throw new IllegalArgumentException("No piece at " + from);
        return p;
    }
    
    protected boolean isOccupiedByOwnPiece(Board b, Color color, Position posTo){
        Piece targetPiece = posTo == null ? null : b.getPiece(posTo);
        return targetPiece != null && targetPiece.getColor() == color;
    }

    protected boolean isOccupiedByEnemyPiece(Board b, Color color, Position posTo){
        Piece targetPiece = posTo == null ? null : b.getPiece(posTo);
        return targetPiece != null && targetPiece.getColor() != color;
    }

    protected Position getStepPosition(Position pos, int[] offset){
        char f = (char) (pos.file() + offset[0]);
        int r = pos.rank() + offset[1];
        return PositionPool.get(f, r);
    }

    protected List<Position> collectSteppingMoves(Board b, Position from, int[][] offsets){
        List<Position> moves = new ArrayList<>();
        Color color = requirePiece(b, from).getColor();

        for (int[] offset : offsets){
            Position posTo = getStepPosition(from, offset);
            if (posTo != null && !isOccupiedByOwnPiece(b, color, posTo)){
                moves.add(posTo);
            }
        }
        return moves;
    }

    protected List<Position> collectSlidingMoves(Board b, Position from, int[][] directions){
        List<Position> moves = new ArrayList<>();
        Color color = requirePiece(b, from).getColor();

        for (int[] dir : directions) {
            int fTo = from.file();
            int rTo = from.rank();

            while (true) {
                fTo += dir[0];
                rTo += dir[1];

                Position posTo = PositionPool.get((char)fTo, rTo);
                if (posTo == null){
                    break;
                }

                Piece target = b.getPiece(posTo);

                if (target == null){
                    moves.add(posTo);
                } else {
                    if (target.getColor() != color){
                        moves.add(posTo);
                    }
                    break;
                }
            }
        }
        return moves;
    }
}
