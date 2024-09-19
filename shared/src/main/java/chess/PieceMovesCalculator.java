package chess;

import java.util.Collection;

public abstract class PieceMovesCalculator {
    abstract public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition piece);

    protected boolean squareOnBoard(int row, int col) {
        return row>=1 && row<=8 && col>=1 && col<=8;
    }

    protected void addMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition startPosition,
                            ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {

        ChessPiece startPiece = board.getPiece(startPosition);
        ChessPiece endPiece = board.getPiece(endPosition);

        if (endPiece == null) {
            validMoves.add(new ChessMove(startPosition, endPosition, promotionPiece));
        } else if (startPiece.getTeamColor() != endPiece.getTeamColor()) {
            validMoves.add(new ChessMove(startPosition, endPosition, promotionPiece));
        }
    }
}
