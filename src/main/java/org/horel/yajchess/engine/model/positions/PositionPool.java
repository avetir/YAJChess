package org.horel.yajchess.engine.model.positions;


import static org.horel.yajchess.engine.constants.Constants.FIRST_FILE;
import static org.horel.yajchess.engine.constants.Constants.FIRST_RANK;
import static org.horel.yajchess.engine.constants.Constants.LAST_FILE;
import static org.horel.yajchess.engine.constants.Constants.LAST_RANK;

public class PositionPool {

    private static final Position[][] POSITIONS = new Position[8][8];

    static {
        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) {
                POSITIONS[f][r] = new Position((char) ('a' + f), r + 1);
            }
        }
    }

    public static Position get(char file, int rank) {
        if (file < FIRST_FILE || file > LAST_FILE || rank < FIRST_RANK || rank > LAST_RANK) {
            return null;
        }
        return POSITIONS[file - 'a'][rank - 1];
    }

}
