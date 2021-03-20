package quentin.core;

import quentin.exceptions.*;

import java.util.Optional;
import java.util.Set;

public class GameState {
    private final Board board;
    private boolean whiteAlreadyPlayed = false;
    private Colour lastPlay = null;

    public GameState(int boardSize) {
        this.board = Board.buildBoard(boardSize);
    }

    protected Board getBoard() {
        return board;
    }

    public void setLastPlay(Colour lastPlay) {
        this.lastPlay = lastPlay;
    }

    protected void makeMove(Colour colour, Position position) throws QuentinException {
        if (isInvalidFirstPlayer(colour)) {
            throw new InvalidFirstPlayerException();
        }
        if (isARepeatedPlay(colour)) {
            throw new RepeatedPlayException();
        }
        if (board.isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }

        board.addStoneAt(colour, position);
        Set<Position> territoriesFilled = board.fillTerritories(colour);

        if (isIllegalMove(board.intersectionAt(position))) {
            territoriesFilled.add(position);
            territoriesFilled.forEach(board::revertForIntersectionAt);
            throw new IllegalMoveException(position);
        }
        lastPlay = colour;
    }

    private boolean isIllegalMove(Intersection intersection) {
        Set<Intersection> colorAlikeOrthogonalIntersections =
                board.getOrthogonallyAdjacentIntersectionsOfColour(intersection);
        return board.getDiagonallyAdjacentIntersectionsOfColour(intersection).stream()
                .anyMatch(diagonalIntersection ->
                        board.getOrthogonallyAdjacentIntersectionsOfColour(diagonalIntersection).stream()
                                .noneMatch(colorAlikeOrthogonalIntersections::contains)
                );
    }

    private boolean isARepeatedPlay(Colour currentPlayerColour) {
        return lastPlay == currentPlayerColour;
    }

    private boolean isFirstTurn() {
        return Optional.ofNullable(lastPlay).isEmpty();
    }

    protected boolean isInvalidFirstPlayer(Colour playerColour) {
        return isFirstTurn() && playerColour == Colour.WHITE;
    }

    public boolean isCurrentPlayerNotAbleToMakeAMove(Player currentPlayer) {
        return board.getEmptyIntersections()
                .allMatch(emptyIntersection -> {
                    board.addStoneAt(currentPlayer.getColor(), emptyIntersection.getPosition());
                    Set<Position> positionsFilled = this.board.fillTerritories(lastPlay);
                    positionsFilled.add(emptyIntersection.getPosition());
                    boolean isIllegalMove = isIllegalMove(emptyIntersection);
                    positionsFilled.forEach(board::revertForIntersectionAt);
                    return isIllegalMove;
                });
    }

    protected Optional<Colour> getWinner() {
        return board.colorWithCompleteChain();
    }

    protected boolean doesWhitePlayerAlreadyPlayed() {
        return this.whiteAlreadyPlayed;
    }

    public void setWhiteAlreadyPlayed(boolean whiteAlreadyPlayed) {
        this.whiteAlreadyPlayed = whiteAlreadyPlayed;
    }

    protected Colour getCurrentPlayerColour() {
        return Optional.ofNullable(lastPlay).map(Colour::getOppositeColor).orElse(Colour.BLACK);
    }
}