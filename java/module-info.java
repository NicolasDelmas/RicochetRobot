module game.richochetrobotfx {
    requires javafx.controls;
    requires javafx.fxml;



    opens game.richochetrobotfx to javafx.fxml;
    exports game.richochetrobotfx;

}