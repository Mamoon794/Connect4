

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Confirm {

    private static boolean yesNo = false;


    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(100);

        Label label = new Label();
        label.setFont(new Font("Dialog", 15));
        label.setText(message);

        //Create two buttons
        Button yes = new Button("Yes");
        Button no = new Button("No");

        no.setOnAction(e -> {
            yesNo = false;
            window.close();
        });

        yes.setOnAction(e -> {
            yesNo = true;
            window.close();
        });

        window.setOnCloseRequest(e-> yesNo = false);


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(5, 10, 5, 10));
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return yesNo;
    }

}
