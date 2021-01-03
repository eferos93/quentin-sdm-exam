package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
