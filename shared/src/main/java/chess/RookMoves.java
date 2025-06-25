package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMoves extends PieceMovesCalculator {

    @Override
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int[][] directions = {
                {1,0},
                {0,1},
                {-1,0},
                {0,-1}
        };

        for (int[] move : directions) {

            int rowOffset = move[0];
            int colOffset = move[1];

            int newRow = row + rowOffset;
            int newCol = col + colOffset;

            while (squareOnBoard(newRow, newCol)) {

                ChessPosition endPosition = new ChessPosition(newRow, newCol);
                ChessPiece endPiece = board.getPiece(endPosition);
                ChessPiece startPiece = board.getPiece(myPosition);

                if (endPiece == null) {
                    addMoves(validMoves, board, myPosition, endPosition, null);
                } else if (startPiece.getTeamColor() != endPiece.getTeamColor()) {
                    addMoves(validMoves, board, myPosition, endPosition, null);
                    break;
                } else {
                    break;
                }

                newRow += rowOffset;
                newCol += colOffset;

            }

        }

        return validMoves;
    }
}
