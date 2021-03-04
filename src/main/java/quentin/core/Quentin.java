package quentin.core;

import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;
import quentin.exceptions.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public abstract class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    private final Board board;
    protected boolean whiteAlreadyPlayed = false;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne;
    private final Player playerTwo;
    protected final InputHandlerImplementation inputHandler;
    protected final OutputHandlerImplementation outputHandler;

    protected Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler,
                   String blackPlayerName, String whitePlayerName) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Stone.BLACK, blackPlayerName);
        this.playerTwo = new Player(Stone.WHITE, whitePlayerName);
    }

    protected void makeMove(Stone color, Position position) {

        if (isInvalidFirstPlayer(color)) {
            throw new InvalidFirstPlayerException();
        }
        if (isARepeatedPlay(color)) {
            throw new RepeatedPlayException();
        }
        if (isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }
        if (isIllegalMove(color, position)) {
            throw new IllegalMoveException(position);
        }

        board.addStoneAt(color, position);
        lastPlay = color;
    }

    private boolean isOccupied(Position position) throws OutsideOfBoardException {
        return board.isOccupied(position);
    }

    private boolean isIllegalMove(Stone playerColor, Position position) throws OutsideOfBoardException {
        return isIllegalMove(playerColor, board.intersectionAt(position));
    }

    private boolean isIllegalMove(Stone playerColor, Intersection intersection) {
        return board.existsDiagonallyAdjacentWithStone(intersection, playerColor) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, playerColor);
    }

    private boolean isARepeatedPlay(Stone playerColor) {
        return lastPlay == playerColor;
    }

    private boolean isFirstTurn() {
        return lastPlay == Stone.NONE;
    }

    private boolean isInvalidFirstPlayer(Stone playerColor) {
        return isFirstTurn() && playerColor == Stone.WHITE;
    }

    public boolean isCurrentPlayerNotAbleToMakeAMove() {
        return board.getEmptyIntersections()
                .allMatch(emptyIntersection -> isIllegalMove(getCurrentPlayer().getColor(), emptyIntersection));
    }

    protected Player getPlayerOfColor(Stone color) {
        return getPlayers().stream().filter(player -> player.getColor() == color).findFirst().orElseThrow();
    }

    public void passTurn() {
        Player currentPlayer = getCurrentPlayer();
        this.lastPlay = currentPlayer.getColor();
        outputHandler.notifyPass(currentPlayer);
    }

    protected boolean checkForWinner() {
        Stone winnerColor = getWinner();
        if (winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return true;
        }
        return false;
    }

    protected Stone getWinner() {
        return board.colorWithCompleteChain();
    }

    public void applyPieRule() {
        Stream.of(playerOne, playerTwo).forEach(Player::changeSide);
    }

    public void fillTerritories() {
        board.fillTerritories(lastPlay);
    }

    public Map<Set<Intersection>, Stone> getTerritoriesAndStones(Stone stone){
        return board.getTerritoriesAndStones(stone);
    }

    protected List<Player> getPlayers() {
        return List.of(playerOne, playerTwo);
    }

    protected Board getBoard() {
        return this.board;
    }

    public Stone getLastPlay() {
        return this.lastPlay;
    }

    public Player getCurrentPlayer() {
        return isFirstTurn() ? getPlayerOfColor(Stone.BLACK) : getPlayerOfColor(getLastPlay().getOppositeColor());
    }

    public abstract void play() throws QuentinException;
}