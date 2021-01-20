package sdmExam;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static sdmExam.Position.in;

public class ChainShould {

    @Test
    public void touchEdgesIfItIsACompleteChain() {
        Chain chain = new Chain();

        Intersection intersection1 = new Intersection(in(1, 1), Stone.BLACK);
        Intersection intersection2 = new Intersection(in(1, 2), Stone.BLACK);
        Intersection intersection3 = new Intersection(in(2, 2), Stone.BLACK);
        Intersection intersection4 = new Intersection(in(3, 2), Stone.BLACK);

        chain.updateChain(intersection1);
        chain.updateChain(intersection2);
        chain.updateChain(intersection3);
        chain.updateChain(intersection4);

        List<Edge> listOfEdges = new ArrayList<>();
        listOfEdges.add(Edge.BOTTOM);
        listOfEdges.add(Edge.TOP);

        Edge.setBoardSize(3);
        listOfEdges.forEach(Edge::initialiseEdge);

        assertTrue(chain.hasACompleteChain(listOfEdges));
    }

    @Test
    public void NotTouchEdgesIfItIsNotACompleteChain() {
        Chain chain = new Chain();

        Intersection intersection1 = new Intersection(in(1, 1), Stone.BLACK);
        Intersection intersection2 = new Intersection(in(1, 2), Stone.BLACK);
        Intersection intersection3 = new Intersection(in(2, 2), Stone.BLACK);

        chain.updateChain(intersection1);
        chain.updateChain(intersection2);
        chain.updateChain(intersection3);

        List<Edge> listOfEdges = new ArrayList<>();
        listOfEdges.add(Edge.BOTTOM);
        listOfEdges.add(Edge.TOP);

        Edge.setBoardSize(3);
        listOfEdges.forEach(Edge::initialiseEdge);

        assertFalse(chain.hasACompleteChain(listOfEdges));
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
