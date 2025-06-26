package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoves extends PieceMovesCalculator {
    @Override
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(new RookMoves().getMoves(board, myPosition));
        validMoves.addAll(new BishopMoves().getMoves(board, myPosition));

        return validMoves;
    }
}
