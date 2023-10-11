package fr.leottaro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class TicTacToeDisplay extends JPanel implements ActionListener, MouseListener {
    final int WIDTH;
    final int HEIGHT;
    final int TILE_SIZE;

    private TicTacToe game;

    public TicTacToeDisplay(int boardWidth, int boardHeight, int tileSize) {
        WIDTH = boardWidth;
        HEIGHT = boardHeight;
        TILE_SIZE = tileSize;

        setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        addMouseListener(this);
        setFocusable(true);
        Init();
    }

    public TicTacToeDisplay(double screenPart) {
        int boardWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * screenPart);
        int boardHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * screenPart);
        TILE_SIZE = Math.min(boardWidth / TicTacToe.GRID_SIZE, boardHeight / TicTacToe.GRID_SIZE);
        WIDTH = TILE_SIZE * TicTacToe.GRID_SIZE;
        HEIGHT = TILE_SIZE * TicTacToe.GRID_SIZE;

        setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        addMouseListener(this);
        setFocusable(true);
        Init();
    }

    public void Init() {
        game = new TicTacToe();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw grid
        g.setColor(Color.GRAY);
        for (int x = 0; x <= WIDTH; x += TILE_SIZE)
            g.drawLine(x, 0, x, HEIGHT);
        for (int y = 0; y <= HEIGHT; y += TILE_SIZE)
            g.drawLine(0, y, WIDTH, y);

        g.setFont(new Font("Lucida Grande", 0, TILE_SIZE * 2 / 3));
        for (int y = 0; y < TicTacToe.GRID_SIZE; y++) {
            for (int x = 0; x < TicTacToe.GRID_SIZE; x++) {
                Player p = game.getCell(x, y);
                if (p != null) {
                    Color c = p.getColor();
                    g.setColor(c);
                    drawCenteredString(g, Player.Stringify(p), (x + 0.5) * TILE_SIZE, (y + 0.5) * TILE_SIZE);
                }
            }
        }
    }

    private void drawCenteredString(Graphics g, String str, double x, double y) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(str, (int) x - metrics.stringWidth(str) / 2,
                (int) y - metrics.getHeight() / 2 + metrics.getAscent());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game.isOver()) {
            game.Reset();
            if (game.getTurn() == Player.O) {
                game.botPlay();
            }
            repaint();
            return;
        }
        int x = e.getX() / TILE_SIZE;
        int y = e.getY() / TILE_SIZE;
        game.place(x, y);
        repaint();
        game.botPlay();
        repaint();
    }

    // Unused function

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}