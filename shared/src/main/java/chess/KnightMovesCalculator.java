package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();

        int startRow = myPosition.getRow();
        int startColumn = myPosition.getColumn();

        int[][] possibleMoves = {
                {1, 2}, {1, -2}, {2, 1}, {2, -1},
                {-1, 2}, {-1, -2}, {-2, 1}, {-2, 1}
        };

        for (int[] move : possibleMoves) {
            int newRow = startRow + move[0];
            int newCol = startColumn + move[1];

            if (squareOnBoard(newRow, newCol)) {
                ChessPosition endPosition = new ChessPosition(newRow, newCol);
                addMoves(validMoves, board, myPosition, endPosition, null);
            }
        }

        return validMoves;
    }
}