package game.richochetrobotfx;


public class Game {
    public static Game context;
    private Plateau plateau;
    private Jeton JetonObjectifActuel;       // Le jeton qui a été pioché
    private int JetonObjectifIndice;            //Selectionner l'indice du jetonObjectif
    public static Status Status;         //Stats du jeu
    private Player joueurUn = new Player("J1");
    private Player joueurDeux = new Player("J2");

    public Game(){
        this.plateau = new Plateau();
    }

    public void play() {
        if (Game.context != null) {
            throw new RuntimeException
                    ("Ne pas spam le lancement du jeu");
        }


        //Créer la session game()
        Game.context = new Game();

        //Créer les joueurs
        Player playerTwo = new Player("J2");
        this.plateau.createPlateau();                                       //Créer le plateau
        this.plateau.addRobotsAuPlateau();                                  //Ajoute les robots sur le plateau
        this.plateau.setListJetonObjectif();                                       // Initialisation ordre d'apparition des pièces "jetonObjectif"
        this.JetonObjectifIndice = 0;                                            // Initialisation de l'indice du JetonObjectif à travers la liste
        this.JetonObjectifActuel = this.plateau.getJetonOjectifs().get(this.JetonObjectifIndice);  //Definition du jetonObjectif à atteindre
        this.plateau.setTypeJetonDansJetonObjectifBox(this.JetonObjectifActuel);                //Ajoute puis affiche le jetonObjectif / la pièce pioché au centre mini plateau
    }

    //Getters/Setters
    public Player getJoueurUn() {
        return joueurUn;
    }

    public Player getJoueurDeux() {
        return joueurDeux;
    }

    // montre statut du jeu
    public enum Status{
        LAUNCH_TIMER,
        PREPARE_ROUND,
        PLAYER_ONE_TURN,
        PLAYER_TWO_TURN,
        END_ROUND;
    }

    /** Les fonctions **/

    //Accesseurs : Getters/Setters

    public Plateau getPlateau() {
        return this.plateau;
    }

    public void setPremierTour(){
        if(this.joueurUn.getIsPremierAMiser()){
            this.joueurUn.setIsMonTour(true);
            this.joueurDeux.setIsMonTour(false);
            Game.Status = Status.PLAYER_ONE_TURN;
        }else if(this.joueurDeux.getIsPremierAMiser()){
            this.joueurDeux.setIsMonTour(true);
            this.joueurUn.setIsMonTour(false);
            Game.Status = Status.PLAYER_TWO_TURN;
        }
    }

    public void setProchainTour(){
        if (this.joueurUn.getIsMonTour()){
            this.joueurUn.setIsMonTour(false);
            this.joueurUn.setDejaJoue(true);
            this.joueurDeux.setIsMonTour(true);
            if (!this.joueurDeux.isDejaJoue()){
                System.out.println("A déjà joué");
                Game.Status = Status.PLAYER_TWO_TURN;
            }else {
                Game.Status = Status.END_ROUND;
            }
        }else if(this.joueurDeux.getIsMonTour()){
            this.joueurDeux.setIsMonTour(false);
            this.joueurDeux.setDejaJoue(true);
            this.joueurUn.setIsMonTour(true);
            if (!this.joueurUn.isDejaJoue()){
                Game.Status = Status.PLAYER_ONE_TURN;
            }else {
                Game.Status = Status.END_ROUND;
            }
        }
    }


    //Obtention d'un nouveau jetonObjectif
    public void getNouveauJetonObjectif(){
        this.JetonObjectifIndice++;                                                                                              //Increment the index of the list
        this.JetonObjectifActuel = this.plateau.getJetonOjectifs().get(this.JetonObjectifIndice);                                                  //Definition of the new objective
    }

    //Fonction verifiant si conditions réuni pour victoire
    public boolean itIsWin(Robot robot){
        if((robot.getCelluleActuel().getJeton() == this.JetonObjectifActuel) && (robot.getCelluleActuel().getJeton().getColor() == robot.getColor()) && (this.JetonObjectifActuel.getColor() == robot.getColor())){
            if(this.joueurUn.getIsMonTour()){
                this.joueurUn.addNouvelleVictoire();
                this.joueurUn.setNombreTourGagne(true);
                System.out.println("Victoire" + this.joueurUn.getNombreTourGagne());
            }else if(this.joueurDeux.getIsMonTour()){
                this.joueurDeux.addNouvelleVictoire();
                this.joueurDeux.setNombreTourGagne(true);
                System.out.println("Victoire" + this.joueurDeux.getNombreTourGagne());
            }

            Game.Status = Status.END_ROUND;
            return true;
        }
        else {
            return false;
        }
    }


    //Fonction initialisant les déplacements des joueurs
    public void reinitialisePlayer(){
        this.joueurUn.setNombreTourGagne(false);
        this.joueurDeux.setNombreTourGagne(false);
        this.joueurUn.setDejaJoue(false);
        this.joueurDeux.setDejaJoue(false);
        this.joueurUn.setIsMonTour(false);
        this.joueurDeux.setIsMonTour(false);
        this.joueurUn.setNombreDeCoupMise(0);
        this.joueurDeux.setNombreDeCoupMise(0);
        this.joueurUn.setNombreDeCoup(0);
        this.joueurDeux.setNombreDeCoup(0);
    }

    public boolean deplacementValide(Cellule celluleActuel, Orientation direction) {
        Position nextCellulePosition = celluleActuel.getPosition().nextPosition(direction);

        if (nextCellulePosition.getColonne() > 16 || nextCellulePosition.getLigne() > 16) {
            return false;
        }

        Cellule nextCellule = this.plateau.getCellule(nextCellulePosition);
        // Verif si deplacement valide
        if (celluleActuel.presenceMur()) {
            for (Mur mur : celluleActuel.getMurs()) {
                if (mur.getOrientation() == direction) { return false; }
            }
        }

        if (nextCellule.getPresenceRobot()) { return false; }

        if (nextCellule.presenceMur()) {
            for (Mur mur : nextCellule.getMurs()) {
                switch (mur.getOrientation()) {
                    case NORD:
                        if (direction == Orientation.SUD) { return false; }
                        break;
                    case SUD:
                        if (direction == Orientation.NORD) { return false; }
                        break;
                    case EST:
                        if (direction == Orientation.OUEST) { return false; }
                        break;
                    case OUEST:
                        if (direction == Orientation.EST) { return false; }
                        break;
                }
            }
        }

        return true;
    }

    public void deplacement(Cellule currentCellule, Orientation direction) {
        Robot robot = currentCellule.getRobotActuel();

        // Suppr robot depuis le plateau
        Position currentPosition = currentCellule.getPosition();
        plateau.getCellule(currentPosition).removeRobot();

        // Ajout robot à nouvelle position
        Position newPosition = currentCellule.getPosition().nextPosition(direction);
        Cellule newCellule = plateau.getCellule(newPosition);
        newCellule.addRobot(robot);
    }

    public Jeton getJetonObjectifActuel() {
        return JetonObjectifActuel;
    }



}
