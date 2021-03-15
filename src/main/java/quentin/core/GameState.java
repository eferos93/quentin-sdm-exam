package quentin.core;

import quentin.exceptions.*;

import java.util.Optional;
import java.util.Set;

public class GameState {
    public final Board board;
    public boolean whiteAlreadyPlayed = false;
    public Colour lastPlay = null;

    public GameState(int boardSize) {
        this.board = Board.buildBoard(boardSize);
    }

    public Board getBoard() {
        return board;
    }

    public Colour getLastPlay() {
        return lastPlay;
    }

    public void setLastPlay(Colour lastPlay) {
        this.lastPlay = lastPlay;
    }

    public void makeMove(Colour colour, Position position) throws QuentinException {
        if (isInvalidFirstPlayer(colour)) {
            throw new InvalidFirstPlayerException();
        }
        if (isARepeatedPlay(colour)) {
            throw new RepeatedPlayException();
        }
        if (isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }

        board.addStoneAt(colour, position);
        Set<Position> territoriesFilled = board.fillTerritories(colour);

        if (isIllegalMove(colour, position)) {
            territoriesFilled.add(position);
            territoriesFilled.forEach(board::revertForIntersectionAt);
            throw new IllegalMoveException(position);
        }
        lastPlay = colour;
    }

    public boolean isOccupied(Position position) throws OutsideOfBoardException {
        return board.isOccupied(position);
    }

    public boolean isIllegalMove(Colour playerColour, Position position) throws OutsideOfBoardException {
        return isIllegalMove(playerColour, board.intersectionAt(position));
    }

    public boolean isIllegalMove(Colour playerColour, Intersection intersection) {
        Set<Intersection> colorAlikeOrthogonalIntersections =
                board.getOrthogonallyAdjacentIntersectionsOfColour(intersection, playerColour);
        return board.getDiagonallyAdjacentIntersectionsOfColour(intersection, playerColour).stream()
                .anyMatch(diagonalIntersection ->
                        board.getOrthogonallyAdjacentIntersectionsOfColour(diagonalIntersection, playerColour).stream()
                                .noneMatch(colorAlikeOrthogonalIntersections::contains)
                );
    }

    public boolean isARepeatedPlay(Colour currentPlayerColour) {
        return lastPlay == currentPlayerColour;
    }

    public boolean isFirstTurn() {
        return Optional.ofNullable(lastPlay).isEmpty();
    }

    public boolean isInvalidFirstPlayer(Colour playerColour) {
        return isFirstTurn() && playerColour == Colour.WHITE;
    }

    public boolean isCurrentPlayerNotAbleToMakeAMove(Player currentPlayer) {
        return board.getEmptyIntersections()
                .allMatch(emptyIntersection -> {
                    board.addStoneAt(currentPlayer.getColor(), emptyIntersection.getPosition());
                    Set<Position> positionsFilled = board.fillTerritories(lastPlay);
                    positionsFilled.add(emptyIntersection.getPosition());
                    boolean isIllegalMove = isIllegalMove(currentPlayer.getColor(), emptyIntersection);
                    positionsFilled.forEach(board::revertForIntersectionAt);
                    return isIllegalMove;
                });
    }

    protected Optional<Colour> getWinner() {
        return board.colorWithCompleteChain();
    }

    protected boolean doesWhitePlayerAlreadyPlayed() {
        return whiteAlreadyPlayed;
    }

    public void setWhiteAlreadyPlayed(boolean whiteAlreadyPlayed) {
        this.whiteAlreadyPlayed = whiteAlreadyPlayed;
    }
}