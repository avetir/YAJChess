package org.horel.yajchess.engine.model.pieces.strategies;

import org.horel.yajchess.engine.enums.PieceType;
import org.horel.yajchess.engine.model.Board;
import org.horel.yajchess.engine.model.pieces.Piece;
import org.horel.yajchess.engine.model.positions.Position;

import java.util.ArrayList;
import java.util.List;

public class CastlingStrategy extends BaseMoveStrategy{
    
    public static final int[] KINGSIDE_CASTLING_OFFSET = {2, 0};
    public static final int[] QUEENSIDE_CASTLING_OFFSET = {-2, 0};
    
    @Override
    public List<Position> generateAttacks(Board b, Position from){
        return List.of();
    }
    
    @Override
    public List<Position> generateMoves(Board b, Position from){
        List<Position> moves = new ArrayList<>();
        
        Piece king = b.requirePiece(from);
        if(king.getType() != PieceType.KING){
            return List.of();
        }
        
        if(b.canCastleKingSide(king.getColor())){
            Position posTo = getStepPosition(from, KINGSIDE_CASTLING_OFFSET);
            moves.add(posTo);
        }
        
        if(b.canCastleQueenSide(king.getColor())){
            Position posTo = getStepPosition(from, QUEENSIDE_CASTLING_OFFSET);
            moves.add(posTo);
        }
        return moves;
    }
}

