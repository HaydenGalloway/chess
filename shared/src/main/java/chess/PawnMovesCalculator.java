package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition piece) {

        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece pawn = board.getPiece(piece);
        ChessGame.TeamColor color = pawn.getTeamColor();

        int row = piece.getRow();
        int col = piece.getColumn();

        int direction, promotionRow;

        if (color == ChessGame.TeamColor.WHITE) {
            direction = 1;
            promotionRow = 7;
        } else {
            direction = -1;
            promotionRow = 1;
        }

        // Capture Directions
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
                        addMoves(validMoves, board, piece, endPosition, ChessPiece.PieceType.QUEEN);
                        addMoves(validMoves, board, piece, endPosition, ChessPiece.PieceType.ROOK);
                        addMoves(validMoves, board, piece, endPosition, ChessPiece.PieceType.BISHOP);
                    } else {
                        addMoves(validMoves, board, piece, endPosition, null);
                    }
                }
            }
        }

        //// Case of moving a single square forward

        int oneRow = row + direction;

        if (squareOnBoard(oneRow, col) && board.getPiece(new ChessPosition(oneRow, col)) == null) {
            ChessPosition onePosition = new ChessPosition(oneRow, col);
            if (oneRow == promotionRow) {
                addMoves(validMoves, board, piece, onePosition, ChessPiece.PieceType.QUEEN);
                addMoves(validMoves, board, piece, onePosition, ChessPiece.PieceType.ROOK);
                addMoves(validMoves, board, piece, onePosition, ChessPiece.PieceType.BISHOP);
            } else {
                addMoves(validMoves, board, piece, onePosition, null);
            }

        }


        return validMoves;
    }
}
