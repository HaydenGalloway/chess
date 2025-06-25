package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMoves extends PieceMovesCalculator {
    @Override
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position) {

        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = position.getRow();
        int col = position.getColumn();

        int[][] directions = {
                {1,2},
                {1,-2},
                {2,1},
                {2,-1},
                {-1,2},
                {-1,-2},
                {-2,1},
                {-2,-1}
        };

        for(int[] move: directions) {

            int newRow = row + move[0];
            int newCol = col + move[1];

            if (squareOnBoard(newRow, newCol)) {
                ChessPosition endPosition = new ChessPosition(newRow, newCol);
                addMoves(validMoves, board, position, endPosition, null);
            }

        }

        return validMoves;
    }
}
