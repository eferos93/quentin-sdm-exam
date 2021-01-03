package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameShould {

    @Test
    public void notAllowWhitePlaysFirst(){
        Game game = new Game();
        assertThrows(Exception.class, () -> game.play(Mark.WHITE, Position.in(4, 5)));
    }

    @Test
    public void notAllowPlayerToPlayTwiceInARow(){
        Game game = new Game();
        assertThrows(Exception.class,
                () -> {
                    game.play(Mark.BLACK, Position.in(2, 2));
                    game.play(Mark.BLACK, Position.in(2, 3));
                });
    }

    @Test
    public void notAllowStoneInOccupiedIntersection(){
        Game game = new Game();
        assertThrows(Exception.class,
                () -> {
                    game.play(Mark.BLACK, Position.in(2, 2));
                    game.play(Mark.WHITE, Position.in(2, 2));
                });
    }

    @Test
    public void notAllowStoneOutsideBoard(){
        Game game = new Game();
        assertThrows(Exception.class, () -> game.play(Mark.BLACK, Position.in(-5, -5)));
    }
}
