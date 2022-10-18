package game.richochetrobotfx;

import java.util.ArrayList;
import java.util.List;


public class Cellule {

    private Position position;
    private Robot robotActuel;
    private List<Mur> murs = new ArrayList<>();
    private boolean presenceMur;
    private boolean presenceRobot;
    private boolean presenceJeton;
    private Jeton jeton;


    //Mise en place de la cellule Box
    Cellule(Position p){
        this.position = p;
        this.presenceMur = false;
        this.presenceJeton = false;
    }

    //Cellule avec Mur
    Cellule(Position p, List<Mur> w){
        this.position = p;
        this.murs = w;
        this.presenceMur = true;
    }

    //Méthodes accesseurs Getters/Setters (Pour Position & Mur)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Mur> getMurs() {
        return this.murs;
    }

    public void setMurs(List<Mur> murs) {
        this.murs = murs;
    }

    public void addMurs(Orientation orientation) {
        this.murs.add(new Mur(orientation));
        this.presenceMur = true;
    }

    public void rotateMurADroite(int nombreDeRotation) {
        for (Mur mur : murs) {
            for (int n = 0; n < nombreDeRotation; n++) {
                switch (mur.getOrientation()) {
                    case NORD -> mur.setOrientation(Orientation.EST);
                    case SUD -> mur.setOrientation(Orientation.OUEST);
                    case EST -> mur.setOrientation(Orientation.SUD);
                    case OUEST -> mur.setOrientation(Orientation.NORD);
                }
            }
        }
    }

    //Ajout des éléments (robot / piece)
    public void addTypeJeton(Jeton jeton){
        this.presenceJeton = true;
        this.jeton = jeton;
    }

    public Robot getRobotActuel() {
        return robotActuel;
    }

    public void addRobot(Robot robot) {
        this.robotActuel = robot;
        this.presenceRobot = true;
    }

    public void removeRobot() {
        this.robotActuel = null;
        this.presenceRobot = false;
    }



    //Accesseurs Getters/Setters (pour Robot & Jetons)
    public boolean presenceMur() {
        return murs.size() > 0;
    }



    public boolean getPresenceRobot() {
        return presenceRobot;
    }
    public boolean getPresenceJeton() {
        return presenceJeton;
    }
    public Jeton getJeton() {
        return jeton;
    }
}
