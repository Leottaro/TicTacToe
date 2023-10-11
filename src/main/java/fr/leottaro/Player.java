package fr.leottaro;

import java.awt.Color;

public enum Player {
    X, O;

    public Player alternated() {
        switch (Player.Stringify(this)) {
            case "X":
                return Player.O;
            case "O":
                return Player.X;
            default:
                return null;
        }
    }

    public Color getColor() {
        switch (this) {
            case O:
                return Color.CYAN;
            case X:
                return Color.GRAY;
            default:
                return Color.WHITE;
        }
    }

    public static String Stringify(Player p) {
        return p == null ? " " : p.name();
    }
}
