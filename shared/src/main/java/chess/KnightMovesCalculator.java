package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator extends PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<>();

//        int[][] possibleMoves = {
//                {1, 2}, {1, -2}, {2, 1}, {2, -1}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, 1}
//        };


        return validMoves;
    }

    if (squareOnBoard()) {
    }

    protected void addMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition startPosition, ChessPosition endPosition) {
        if (board.getPiece(startPosition).getTeamColor() != board.getPiece(endPosition).getTeamColor()) {
            validMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        }
        if (board.getPiece(endPosition) == null) {
            validMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        }
    }

    private boolean squareOnBoard(int row, int col) {
        return row>=0 && row<8 && col>=0 && col<8;
    }
}