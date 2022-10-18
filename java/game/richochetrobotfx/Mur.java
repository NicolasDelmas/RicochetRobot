package game.richochetrobotfx;


public class Mur {

    private Orientation orientation;

    Mur(Orientation o){
        this.orientation = o;
    }

    //Accesseurs : Getters/Setters
    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
