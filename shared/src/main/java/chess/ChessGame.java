package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor turn;
    private ChessBoard board;

    public ChessGame() {
        turn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "turn=" + turn +
                ", board=" + board +
                '}';
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        ChessPiece piece = getBoard().getPiece(startPosition);

        if (piece == null || piece.getTeamColor() != turn) {
            return null;
        }

        return piece.pieceMoves(board, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPiece piece = board.getPiece(move.getStartPosition());

        if (piece == null || piece.getTeamColor() != turn) {
            throw new InvalidMoveException("Invalid move.");
        }

        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        if (validMoves == null || !validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move.");
        }

        board.addPiece(move.getEndPosition(), piece);
        board.addPiece(move.getStartPosition(), null);

        if (move.getPromotionPiece() != null && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            ChessPiece promotionPiece = new ChessPiece(turn, move.getPromotionPiece());
            board.addPiece(move.getEndPosition(), promotionPiece);
        }

        turn = (turn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = getKingPosition(teamColor);

        if (kingPosition == null) {
            return false;
        }

        return underAttack(kingPosition, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    private ChessPosition getKingPosition(TeamColor teamColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition kingPosition = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = board.getPiece(kingPosition);

                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING &&
                        piece.getTeamColor() == teamColor) {
                    return kingPosition;
                }
            }
        }
        return null;
    }


    private boolean underAttack(ChessPosition position, TeamColor defendingTeam) {

        TeamColor attackingTeam = (defendingTeam == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

        for (int row=0; row < 8; row++) {
            for (int col=0; col<8; col++) {
                ChessPosition attackPosition = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = board.getPiece(attackPosition);

                if (piece != null && piece.getTeamColor() == attackingTeam) {

                    Collection<ChessMove> moves = piece.pieceMoves(board, attackPosition);

                    for (ChessMove move : moves) {
                        if (move.getEndPosition().equals(position)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canEscapeCheck(TeamColor teamColor) {
        for (int row=0; row<8; row++) {
            for (int col=0; col<8; col++) {
                ChessPosition escapePosition = new ChessPosition(row + 1, col + 1);
                ChessPiece escapePiece = board.getPiece(escapePosition);

                if (escapePiece != null && escapePiece.getTeamColor() != teamColor) {
                    Collection<ChessMove> escapeMoves = escapePiece.pieceMoves(board, escapePosition);

                    for (ChessMove move : escapeMoves) {
                            //// I need to see how to clone the board and simulate the possible moves.
                    }


                }
            }
        }
    }


}
