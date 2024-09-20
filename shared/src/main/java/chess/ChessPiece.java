package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }


    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        if (type == PieceType.KNIGHT) {
                return new KnightMovesCalculator().getMoves(board, myPosition);
        } else if (type == PieceType.ROOK) {
            return new RookMovesCalculator().getMoves(board, myPosition);
        } else if (type == PieceType.BISHOP) {
            return new BishopMovesCalculator().getMoves(board, myPosition);
        } else if (type == PieceType.QUEEN) {
            return new QueenMovesCalculator().getMoves(board, myPosition);
        }
//        else if (type == PieceType.KING) {
//            return new KingMovesCalculator().getMoves(board, myPosition);
//        } else if (type == PieceType.PAWN) {
//            return new PawnMovesCalculator().getMoves(board, myPosition);
//        }
        else {
            return null;
        }

    }
}
