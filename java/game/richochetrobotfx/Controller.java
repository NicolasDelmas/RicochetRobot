package game.richochetrobotfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.stream.Stream;


public class Controller implements Initializable {

    private static final Game game = new Game();
    private final Pane[][] board = new Pane[16][16];
    private final String filePathRoot = "src/main/resources/game/richochetrobotfx/";
    public Robot robotSelectionne;

    private Label timerLabel = new Label(), splitTimerLabel = new Label();
    private DoubleProperty timeSeconds = new SimpleDoubleProperty(), splitTimeSeconds = new SimpleDoubleProperty();
    private Duration time = Duration.ZERO, splitTime = Duration.ZERO;

    @FXML
    private GridPane boardPane;
    @FXML
    private Label indication;
    @FXML
    private ImageView currentImageGoal;
    @FXML
    private Button gameBtn;
    @FXML
    private Label timerText;
    @FXML
    private Label scorePlayerOne;
    @FXML
    private Label scorePlayerTwo;
    @FXML
    private ImageView goalCenterImage;
    @FXML
    private Spinner<Integer> spinnerPlayerOne;
    @FXML
    private Spinner<Integer> spinnerPlayerTwo;
    @FXML
    private Label hitsNumberChoicePlayerOne;
    @FXML
    private Label hitsNumberChoicePlayerTwo;
    @FXML
    private RadioButton radioPlayerOne;
    @FXML
    private RadioButton radioPlayerTwo;
    @FXML
    private ToggleGroup radioGroup = new ToggleGroup();
    @FXML
    private Label dotPlayerOne;
    @FXML
    private Label dotPlayerTwo;
    @FXML
    private Text indicationNumberOfHits;
    @FXML
    private Text stateRound;
    private int launchTimer = 30;               // Temps pour trouver les coups
    private boolean booleanTimerArret;
    private boolean itIsWin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Lancer le jeu
        game.play();
        this.scorePlayerOne.setText("");
        this.scorePlayerTwo.setText("");
        itIsWin = false;

