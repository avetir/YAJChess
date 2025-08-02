package org.horel.yajchess.engine.model;

import static org.horel.yajchess.engine.constants.Constants.FIRST_FILE;
import static org.horel.yajchess.engine.constants.Constants.FIRST_RANK;
import static org.horel.yajchess.engine.constants.Constants.LAST_FILE;
import static org.horel.yajchess.engine.constants.Constants.LAST_RANK;

public record Position(char file, int rank) {

    public Position {
        if (file < 'a' || file > 'h') {
            throw new IllegalArgumentException("File must be in a-h range, got " + file);
        }
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank must be in 1-8 range, got " + rank);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }

    @Override
    public String toString() {
        return String.format("%s%d", file, rank);
    }

    public boolean isValid(){
        return file >= FIRST_FILE && file <= LAST_FILE && rank >= FIRST_RANK && rank <= LAST_RANK;
    }
}
