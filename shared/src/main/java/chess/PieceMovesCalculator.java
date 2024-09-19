package chess;

import java.util.Collection;

public abstract class PieceMovesCalculator {
    abstract public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition piece);

//    protected boolean squareOnBoard(int row, int col) {
//        return row>=0 && row<8 && col>=0 && col<8;
//    }
}
