package fr.leottaro;

import javax.swing.JFrame;

public class App {
    public static final double screenPart = 0.75;

    public static void main(String[] args) {
        TicTacToeDisplay Game = new TicTacToeDisplay(screenPart);

        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setVisible(true);
        frame.setSize(Game.WIDTH, Game.HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(Game);
        frame.pack();
        Game.requestFocus();
    }
}