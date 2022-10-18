package game.richochetrobotfx;

import javafx.scene.paint.Color;


public class Robot {
    private Color couleur;
    private boolean isJetonObjectifRobot;
    private Cellule celluleActuel;

    Robot(Color c){
        this.couleur = c;
    }

    // Accesseurs : Getters & Setters
    public Color getColor() {
        return couleur;
    }

    public Cellule getCelluleActuel() {
        return celluleActuel;
    }

    public boolean isJetonObjectifRobot() {
        return isJetonObjectifRobot;
    }

    public void setCelluleActuel(Cellule celluleActuel) {
        this.celluleActuel = celluleActuel;
    }

    public void setJetonObjectifRobot(boolean jetonObjectifRobot) {
        isJetonObjectifRobot = jetonObjectifRobot;
    }
}
