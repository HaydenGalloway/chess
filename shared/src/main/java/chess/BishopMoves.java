package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMoves extends PieceMovesCalculator {
    @Override
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int[][] directions = {
                {1,1},
                {1,-1},
                {-1,1},
                {-1,-1}
        };

        for (int[] move : directions) {
            int newRow = row + move[0];
            int newCol = col + move[1];

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

                newRow += move[0];
                newCol += move[1];
            }
        }

        return validMoves;
    }
}