        //Création du panneau
        Scene scene = boardPane.getScene();
        boardGeneration();
        this.stateRound.setVisible(false);
        this.spinnerPlayerOne.setVisible(false);
        this.spinnerPlayerTwo.setVisible(false);
        this.radioPlayerOne.setVisible(false);
        this.radioPlayerTwo.setVisible(false);
        this.dotPlayerOne.setVisible(false);
        this.dotPlayerTwo.setVisible(false);
        this.indicationNumberOfHits.setVisible(false);
        game.Status = Game.Status.LAUNCH_TIMER;
    }


    @FXML
    public void handleGameBtn(){
        switch (game.Status) {
            case LAUNCH_TIMER -> {
                game.reinitialisePlayer();   //Reset le nombre de déplacement à 0, reset la mise effectuée
                this.stateRound.setText("Entrez le nombre de coup minimum trouvé");
                this.stateRound.setVisible(true);
                this.spinnerPlayerOne.setVisible(true);
                this.spinnerPlayerTwo.setVisible(true);
                this.indicationNumberOfHits.setVisible(true);
                hitsNumberChoicePlayerOne.setVisible(false);
                hitsNumberChoicePlayerTwo.setVisible(false);
                this.scorePlayerOne.setVisible(false);
                this.scorePlayerTwo.setVisible(false);
                this.gameBtn.setText("Confirmer le nombre de coups");
                game.Status = Game.Status.PREPARE_ROUND;
                launchSpinners();
                getCherchePremierJoueur();
                timer();
                movePlayer();
                displayGoal();
            }
            case PREPARE_ROUND -> {
                this.scorePlayerOne.setText("");
                this.scorePlayerTwo.setText("");
                launchTimer = 0;
                this.spinnerPlayerOne.setVisible(false);
                this.spinnerPlayerTwo.setVisible(false);
                this.radioPlayerOne.setVisible(false);
                this.radioPlayerTwo.setVisible(false);
                this.indicationNumberOfHits.setVisible(false);
                if (game.getJoueurUn().getIsPremierAMiser()){
                    this.dotPlayerOne.setVisible(true);
                    this.dotPlayerTwo.setVisible(false);
                } else if (game.getJoueurDeux().getIsPremierAMiser()) {
                    this.dotPlayerTwo.setVisible(true);
                    this.dotPlayerOne.setVisible(false);
                }
                this.gameBtn.setVisible(false);
                this.hitsNumberChoicePlayerOne.setVisible(true);
                this.hitsNumberChoicePlayerTwo.setVisible(true);
                game.setPremierTour();
                handleGameBtn();
            }
            case PLAYER_ONE_TURN -> {
                this.scorePlayerOne.setVisible(true);
                this.scorePlayerTwo.setVisible(true);
                this.stateRound.setVisible(true);
                System.out.println("\n" + "C'est à J1 de jouer");
                this.stateRound.setText("Joueur 1 : à vous de jouer");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Joueur 1 : vos coups");
                alert.setContentText("Joueur 1 : à vous de jouer");
                alert.showAndWait();
                movePlayer();
            }case PLAYER_TWO_TURN -> {
                this.scorePlayerOne.setVisible(true);
                this.scorePlayerTwo.setVisible(true);
                this.stateRound.setVisible(true);
                System.out.println("\n" + "C'est à J2 de jouer");
                this.stateRound.setText("Joueur 2 : à vous de jouer");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Joueur 2 : vos coups");
                alert.setContentText("Joueur 2 : à vous de jouer");
                alert.show();
                movePlayer();
            }case END_ROUND -> {
                this.stateRound.setVisible(true);
                System.out.println("Tour fini");
                this.gameBtn.setText("Nouveau Tour");
                if(itIsWin && game.getJoueurUn().isTourGagne()){
                    this.stateRound.setText(game.getJoueurUn().getNom() + " a gagné ce tour");
                }else if(itIsWin && game.getJoueurDeux().isTourGagne()){
                    this.stateRound.setText(game.getJoueurDeux().getNom() + " a gagné ce tour");
                }
                else {
                    this.stateRound.setText("\n Personne n'a gagné");
                }
                this.gameBtn.setVisible(true);
                game.getNouveauJetonObjectif();
                Game.Status = Game.Status.LAUNCH_TIMER;
            }
        }
    }

    private void boardGeneration(){
        Image cellImage = new Image(new File(filePathRoot + "plateau/Cellule.PNG").toURI().toString() , 44, 44, false, false);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                // JavaFX Classe StackPane
                StackPane stackPane = new StackPane();

                // Ajout d'un identificateur
                stackPane.setId(Integer.toString(i + 1) + "," + Integer.toString(j + 1));

                // Ajout de l'image de la cellule
                ImageView cellImageView = new ImageView(cellImage);
                stackPane.getChildren().add(cellImageView);

                Cellule celluleActuel = game.getPlateau().getCellules()[i + 1][j + 1];

                //Ajout Murs
                String wallImageFilename = null;
                if (celluleActuel.presenceMur()) {
                    for (int w = 0; w < celluleActuel.getMurs().size(); w++) {
                        switch (celluleActuel.getMurs().get(w).getOrientation()) {
                            case NORD -> wallImageFilename = "NordMur.png";
                            case SUD -> wallImageFilename = "SudMur.png";
                            case EST -> wallImageFilename = "EstMur.png";
                            case OUEST -> wallImageFilename = "OuestMur.png";
                        }



                        // Ajout image du mur sur la cellule
                        Image wallImage = new Image(new File(filePathRoot + "plateau/" + wallImageFilename).toURI().toString() , 44, 44, false, false);
                        ImageView wallImageView = new ImageView(wallImage);
                        stackPane.getChildren().add(wallImageView);
                    }
                }


                // Ajout d'une pièce / jeton
                String symbolImageFilename = null;
                if(celluleActuel.getPresenceJeton()){
                    if (Color.BLUE.equals(celluleActuel.getJeton().getColor())) {
                        switch (celluleActuel.getJeton().getTypeJeton()) {
                            case SOLEIL -> symbolImageFilename = "SoleilBleu.png";
                            case LUNE -> symbolImageFilename = "LuneBleu.png";
                            case PLANETE -> symbolImageFilename = "PlaneteBleu.png";
                            case ETOILE -> symbolImageFilename = "EtoileBleu.png";
                        }
                    } else if (Color.GREEN.equals(celluleActuel.getJeton().getColor())) {
                        switch (celluleActuel.getJeton().getTypeJeton()) {
                            case SOLEIL -> symbolImageFilename = "SoleilVert.png";
                            case LUNE -> symbolImageFilename = "LuneVert.png";
                            case PLANETE -> symbolImageFilename = "PlaneteVert.png";
                            case ETOILE -> symbolImageFilename = "EtoileVert.png";
                        }
                    } else if (Color.YELLOW.equals(celluleActuel.getJeton().getColor())) {
                        switch (celluleActuel.getJeton().getTypeJeton()) {
                            case SOLEIL -> symbolImageFilename = "SoleilJaune.png";
                            case LUNE -> symbolImageFilename = "LuneJaune.png";
                            case PLANETE -> symbolImageFilename = "PlaneteJaune.png";
                            case ETOILE -> symbolImageFilename = "EtoileJaune.png";
                        }
                    } else if (Color.RED.equals(celluleActuel.getJeton().getColor())) {
                        switch (celluleActuel.getJeton().getTypeJeton()) {
                            case SOLEIL -> symbolImageFilename = "SoleilRouge.png";
                            case LUNE -> symbolImageFilename = "LuneRouge.png";
                            case PLANETE -> symbolImageFilename = "PlaneteRouge.png";
                            case ETOILE -> symbolImageFilename = "EtoileRouge.png";
                        }
                    } else if (Color.BLACK.equals(celluleActuel.getJeton().getColor())) {
                        symbolImageFilename = "Vortex.png";
                    }
                }

                Image imageJeton = new Image(new File(filePathRoot + "pieces/" + symbolImageFilename).toURI().toString() , 35, 35, false, false);
                ImageView imageViewJeton = new ImageView(imageJeton);
                stackPane.getChildren().add(imageViewJeton);

                // affichage des robots
                if (game.getPlateau().getCellules()[i + 1][j + 1].getPresenceRobot()) {
                    Robot robot = game.getPlateau().getCellules()[i + 1][j + 1].getRobotActuel();

                    String filename = getNomImageRobot(robot.getColor());

                    Image robotImage = new Image(new File("src/main/resources/game/richochetrobotfx/robots/" + filename).toURI().toString() , 44, 44, false, false);
                    ImageView robotImageView = new ImageView(robotImage);
                    stackPane.getChildren().add(robotImageView);
                }

                if((i != 7 && i != 8) || (j != 7 && j != 8)){
                      // td
// boucle

                }else{

                    stackPane = new StackPane();
                    ImageView goalBoxView = new ImageView();
                    goalBoxView.imageProperty().bind(currentImageGoal.imageProperty());
                    stackPane.getChildren().add(goalBoxView);
                    //affichage de la pièce choisi (jetonobjectif) sur le centre du mini plateau
                }
                // Ajout outil graph stackPane sur le plateau
                this.board[i][j] = stackPane;
                this.boardPane.add(stackPane, i, j);

            }
        }

    }

    private String getNomImageRobot(Color robotCouleur) {
        if (robotCouleur.equals(Color.RED)) {
            return "robotRouge.png";
        } else if (robotCouleur.equals(Color.BLUE)) {
            return "robotBleu.png";
        } else if (robotCouleur.equals(Color.GREEN)) {
            return "robotVert.png";
        } else {
            return "robotJaune.png";
        }
    }

    public void deplacement(Orientation direction) {
        Cellule currentCellule = robotSelectionne.getCelluleActuel();

        if (robotSelectionne != null) {
            while (game.deplacementValide(currentCellule, direction)) {
                Position oldPosition = currentCellule.getPosition();
                Position newPosition = currentCellule.getPosition().nextPosition(direction);

                System.out.print("Ancien titre: ");
                System.out.print(currentCellule.getPosition().getLigne() + "," + currentCellule.getPosition().getColonne());
                System.out.print(" Nouveau titre: ");
                System.out.println(newPosition.getLigne() + "," + newPosition.getColonne());

                game.deplacement(currentCellule, direction);
                updateRobotDisplay(oldPosition, newPosition);
                currentCellule = game.getPlateau().getCellule(newPosition);
                robotSelectionne.setCelluleActuel(currentCellule);
            }

            // MAJ robot selectionné
            robotSelectionne = currentCellule.getRobotActuel();
        }
    }

    private void removeRobotFromCell(Position position) {
        int numberOfChildren = board[position.getLigne()  - 1][position.getColonne() - 1].getChildren().size();
        board[position.getLigne() - 1][position.getColonne() - 1].getChildren().remove(numberOfChildren - 1);
    }

    private void addRobotToCell(Position position) {
        Robot robot = robotSelectionne;
        String filename = getNomImageRobot(robotSelectionne.getColor());

        // Récup StackPane
        Image robotImage = new Image(new File("src/main/resources/game/richochetrobotfx/robots/" + filename).toURI().toString() , 44, 44, false, false);
        ImageView robotImageView = new ImageView(robotImage);

        board[position.getLigne() - 1][position.getColonne() - 1].getChildren().add(robotImageView);
    }

    private void updateRobotDisplay(Position oldPosition, Position newPosition) {
        removeRobotFromCell(oldPosition);
        addRobotToCell(newPosition);
    }

    private void displayGoal(){
        String symbolImageFilename = null;
        if (Color.BLUE.equals(game.getJetonObjectifActuel().getColor())){
            switch (game.getJetonObjectifActuel().getTypeJeton()) {
                case SOLEIL -> symbolImageFilename = "SoleilBleu.png";
                case LUNE -> symbolImageFilename = "LuneBleu.png";
                case PLANETE -> symbolImageFilename = "PlaneteBleu.png";
                case ETOILE -> symbolImageFilename = "EtoileBleu.png";
            }
        } else if (Color.RED.equals(game.getJetonObjectifActuel().getColor())) {
            switch (game.getJetonObjectifActuel().getTypeJeton()) {
                case SOLEIL -> symbolImageFilename = "SoleilRouge.png";
                case LUNE -> symbolImageFilename = "LuneRouge.png";
                case PLANETE -> symbolImageFilename = "PlaneteRouge.png";
                case ETOILE -> symbolImageFilename = "EtoileRouge.png";
            }
        } else if (Color.GREEN.equals(game.getJetonObjectifActuel().getColor())) {
            switch (game.getJetonObjectifActuel().getTypeJeton()) {
                case SOLEIL -> symbolImageFilename = "SoleilVert.png";
                case LUNE -> symbolImageFilename = "LuneVert.png";
                case PLANETE -> symbolImageFilename = "PlaneteVert.png";
                case ETOILE -> symbolImageFilename = "EtoileVert.png";
            }
        } else if (Color.YELLOW.equals(game.getJetonObjectifActuel().getColor())) {
            switch (game.getJetonObjectifActuel().getTypeJeton()) {
                case SOLEIL -> symbolImageFilename = "EtoileJaune.png";
                case LUNE -> symbolImageFilename = "LuneJaune.png";
                case PLANETE -> symbolImageFilename = "PlanetJaune.png";
                case ETOILE -> symbolImageFilename = "Etoielpng";
            }
        } else if (Color.BLACK.equals(game.getJetonObjectifActuel().getColor())) {
            symbolImageFilename = "Vortex.png";
        }
        Image symbolImage = new Image(new File(filePathRoot + "pieces/" + symbolImageFilename).toURI().toString() , 40, 40, false, false);
        this.currentImageGoal.setImage(symbolImage);
        this.currentImageGoal.setVisible(true);
    }


    //Activation du timer après clic sur bouton "commencer"
    private void timer(){
        launchTimer = 30;
        booleanTimerArret = false;
        timerText.setVisible(true);
        timerText.setText(String.valueOf(launchTimer));
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e ->{
            if (launchTimer == 0) {
                if(!booleanTimerArret) {
                    timerText.setText("");
                    launchTimer = 0;
                    booleanTimerArret = true;
                    game.Status = Game.Status.PREPARE_ROUND;
                    System.out.println("encore une fois");
                    handleGameBtn();
                }
            }else {
                launchTimer--;
                timerText.setText(String.valueOf(launchTimer));
            }
            e.consume();
        }));
        timeline.setCycleCount(31);
        timeline.play();


    }

    private void movePlayer(){
        if(booleanTimerArret){
            for (int i = 0; i < 16; i++){
                for (int j = 0; j < 16; j++){
                    this.board[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            String id = ((StackPane) event.getSource()).getId();
                            System.out.println("Titre id: " + id);

                            // Récup robot de la cellule
                            int[] coordinates = Stream.of(id.split(",")).mapToInt(Integer::parseInt).toArray();
                            Cellule currentCellule = game.getPlateau().getCellules()[coordinates[0]][coordinates[1]];

                            if (currentCellule.getPresenceRobot()) {
                                robotSelectionne = currentCellule.getRobotActuel();
                                robotSelectionne.setCelluleActuel(currentCellule);

                            }
                            event.consume();
                        }
                    });
                }
            }
            robotSelectionne = null;
        }

    }
    // Interface FX : design Flèches pour la jauge du nombre de coup pour la mise
    private void launchSpinners(){
        SpinnerValueFactory<Integer> valueFactoryPlayerOne = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactoryPlayerOne.setValue(1);
        SpinnerValueFactory<Integer> valueFactoryPlayerTwo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactoryPlayerTwo.setValue(1);
        spinnerPlayerOne.setValueFactory(valueFactoryPlayerOne);
        spinnerPlayerTwo.setValueFactory(valueFactoryPlayerTwo);
        game.getJoueurUn().setNombreDeCoupMise(spinnerPlayerOne.getValue());
        game.getJoueurDeux().setNombreDeCoupMise(spinnerPlayerTwo.getValue());
        hitsNumberChoicePlayerOne.setText(String.valueOf("Nombre de coup choisi : " + game.getJoueurUn().getNombreDeCoupMise()));
        hitsNumberChoicePlayerTwo.setText(String.valueOf("Nombre de coup choisi : " + game.getJoueurDeux().getNombreDeCoupMise()));
        this.spinnerPlayerOne.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                game.getJoueurUn().setNombreDeCoupMise(spinnerPlayerOne.getValue());
                hitsNumberChoicePlayerOne.setText(String.valueOf("Nombre de coup choisi : " + game.getJoueurUn().getNombreDeCoupMise()));
            }
        });
        this.spinnerPlayerTwo.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                game.getJoueurDeux().setNombreDeCoupMise(spinnerPlayerTwo.getValue());
                hitsNumberChoicePlayerTwo.setText(String.valueOf("Nombre de coup choisi : " + game.getJoueurDeux().getNombreDeCoupMise()));
            }
        });
    }

    public void getCherchePremierJoueur(){
        this.radioPlayerOne.setToggleGroup(this.radioGroup);
        this.radioPlayerTwo.setToggleGroup(this.radioGroup);
        this.radioPlayerOne.setVisible(true);
        this.radioPlayerTwo.setVisible(true);
        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (radioGroup.getSelectedToggle() != null) {
                    RadioButton button = (RadioButton) radioGroup.getSelectedToggle();
                    if (button == radioPlayerOne){
                        game.getJoueurUn().setPremierAMiser(true);
                        game.getJoueurDeux().setPremierAMiser(false);
                    }else if(button == radioPlayerTwo){
                        game.getJoueurDeux().setPremierAMiser(true);
                        game.getJoueurUn().setPremierAMiser(false);
                    }
                }
            }
        });
    }

    public void setCoups(){

        if (game.getJoueurUn().getIsMonTour() && (game.getJoueurUn().getNombreDeCoup() >= game.getJoueurUn().getNombreDeCoupMise())){
            game.setProchainTour();
            handleGameBtn();
        } else if (game.getJoueurDeux().getIsMonTour() && (game.getJoueurDeux().getNombreDeCoup() >= game.getJoueurDeux().getNombreDeCoupMise())) {
            game.setProchainTour();
            handleGameBtn();
        }

        if (game.getJoueurUn().getIsMonTour() && game.getJoueurUn().getNombreDeCoup() < game.getJoueurUn().getNombreDeCoupMise()){
            game.getJoueurUn().setNombreDeCoup(game.getJoueurUn().getNombreDeCoup() + 1);
            System.out.println(game.getJoueurUn().getNombreDeCoup());
            scorePlayerOne.setText(String.valueOf(game.getJoueurUn().getNombreDeCoup()));
            itIsWin = game.itIsWin(robotSelectionne);
        }else if(game.getJoueurDeux().getIsMonTour() && game.getJoueurDeux().getNombreDeCoup() < game.getJoueurDeux().getNombreDeCoupMise()) {
            game.getJoueurDeux().setNombreDeCoup(game.getJoueurDeux().getNombreDeCoup() + 1);
            System.out.println(game.getJoueurDeux().getNombreDeCoup());
            scorePlayerTwo.setText(String.valueOf(game.getJoueurDeux().getNombreDeCoup()));
            itIsWin = game.itIsWin(robotSelectionne);
        }else {
            System.out.println("CACTUS");
        }
    }

    public boolean isFinito(){
        if((Game.Status != Game.Status.PLAYER_ONE_TURN &&  Game.Status != Game.Status.PLAYER_TWO_TURN) || itIsWin || (game.getJoueurUn().getNombreDeCoup() + game.getJoueurDeux().getNombreDeCoup() == game.getJoueurUn().getNombreDeCoupMise() + game.getJoueurDeux().getNombreDeCoupMise())){
            System.out.println("YES");
            Game.Status = Game.Status.END_ROUND;
            handleGameBtn();
            return true;
        }else {
            return false;
        }
    }

}
