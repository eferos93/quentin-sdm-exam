package sdmExam;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static sdmExam.Position.in;

public class ChainShould {

    @Test
    public void touchEdgesIfItIsACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK),
                new Intersection(in(3, 2), Stone.BLACK)
        );

        Chain chain = new Chain();
        intersectionStream.forEach(chain::updateChain);

        List<BoardSide> listOfBoardSides = List.of(BoardSide.BOTTOM, BoardSide.TOP);

        BoardSide.setBoardSize(3);
        listOfBoardSides.forEach(BoardSide::initialiseSide);

        assertTrue(chain.hasACompleteChain(listOfBoardSides));
    }

    @Test
    public void NotTouchEdgesIfItIsNotACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK)
        );

        Chain chain = new Chain();
        intersectionStream.forEach(chain::updateChain);

        List<BoardSide> listOfBoardSides = List.of(BoardSide.BOTTOM, BoardSide.TOP);

        BoardSide.setBoardSize(3);
        listOfBoardSides.forEach(BoardSide::initialiseSide);

        assertFalse(chain.hasACompleteChain(listOfBoardSides));
    }

    @Test
    public void updateTheChainsCorrectly() {
        Board customBoard = Board.buildTestBoard(3);
        customBoard.addStoneAt(Stone.BLACK, in(1, 1));
        customBoard.addStoneAt(Stone.BLACK, in(1, 2));
        customBoard.addStoneAt(Stone.BLACK, in(2, 2));
        customBoard.addStoneAt(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customBoard.colorWithCompleteChain());
    }

    @Test
    public void provideCorrectWinnerMergeChainsFeature() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.makeMove(Stone.BLACK, in(1, 1));
        customGame.makeMove(Stone.WHITE, in(1, 2));
        customGame.makeMove(Stone.BLACK, in(4, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.WHITE, in(1, 3));
        customGame.makeMove(Stone.BLACK, in(2, 1));
        customGame.makeMove(Stone.WHITE, in(1, 4));
        customGame.makeMove(Stone.BLACK, in(3, 4));
        customGame.makeMove(Stone.WHITE, in(2, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.BLACK, in(3, 1));
        customGame.makeMove(Stone.WHITE, in(2, 3));
        customGame.makeMove(Stone.BLACK, in(3, 3));
        customGame.makeMove(Stone.WHITE, in(2, 2));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customGame.getWinner());
    }
}
