package spw4.connectfour;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConnectFourImplTests {
    ConnectFourImpl game;

    @BeforeEach
    public void initialize() {
        game = new ConnectFourImpl(Player.yellow);
    }

    @Test
    public void InstanceOfConnectFourIsNotNull() {
        assertNotNull(game);
    }

    @Test
    public void StartAGameAndLookForPlayer() {
        assertEquals(Player.yellow, game.getPlayerOnTurn());
    }

    @Test
    public void getPlayerAtEqualsNone() {
        assertEquals(Player.none, game.getPlayerAt(0, 0));
    }

    @Test
    public void StartGameDropDiscAndLook() {
        game.drop(0);
        assertEquals(Player.yellow, game.getPlayerAt(5, 0));
    }

    @Test
    public void LookForOutputAfterDrop() {
        assertEquals("\nPlayer:" + game.getPlayerOnTurn().toString() + "\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n", game.toString());
        game.drop(0);
        assertEquals("\nPlayer:" + game.getPlayerOnTurn().toString() + "\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| .  .  .  .  .  .  . |\n" +
                "| Y  .  .  .  .  .  . |\n", game.toString());
    }

    @Test
    public void DropTwoDiscsInSameColumn() {
        game.drop(0);
        game.drop(0);
        assertEquals(Player.yellow, game.getPlayerAt(5, 0));
        assertEquals(Player.red, game.getPlayerAt(4, 0));
    }

    @Test
    public void getPlayerOnSecondTurn() {
        game.drop(0);
        assertEquals(Player.red, game.getPlayerOnTurn());
    }

    @Test
    public void ResetGame() {
        game.drop(0);
        game.drop(0);
        game.reset(Player.yellow);
        assertEquals(Player.yellow, game.getPlayerOnTurn());
        assertEquals(Player.none, game.getPlayerAt(5, 0));
    }

    @Test
    public void DropTillGameOver() {
        game.drop(0);
        game.drop(1);
        game.drop(0);
        game.drop(1);
        game.drop(0);
        game.drop(1);
        game.drop(0);
        assertTrue(game.isGameOver());
    }

    @Test
    public void DropDiscAfterGameOverException() {
        game.drop(0);
        game.drop(0);
        game.drop(1);
        game.drop(1);
        game.drop(2);
        game.drop(2);
        game.drop(3);
        assertThrows(IllegalStateException.class, () -> game.drop(3));
    }

    @Test
    public void CheckForWinnerRightDirection() {
        game.drop(0);
        game.drop(0);
        game.drop(1);
        game.drop(1);
        game.drop(2);
        game.drop(2);
        game.drop(3);
        assertEquals(Player.yellow, game.getWinner());
    }

    @Test
    public void CheckForWinnerRightUpperDirection() {
        game.drop(0);
        game.drop(1);
        game.drop(1);
        game.drop(2);
        game.drop(2);
        game.drop(3);
        game.drop(2);
        game.drop(3);
        game.drop(3);
        game.drop(1);
        game.drop(3);
        System.out.println(game.toString());
        assertEquals(Player.yellow, game.getWinner());
    }

    @Test
    public void CheckForWinnerLeftUpperDirection() {
        game.drop(6);
        game.drop(5);
        game.drop(5);
        game.drop(4);
        game.drop(4);
        game.drop(3);
        game.drop(4);
        game.drop(3);
        game.drop(3);
        game.drop(4);
        game.drop(3);
        assertEquals(Player.yellow, game.getWinner());
    }

    @Test
    public void CheckForWinnerWhileGameIsOngoing() {
        game.drop(0);
        game.drop(1);
        game.drop(1);
        game.drop(2);
        assertEquals(Player.none, game.getWinner());
    }

    @Test
    public void WrongColumnDrop() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> game.drop(-1)),
                () -> assertThrows(IllegalArgumentException.class, () -> game.drop(7))
        );
    }

    @Test
    public void CheckIfGameIsOverWhenBoardIsFull() {
        int[] moves = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6};

        for (int move : moves) {
            game.drop(move);
            System.out.println(game.toString());
        }
        assertAll(
                () -> assertEquals(Player.none, game.getWinner()),
                () -> assertTrue(game.isGameOver())
        );
    }
}
