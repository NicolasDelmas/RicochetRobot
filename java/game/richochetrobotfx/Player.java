package game.richochetrobotfx;


public class Player {

    private String nom;                                                                                                //Nom du joueur
    private int nombreDeCoup;                                                                                             //Nombre de deplacement depuis la game
    private int nombreDeCoupMise;                                                                                       //Number deplacement durant le comptage
    private boolean MonTour;                                                                                           //Definit le tour du joueur
    private boolean dejaJoue;                                                                                  // appel joueur si il a déjà joué
    private boolean jeSuisPremierAMiser;                                                                          // si le joueur est le premier à avoir trouvé la solution
    private int nombreTourGagne;                                                                                              //Nombre de tour gagné
    private boolean TourGagne;                                                                                           //Si la manche a été remporté par un joueur

    Player(String n){
        this.nom = n;
        this.MonTour = false;
        this.jeSuisPremierAMiser = false;
        this.dejaJoue = false;
        this.nombreDeCoup = 0;
        this.nombreTourGagne = 0;
    }

    // Methods
    public void addNouvelleVictoire(){
        this.nombreTourGagne++;
    }


    //Getters/Setters
    public boolean getIsMonTour() {
        return MonTour;
    }

    public void setIsMonTour(boolean monTour) {
        MonTour = monTour;
    }

    public int getNombreDeCoup() {
        return nombreDeCoup;
    }

    public void setNombreDeCoup(int nombreDeCoup) {
        this.nombreDeCoup = nombreDeCoup;
    }

    public int getNombreDeCoupMise() {
        return nombreDeCoupMise;
    }

    public void setNombreDeCoupMise(int nombreDeCoupMise) {
        this.nombreDeCoupMise = nombreDeCoupMise;
    }

    public boolean getIsPremierAMiser() {
        return jeSuisPremierAMiser;
    }

    public void setPremierAMiser(boolean jeSuisPremierAMiser) {
        this.jeSuisPremierAMiser = jeSuisPremierAMiser;
    }

    public int getNombreTourGagne() {
        return nombreTourGagne;
    }

    public void setNombreTourGagne(int nombreToursGagne) {
        this.nombreTourGagne = nombreToursGagne;
    }

    public boolean isDejaJoue() {
        return dejaJoue;
    }

    public void setDejaJoue(boolean dejaJoue) {
        this.dejaJoue = dejaJoue;
    }

    public boolean isTourGagne() {
        return TourGagne;
    }

    public void setNombreTourGagne(boolean TourGagne) {
        this.TourGagne = TourGagne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
