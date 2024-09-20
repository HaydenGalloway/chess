package chess;

import java.util.Collection;
import java.util.ArrayList;

public class QueenMovesCalculator extends PieceMovesCalculator {

    private RookMovesCalculator rookMoves = new RookMovesCalculator();
    private BishopMovesCalculator bishopMoves = new BishopMovesCalculator();

    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        validMoves.addAll(rookMoves.getMoves(board, myPosition));
        validMoves.addAll(bishopMoves.getMoves(board, myPosition));

        return validMoves;
    }

}
