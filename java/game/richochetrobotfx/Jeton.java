package game.richochetrobotfx;

import javafx.scene.paint.Color;


public class Jeton {
    private Color couleur;
    private TypeJeton typeJeton;
    private Position position;
    private boolean estJetonObjectif;


    // lib JavaFX Scene Paint

    Jeton(Color c, TypeJeton s, Position p){
        this.couleur = c;
        this.typeJeton = s;
        this.position = p;
    }


    //Getters/Setters
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Color getColor() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public TypeJeton getTypeJeton() {
        return this.typeJeton;
    }

    public void setTypeJeton(TypeJeton typeJeton) {
        this.typeJeton = typeJeton;
    }

    public boolean isJetonObjectif() {
        return estJetonObjectif;
    }

    public void setJetonObjectif(boolean ouiJetonObjectif) {
        estJetonObjectif = ouiJetonObjectif;
    }

    public void affichage(){
        System.out.println(this.typeJeton + " " + this.couleur);
    }
}
