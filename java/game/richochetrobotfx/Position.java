package game.richochetrobotfx;


public class Position {

    private int ligne;
    private int colonne;

    Position(int l, int c){
        this.ligne = l;
        this.colonne = c;
    }


    //Getters/Setters

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    // Fonctions
    public Position nextPosition(Orientation direction) {
        Position nextCellulePosition = new Position(this.ligne, this.colonne);

        switch (direction) {
            case NORD -> nextCellulePosition.setColonne(nextCellulePosition.getColonne() - 1);
            case SUD -> nextCellulePosition.setColonne(nextCellulePosition.getColonne() + 1);
            case EST -> nextCellulePosition.setLigne(nextCellulePosition.getLigne() + 1);
            case OUEST -> nextCellulePosition.setLigne(nextCellulePosition.getLigne() - 1);
        }

        return nextCellulePosition;
    }

}
