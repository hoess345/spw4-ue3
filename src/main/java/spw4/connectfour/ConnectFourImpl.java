package spw4.connectfour;

public class ConnectFourImpl implements ConnectFour {
    private Player playerOnTurn;
    private final Character[][] board = new Character[6][7];


    public ConnectFourImpl(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) board[i][j] = '.';
        }
    }

    public Player getPlayerAt(int row, int col) {
        if (board[row][col] == 'R') return Player.red;
        else if (board[row][col] == 'Y') return Player.yellow;
        else return Player.none;
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public boolean isGameOver() {
        boolean gameboardisfull = true;

        for (int i = 0; i < 6; i++) {
            if (!gameboardisfull) break;
            for (int j = 0; j < 7; j++) {

                if (board[i][j] == '.') {
                    gameboardisfull = false;
                    break;
                }
            }
        }
        if (gameboardisfull) return true;
        else return checkforwinner();
    }

    private boolean checkforwinner() {
        //check if in board are 4 Connected discs
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] != '.') {
                    //check vertical
                    if (i + 3 < 6) {
                        if (board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                            return true;
                        }
                    }
                    //check horizontal
                    if (j + 3 < 7) {
                        if (board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                            return true;
                        }
                    }
                    //check diagonal
                    if (i - 3 >= 0 && j + 3 < 7) {
                        if (board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                            return true;
                        }
                    }
                    //check other diagonal
                    if (i - 3 >= 0 && j - 3 >= 0) {
                        if (board[i][j] == board[i - 1][j - 1] && board[i][j] == board[i - 2][j - 2] && board[i][j] == board[i - 3][j - 3]) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Player getWinner() {
        if (!isGameOver()) return Player.none;
        else if (!checkforwinner()) return Player.none;
        return playerOnTurn == Player.yellow ? Player.red : Player.yellow;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("\nPlayer:").append(playerOnTurn.toString()).append("\n");
        for (int i = 0; i < 6; i++) {
            output.append("|");
            for (int j = 0; j < 7; j++) {
                output.append(" ");
                output.append(board[i][j]);
                output.append(" ");
            }
            output.append("|\n");
        }
        return output.toString();
    }

    public void reset(Player playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = '.';
            }
        }
    }

    public void drop(int col) {
        if (col < 0 || col > 6) throw new IllegalArgumentException("Column must be between 0 and 6");
        if (isGameOver()) throw new IllegalStateException("Game is over");
        else {
            for (int i = 5; i >= 0; i--) {
                if (board[i][col] == '.') {
                    if (playerOnTurn == Player.red) {
                        board[i][col] = 'R';
                        playerOnTurn = Player.yellow;
                    } else {
                        board[i][col] = 'Y';
                        playerOnTurn = Player.red;
                    }
                    break;
                }
            }
        }
    }
}
