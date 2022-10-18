package game.richochetrobotfx;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Plateau {

    private Cellule[][] cellules;    //Tab matrice 16x16  --- Débute par 1
    private Cellule[][][] miniPlateau;
    private List<Jeton> jetons = new ArrayList<>();
    private List<Jeton> goals = new ArrayList<>();
    private Cellule[][] goalBox;
    private List<Robot> robots = new ArrayList<>();
    private Robot RobotActuel;
    private Robot goalRobot;
    private Jeton JetonObjectifActuel;
    private int NombreDeJetonObjectif;

    Plateau(){
    }

    Plateau(List<JetonObjectif> g, int gN){
        this.jetons = new ArrayList<>();
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.SOLEIL, new Position(2, 4)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.SOLEIL, new Position(2, 13)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.ETOILE, new Position(4, 7)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.ETOILE, new Position(4, 10)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.LUNE, new Position(5, 2)));
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.LUNE, new Position(5, 15)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.PLANETE, new Position(6, 11)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.PLANETE, new Position(7, 5)));
        this.jetons.add(new Jeton(Color.BLACK, TypeJeton.VORTEX, new Position(9, 13)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.LUNE, new Position(10, 11)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.SOLEIL, new Position(10, 4)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.PLANETE, new Position(12, 6)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.SOLEIL, new Position(12, 10)));
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.PLANETE, new Position(13, 15)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.LUNE, new Position(14, 2)));
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.ETOILE, new Position(15, 7)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.ETOILE, new Position(15, 14)));
        this.cellules = new Cellule[17][17];
        this.miniPlateau = new Cellule[4][8][8];
    }

    //Getters/Setters
    public Cellule[][] getCellules() {
        return this.cellules;
    }
    public void setCellules(Cellule[][] cellules) {
        this.cellules = cellules;
    }
    public List<Jeton> getJetonOjectifs() {
        return goals;
    }

    public List<Jeton> getJetons() {
        return jetons;
    }

    public void setJetonObjectifActuel(JetonObjectif currentJetonObjectif) {
        this.JetonObjectifActuel = currentJetonObjectif;
    }
    public Jeton getJetonObjectifActuel() {
        return JetonObjectifActuel;
    }

    public Cellule getCellule(Position position) {
        return this.cellules[position.getLigne()][position.getColonne()];
    }

    //Nouveau plateau généré
    public void createPlateau(){
        this.cellules = new Cellule[17][17];
        for (int i = 1; i < 17; i++){    //Initialisation du box à 1-1
            for (int n = 1; n < 17; n++){
                this.cellules[i][n] = new Cellule(new Position(i, n));
            }
        }

        faireBoxCentral();       //Creation du box central
        addMursSurPlateau();     //AJout murs
        setJetons();
        setJetonsSurCellule();           //Placement des jetons sur cellules

        Cellule[][] celltest = new Cellule[9][9];

        for(int i = 1; i <= 8; i++){
            for(int n = 1; n <= 8; n++){
                celltest[i][n] = this.cellules[n][i];
            }
        }

    }

    public void setJetons() {
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.SOLEIL, new Position(2, 4)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.SOLEIL, new Position(2, 13)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.ETOILE, new Position(4, 7)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.ETOILE, new Position(4, 10)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.LUNE, new Position(5, 2)));
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.LUNE, new Position(5, 15)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.PLANETE, new Position(6, 11)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.PLANETE, new Position(7, 5)));
        this.jetons.add(new Jeton(Color.BLACK, TypeJeton.VORTEX, new Position(9, 13)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.LUNE, new Position(10, 11)));
        this.jetons.add(new Jeton(Color.BLUE, TypeJeton.SOLEIL, new Position(10, 4)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.PLANETE, new Position(12, 6)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.SOLEIL, new Position(12, 10)));
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.PLANETE, new Position(13, 15)));
        this.jetons.add(new Jeton(Color.YELLOW, TypeJeton.LUNE, new Position(14, 2)));
        this.jetons.add(new Jeton(Color.GREEN, TypeJeton.ETOILE, new Position(15, 7)));
        this.jetons.add(new Jeton(Color.RED, TypeJeton.ETOILE, new Position(15, 14)));
    }

    //Ajout mur dans la cellule : spécifier les positions de celui ci (dans la matrice) par rapport aux cellules
    public void addMurDansCellule(int ligne, int colonne, Orientation orientation){
        this.cellules[ligne][colonne].addMurs(orientation);
    }


    //Generer le box central pour les jetons objectif
    public void faireBoxCentral(){


        //Creation de mur
        this.cellules[9][7].addMurs(Orientation.SUD);
        this.cellules[9][10].addMurs(Orientation.NORD);
        this.cellules[10][8].addMurs(Orientation.OUEST);
        this.cellules[10][9].addMurs(Orientation.OUEST);
        this.cellules[7][8].addMurs(Orientation.EST);
        this.cellules[7][9].addMurs(Orientation.EST);
        this.cellules[8][7].addMurs(Orientation.SUD);
        this.cellules[8][10].addMurs(Orientation.NORD);


        for(int i = 1; i < this.cellules.length; i++){
            this.cellules[i][1].addMurs(Orientation.NORD);
        }

        for(int i = 1; i < this.cellules.length; i++){
            this.cellules[1][i].addMurs(Orientation.OUEST);
        }

        for(int i = 1; i < this.cellules.length; i++){
            this.cellules[16][i].addMurs(Orientation.EST);
        }

        for(int i = 1; i < this.cellules.length; i++){
            this.cellules[i][16].addMurs(Orientation.SUD);
        }
    }


    // ajout mur dans les cellules precisement [][]
    public void addMursSurPlateau(){
        this.cellules[2][4].addMurs(Orientation.NORD);
        this.cellules[2][4].addMurs(Orientation.OUEST);
        this.cellules[5][2].addMurs(Orientation.OUEST);
        this.cellules[5][2].addMurs(Orientation.SUD);
        this.cellules[7][5].addMurs(Orientation.NORD);
        this.cellules[7][1].addMurs(Orientation.OUEST);
        this.cellules[7][5].addMurs(Orientation.EST);
        this.cellules[10][4].addMurs(Orientation.SUD);
        this.cellules[10][4].addMurs(Orientation.EST);
        this.cellules[11][1].addMurs(Orientation.EST);
        this.cellules[14][2].addMurs(Orientation.OUEST);
        this.cellules[14][2].addMurs(Orientation.SUD);
        this.cellules[16][5].addMurs(Orientation.EST);
        this.cellules[16][11].addMurs(Orientation.EST);
        this.cellules[5][16].addMurs(Orientation.EST);
        this.cellules[10][16].addMurs(Orientation.EST);
        this.cellules[13][15].addMurs(Orientation.OUEST);
        this.cellules[13][15].addMurs(Orientation.SUD);
        this.cellules[15][7].addMurs(Orientation.NORD);
        this.cellules[15][7].addMurs(Orientation.OUEST);
        this.cellules[16][3].addMurs(Orientation.SUD);
        this.cellules[16][10].addMurs(Orientation.SUD);
        this.cellules[1][7].addMurs(Orientation.NORD);
        this.cellules[1][12].addMurs(Orientation.NORD);
        this.cellules[4][7].addMurs(Orientation.OUEST);
        this.cellules[4][7].addMurs(Orientation.SUD);
        this.cellules[12][6].addMurs(Orientation.NORD);
        this.cellules[12][6].addMurs(Orientation.EST);
        this.cellules[12][10].addMurs(Orientation.SUD);
        this.cellules[12][10].addMurs(Orientation.EST);

        this.cellules[4][10].addMurs(Orientation.SUD);
        this.cellules[4][10].addMurs(Orientation.EST);

        this.cellules[9][13].addMurs(Orientation.OUEST);
        this.cellules[9][13].addMurs(Orientation.SUD);

        this.cellules[15][14].addMurs(Orientation.NORD);
        this.cellules[15][14].addMurs(Orientation.EST);
        this.cellules[10][11].addMurs(Orientation.OUEST);
        this.cellules[10][11].addMurs(Orientation.NORD);
        this.cellules[6][11].addMurs(Orientation.OUEST);
        this.cellules[6][11].addMurs(Orientation.NORD);

        this.cellules[2][13].addMurs(Orientation.OUEST);
        this.cellules[2][13].addMurs(Orientation.NORD);
        this.cellules[7][15].addMurs(Orientation.EST);
        this.cellules[7][15].addMurs(Orientation.NORD);

    }

    //Ajout des robots sur le plateau
    public void addRobotsAuPlateau() {
        for (int i = 0; i < 4; i++) {
            int randomLigne = (int)(Math.random() * 16) + 1;
            int randomColonne = (int)(Math.random() * 16) + 1;
            // Positionner aléatoirement les robots
            while ((randomLigne == 8 || randomLigne == 9) && (randomColonne == 8 || randomColonne == 9)) {
                randomLigne = (int)(Math.random() * 16) + 1;
                randomColonne = (int)(Math.random() * 16) + 1;
            }

            Color robotColor = Color.RED;
            switch (i) {
                case 1 -> robotColor = Color.BLUE;
                case 2 -> robotColor = Color.GREEN;
                case 3 -> robotColor = Color.YELLOW;
            }

            Robot robot = new Robot(robotColor);
            this.cellules[randomLigne][randomColonne].addRobot(robot);

            System.out.println("Robot " + i + " : " + randomLigne + "," + randomColonne);
        }
    }

    private void initMiniPlateau() {
        this.miniPlateau = new Cellule[4][8][8];

        for (int b = 0; b < 4; b++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    this.miniPlateau[b][i][j] = new Cellule(new Position(i, j));
                }
            }
        }
    }

    private void addMursAMiniPlateau() {
        this.miniPlateau[0][2][2].addMurs(Orientation.OUEST);
        this.miniPlateau[0][2][2].addMurs(Orientation.NORD);
        this.miniPlateau[0][2][2].addMurs(Orientation.SUD);

    }

    private void rotateMiniPlateauADroite(int index, int nombreDeRotations) {
        Cellule[][] miniPlateau = this.miniPlateau[index];

        for (int n = 0; n < nombreDeRotations; n++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    // get rotated  board cellule
                    Cellule tempCellule = miniPlateau[i][j];

                    miniPlateau[i][j] = miniPlateau[8 - 1 - j][i];
                    miniPlateau[8 - 1 - j][i] = miniPlateau[8 - 1 - i][8 - 1 - j];
                    miniPlateau[8 - 1 - i][8 - 1 - j] = miniPlateau[j][8 - 1 - i];
                    miniPlateau[j][8 - 1 - i] = tempCellule;

                    Cellule cellule = miniPlateau[i][j];

                    // applique rotation vers la droite sur la cellule
                    cellule.rotateMurADroite(1);
                }
            }
        }
    }

    public void constructPlateauDepuisMiniPlateau() {
        initMiniPlateau();
        addMursAMiniPlateau();

        // ordre random de chaque plateau
        List<Integer> randomIndexes = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(randomIndexes);

        // rotation aléatoire
        for (Integer index : randomIndexes) {
            int numberOfRotations = (int) (Math.random() * 4);
            rotateMiniPlateauADroite(index, numberOfRotations);
        }

        // Initialise cellule dans plateau
        this.cellules = new Cellule[17][17];

        for (int i = 1; i < 17; i++) {
            for (int j = 1; j < 17; j++) {
                this.cellules[i][j] = new Cellule(new Position(i, j));
            }

        }

        // Combine plateau
        for (int r = 1; r < 17; r++) {
            for (int c = 1; c < 17; c++) {

                // obtient l'indice du mini plateau
                int index;

                if (r <= 8) {
                    index = c <= 8 ? 0 : 1;
                } else {
                    index = c <= 8 ? 2 : 3;
                }



                // Obtient cellule des murs pour le mini plateau
                Cellule[][] miniPlateau = this.miniPlateau[randomIndexes.get(index)];
                Cellule cellule = miniPlateau[r - 1 - (index < 2 ? 0 : 8)][c - 1 - (index % 2) * 8];
                List<Mur> murs = cellule.getMurs();

                if (cellule.presenceMur()) {
                    // Set up mur des cellules
                    this.cellules[r][c].setMurs(murs);
                }
            }
        }

        // Pour les bordures
        faireBoxCentral();
        addMursSurPlateau();
        setJetons();
        setJetonsSurCellule();
    }


    //Ajout des jetons dans les boxes
    public void setJetonsSurCellule(){
        this.cellules[2][4].addTypeJeton(this.jetons.get(0));
        this.cellules[2][13].addTypeJeton(this.jetons.get(1));
        this.cellules[4][7].addTypeJeton(this.jetons.get(2));
        this.cellules[4][10].addTypeJeton(this.jetons.get(3));
        this.cellules[5][2].addTypeJeton(this.jetons.get(4));
        this.cellules[7][15].addTypeJeton(this.jetons.get(5));
        this.cellules[6][11].addTypeJeton(this.jetons.get(6));
        this.cellules[7][5].addTypeJeton(this.jetons.get(7));
        this.cellules[9][13].addTypeJeton(this.jetons.get(8));
        this.cellules[10][11].addTypeJeton(this.jetons.get(9));
        this.cellules[10][4].addTypeJeton(this.jetons.get(10));
        this.cellules[12][6].addTypeJeton(this.jetons.get(11));
        this.cellules[12][10].addTypeJeton(this.jetons.get(12));
        this.cellules[13][15].addTypeJeton(this.jetons.get(13));
        this.cellules[14][2].addTypeJeton(this.jetons.get(14));
        this.cellules[15][7].addTypeJeton(this.jetons.get(15));
        this.cellules[15][14].addTypeJeton(this.jetons.get(16));



    }

    //Creation liste des pieces objectif pour chaque tour
    public void setListJetonObjectif(){
        this.goals = this.jetons;
        Collections.shuffle(this.goals);
    }

    public void setTypeJetonDansJetonObjectifBox(Jeton jeton){
        this.cellules[8][8].addTypeJeton(jeton);
        this.cellules[9][8].addTypeJeton(jeton);
        this.cellules[8][9].addTypeJeton(jeton);
        this.cellules[9][9].addTypeJeton(jeton);
    }



}
