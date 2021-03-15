package quentin.core;

import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;
import quentin.exceptions.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public abstract class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    private final Board board;
    protected boolean whiteAlreadyPlayed = false;
    private Colour lastPlay = null;
    private final Player playerOne;
    private final Player playerTwo;
    protected final InputHandlerImplementation inputHandler;
    protected final OutputHandlerImplementation outputHandler;

    protected Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler,
                   String blackPlayerName, String whitePlayerName) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Colour.BLACK, blackPlayerName);
        this.playerTwo = new Player(Colour.WHITE, whitePlayerName);
    }

    protected void makeMove(Colour colour, Position position) throws QuentinException {
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

    private boolean isOccupied(Position position) throws OutsideOfBoardException {
        return board.isOccupied(position);
    }

    private boolean isIllegalMove(Colour playerColour, Position position) throws OutsideOfBoardException {
        return isIllegalMove(playerColour, board.intersectionAt(position));
    }

    private boolean isIllegalMove(Colour playerColour, Intersection intersection) {
        Set<Intersection> colorAlikeOrthogonalIntersections =
                board.getOrthogonallyAdjacentIntersectionsOfColour(intersection, playerColour);
        return board.getDiagonallyAdjacentIntersectionsOfColour(intersection, playerColour).stream()
                .anyMatch(diagonalIntersection ->
                        board.getOrthogonallyAdjacentIntersectionsOfColour(diagonalIntersection, playerColour).stream()
                            .noneMatch(colorAlikeOrthogonalIntersections::contains)
                );
    }

    private boolean isARepeatedPlay(Colour playerColour) {
        return lastPlay == playerColour;
    }

    private boolean isFirstTurn() {
        return Optional.ofNullable(lastPlay).isEmpty();
    }

    private boolean isInvalidFirstPlayer(Colour playerColour) {
        return isFirstTurn() && playerColour == Colour.WHITE;
    }

    public boolean isCurrentPlayerNotAbleToMakeAMove() {
        return board.getEmptyIntersections()
                .allMatch(emptyIntersection -> {
                    board.addStoneAt(getCurrentPlayer().getColor(), emptyIntersection.getPosition());
                    Set<Position> positionsFilled = board.fillTerritories(lastPlay);
                    positionsFilled.add(emptyIntersection.getPosition());
                    boolean isIllegalMove = isIllegalMove(getCurrentPlayer().getColor(), emptyIntersection);
                    positionsFilled.forEach(board::revertForIntersectionAt);
                    return isIllegalMove;
                });
    }

    protected Player getPlayerOfColor(Colour colour) {
        return getPlayers().stream().filter(player -> player.getColor() == colour).findFirst().orElseThrow();
    }

    public void passTurn() {
        Player currentPlayer = getCurrentPlayer();
        this.lastPlay = currentPlayer.getColor();
        outputHandler.notifyPass(currentPlayer);
    }

    protected boolean checkForWinner() {
        return getWinner().map(winnerColor -> {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return true;
        }).orElse(false);
    }

    protected Optional<Colour> getWinner() {
        return board.colorWithCompleteChain();
    }

    protected void applyPieRule() {
        Stream.of(playerOne, playerTwo).forEach(Player::changeSide);
    }

    protected List<Player> getPlayers() {
        return List.of(playerOne, playerTwo);
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        return isFirstTurn() ? getPlayerOfColor(Colour.BLACK) : getPlayerOfColor(this.lastPlay.getOppositeColor());
    }

    protected boolean applyPieRuleIfPlayerWants(Player currentPlayer) {
        if (inputHandler.askPie(currentPlayer.getName())) {
            applyPieRule();
            outputHandler.notifyPieRule(getPlayers());
            return true;
        } else {
            return false;
        }
    }

    public abstract void play() throws QuentinException;
}