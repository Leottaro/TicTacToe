package fr.leottaro;

import java.util.Random;

public class TicTacToe {
    public static final int GRID_SIZE = 3;

    private Player[][] grid;
    private Player turn;
    private int turnNumber;
    private Player winner;

    public TicTacToe() {
        Init();
    }

    public TicTacToe(Player p) {
        Init(p);
    }

    private void Init() {
        grid = new Player[GRID_SIZE][GRID_SIZE];
        turn = Player.X;
        turnNumber = 0;
        winner = null;
    }

    private void Init(Player p) {
        Init();
        turn = p;
    }

    public void place(int x, int y) {
        if (!isFree(x, y) || isOver())
            return;
        grid[y][x] = turn;
        turn = turn.alternated();
        turnNumber++;
        winner = chooseWinner();
    }

    private Player chooseWinner() {
        for (int i = 0; i < GRID_SIZE; i++) {
            // check for lines
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2])
                return grid[i][0];
            // check for columns
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i])
                return grid[0][i];
        }

        // check for diagonal 1
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
            return grid[1][1];
        // check for diagonal 2
        if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])
            return grid[1][1];

        return null;
    }

    public void Reset() {
        if (turnNumber % 2 == 0)
            Init(turn.alternated());
        else
            Init(turn);
    }

    private void remove(int x, int y) {
        if (isFree(x, y))
            return;
        grid[y][x] = null;
        turn = turn.alternated();
        turnNumber--;
        winner = null;
    }

    private void shuffleArray(int[] array) {
        Random rnd = new Random();
        int index, temp;
        for (int i = array.length - 1; i > 0; i--) {
            index = rnd.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private int[] bestPlay(Player p) {
        if (isOver())
            return null;
        int bestX = -1;
        int bestY = -1;
        int bestScore = Integer.MIN_VALUE;

        int[] rows = new int[] { 0, 1, 2 };
        int[] columns = new int[] { 0, 1, 2 };
        shuffleArray(rows);
        shuffleArray(columns);

        for (int y : rows) {
            for (int x : columns) {
                if (isFree(x, y) && !isOver()) {
                    int score = Integer.MIN_VALUE;
                    place(x, y);
                    if (isOver()) {
                        if (winner == p)
                            score = Integer.MAX_VALUE;
                        else if (winner != null)
                            score = Integer.MIN_VALUE;
                        else
                            score = 0;
                    } else {
                        int[] temp = bestPlay(p.alternated());
                        if (score < temp[2])
                            score = temp[2];
                    }
                    if (score > bestScore) {
                        bestX = x;
                        bestY = y;
                        bestScore = score;
                    }
                    remove(x, y);
                }
            }
        }
        return new int[] { bestX, bestY, -(bestScore-1) };
    }

    public void botPlay() {
        int[] data = bestPlay(turn);
        if (data == null)
            return;
        place(data[0], data[1]);
    }

    // Getters

    public Player getCell(int x, int y) {
        if (0 > x || GRID_SIZE < x || 0 > y || GRID_SIZE < y)
            return null;
        return grid[y][x];
    }

    public Player getTurn() {
        return turn;
    }

    public void alternateTurn() {
        turn = turn.alternated();
    }

    public boolean isFree(int x, int y) {
        if (0 > x || GRID_SIZE < x || 0 > y || GRID_SIZE < y)
            return false;
        return grid[y][x] == null;
    }

    public boolean isOver() {
        return turnNumber == 9 || winner != null;
    }

    public String toString() {
        String result = "\n";
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (x != 0)
                    result += " |";
                result += " " + Player.Stringify(grid[y][x]);
            }
            if (y != 2)
                result += "\n---+---+---\n";
        }
        result += "\n";
        if (isOver()) {
            if (winner == null)
                result += "Nobody won !";
            else
                result += "Winner is " + Player.Stringify(winner) + "!";
        } else
            result += Player.Stringify(turn) + "'s turn";
        return result + "\n";
    }
}