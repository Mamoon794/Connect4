package Connect4;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Connect4Game extends Application implements EventHandler<ActionEvent> {

    private Label text;
    private final Circle[][] circles = new Circle[7][6];
    private Pane body;
    private Scene scene2;
    private Button[] columns;


    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setResizable(false);
        window.centerOnScreen();

        window.setTitle("Connect4");
    }
}