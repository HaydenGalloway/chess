package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMoves extends PieceMovesCalculator {

    @Override
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece pawn = board.getPiece(myPosition);
        ChessGame.TeamColor color = pawn.getTeamColor();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int direction;
        int startRow;
        int promotionRow;

        if (color == ChessGame.TeamColor.WHITE) {
            direction = 1;
            startRow = 2;
            promotionRow = 8;
        } else {
            direction = -1;
            startRow = 7;
            promotionRow = 1;
        }

        int oneStepRow = row + direction;

        if (squareOnBoard(oneStepRow, col)) {
            ChessPosition oneStepPos = new ChessPosition(oneStepRow, col);

            if (board.getPiece(oneStepPos) == null) {
                if (oneStepRow == promotionRow) {
                    addMoves(validMoves, board, myPosition, oneStepPos, ChessPiece.PieceType.QUEEN);
                    addMoves(validMoves, board, myPosition, oneStepPos, ChessPiece.PieceType.ROOK);
                    addMoves(validMoves, board, myPosition, oneStepPos, ChessPiece.PieceType.BISHOP);
                    addMoves(validMoves, board, myPosition, oneStepPos, ChessPiece.PieceType.KNIGHT);
                } else {
                    addMoves(validMoves, board, myPosition, oneStepPos, null);

                    if (row == startRow) {
                        int twoStepRow = row + 2 * direction;
                        if (squareOnBoard(twoStepRow, col)) {
                            ChessPosition twoStepPos = new ChessPosition(twoStepRow, col);
                            if (board.getPiece(twoStepPos) == null) {
                                addMoves(validMoves, board, myPosition, twoStepPos, null);
                            }
                        }
                    }
                }
            }
        }

        int[] captureOffsets = new int[]{-1, 1};

        for (int offset : captureOffsets) {

            int captureRow = row + direction;
            int captureCol = col + offset;

            if (squareOnBoard(captureRow, captureCol)) {

                ChessPosition capturePos = new ChessPosition(captureRow, captureCol);
                ChessPiece pieceAtCapture = board.getPiece(capturePos);

                if (pieceAtCapture != null && pieceAtCapture.getTeamColor() != color) {

                    if (captureRow == promotionRow) {
                        addMoves(validMoves, board, myPosition, capturePos, ChessPiece.PieceType.QUEEN);
                        addMoves(validMoves, board, myPosition, capturePos, ChessPiece.PieceType.ROOK);
                        addMoves(validMoves, board, myPosition, capturePos, ChessPiece.PieceType.BISHOP);
                        addMoves(validMoves, board, myPosition, capturePos, ChessPiece.PieceType.KNIGHT);
                    } else {
                        addMoves(validMoves, board, myPosition, capturePos, null);
                    }
                }
            }
        }
        return validMoves;
    }
}