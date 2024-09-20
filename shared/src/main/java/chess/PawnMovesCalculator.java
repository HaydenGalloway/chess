package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece pawn = board.getPiece(myPosition);
        ChessGame.TeamColor color = pawn.getTeamColor();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int direction, promotionRow, startRow;

        if (color == ChessGame.TeamColor.WHITE) {
            direction = 1;
            promotionRow = 8;
            startRow = 2;
        } else {
            direction = -1;
            promotionRow = 1;
            startRow = 7;
        }

        // Capture Directions]

        int[][] directions = {
                {direction, 1},
                {direction, -1}
        };

        // Iterate through Capture Directions

        for (int[] move: directions) {

            int newRow = row + move[0];
            int newCol = col + move[1];

            if (squareOnBoard(newRow, newCol)) {

                ChessPosition endPosition = new ChessPosition(newRow, newCol);
                ChessPiece endPiece = board.getPiece(endPosition);

                if (endPiece != null && endPiece.getTeamColor() != color) {
                    if (newRow == promotionRow) {
                        addMoves(validMoves, board, myPosition, endPosition, ChessPiece.PieceType.QUEEN);
                        addMoves(validMoves, board, myPosition, endPosition, ChessPiece.PieceType.ROOK);
                        addMoves(validMoves, board, myPosition, endPosition, ChessPiece.PieceType.BISHOP);
                        addMoves(validMoves, board, myPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                    } else {
                        addMoves(validMoves, board, myPosition, endPosition, null);
                    }
                }
            }
        }

        //// Case of moving a single square forward

        int oneRow = row + direction;

        if (squareOnBoard(oneRow, col) && board.getPiece(new ChessPosition(oneRow, col)) == null) {
            ChessPosition onePosition = new ChessPosition(oneRow, col);
            if (oneRow == promotionRow) {
                addMoves(validMoves, board, myPosition, onePosition, ChessPiece.PieceType.QUEEN);
                addMoves(validMoves, board, myPosition, onePosition, ChessPiece.PieceType.ROOK);
                addMoves(validMoves, board, myPosition, onePosition, ChessPiece.PieceType.BISHOP);
                addMoves(validMoves, board, myPosition, onePosition, ChessPiece.PieceType.KNIGHT);
            } else {
                addMoves(validMoves, board, myPosition, onePosition, null);
            }

            //// Case of starting positions, two squares forward

            if (row == startRow) {
                int twoRow = row + (2 * direction);
                if (squareOnBoard(twoRow, col) && board.getPiece(new ChessPosition(twoRow, col)) == null) {
                    ChessPosition twoPosition = new ChessPosition(twoRow, col);
                    addMoves(validMoves, board, myPosition, twoPosition, null);
                }
            }
        }


        return validMoves;
    }
}
