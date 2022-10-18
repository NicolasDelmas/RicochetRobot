package game.richochetrobotfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    Controller controller;
    Game game;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("RicochetBing!");
        stage.setScene(scene);
        stage.show();

        // Méthode GET la classe Controller

        controller = fxmlLoader.getController();

        initKeyEventHandler(scene);
    }

    private void initKeyEventHandler(Scene scene) {
        if(Game.Status != Game.Status.END_ROUND){
            scene.setOnKeyPressed(e -> {
                System.out.println("Action réalisé");
                if (controller.isFinito()) {
                    return;
                }
                Robot selectedRobot = controller.robotSelectionne;
                if (selectedRobot != null) {
                    switch (e.getCode()) {
                        case Z -> controller.deplacement(Orientation.NORD);
                        case S -> controller.deplacement(Orientation.SUD);
                        case D -> controller.deplacement(Orientation.EST);
                        case Q -> controller.deplacement(Orientation.OUEST);

                        case UP -> controller.deplacement(Orientation.NORD);
                        case DOWN -> controller.deplacement(Orientation.SUD);
                        case RIGHT -> controller.deplacement(Orientation.EST);
                        case LEFT -> controller.deplacement(Orientation.OUEST);
                    }
                    controller.setCoups();
                } else {
                    System.out.println("Vous devez d'abord choisir un robot !");
                }
                e.consume();
            });
        }

    }

    public static void main(String[] args) {
        launch();
    }
}