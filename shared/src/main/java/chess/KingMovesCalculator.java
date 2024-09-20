package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KingMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1},
                {1, 1},
                {-1, 1},
                {1, -1},
                {-1, -1}
        };

        for (int[] move : directions) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (squareOnBoard(newRow, newCol)) {
                ChessPosition endPosition = new ChessPosition(newRow, newCol);
                addMoves(validMoves, board, myPosition, endPosition, null);
            }
        }

        return validMoves;
    }
}
