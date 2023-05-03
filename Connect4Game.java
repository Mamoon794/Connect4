

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class Connect4Game extends Application implements EventHandler<ActionEvent>{

    private Label text;
    private final Circle[][] circles = new Circle[7][6];
    private Pane body;
    private Scene scene2;
    private Button[] columns;
    private String playerName, playername2, currentplayer, colorChoice1, colorChoice2, currentColorChoice;
    private final boolean[][] all = new boolean[7][6];
    private final boolean[][] player1 = new boolean[7][6];
    private final boolean[][] player2 = new boolean[7][6];
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
//		top.setStyle("-fx-background-color: blue");
        window = primaryStage;
        window.setResizable(false);
        window.centerOnScreen();
//		window.setResizable(false);
        window.setTitle("Connect4");

        //Scene 1
        VBox selection = new VBox(10);
        selection.setPadding(new Insets(10));
        TextField player1 = new TextField("Enter first player's name");
        player1.setPromptText("Player 1 name");

        TextField player2 = new TextField();
        player2.setPromptText("Player 2 name");
        Button start = new Button("Start game");
        start.setDisable(true);

        String[] colors = {"red", "blue", "pink", "black", "white", "brown", "purple", "turquoise"};
        ComboBox<String> choices2 = new ComboBox<>();
        ComboBox<String> choices = new ComboBox<>();
        choices2.setDisable(true);
        choices.getItems().addAll(colors);
        choices.setPromptText("Player one's color choice");
        choices.setOnAction(e -> {
            choices2.setDisable(false);
            choices2.getItems().removeAll(colors);
            choices2.getItems().addAll(colors);
            choices2.getItems().remove(choices.getValue());
            start.setDisable(true);
        });


        choices2.setPromptText("Player two's color choice");
        choices2.setOnAction(e -> start.setDisable(false));

        selection.getChildren().addAll(player1, choices, player2, choices2, start);

        start.setOnAction(e -> checkPlayer(player1.getText(), player2.getText(), choices.getValue(), choices2.getValue()));
        Scene scene1 = new Scene(selection, 300 ,200);

        //Scene 2
        columns = new Button[7];

        //top side of the border
        BorderPane border = new BorderPane();
        HBox top = new HBox();
        top.setPadding(new Insets(20, 0, 0, 0));
        text = new Label();
        text.setFont(new Font("Dialog", 14));
        top.getChildren().add(text);

        //The bottom side of the border
        HBox bottom = new HBox(90);
        bottom.setPadding(new Insets(5, 5, 35, 35));
        for(int i = 0; i < 7; i++) {
            columns[i] = new Button("Column " + (i+1));
            columns[i].setOnAction(this);
            bottom.getChildren().add(columns[i]);
        }


        //body of the border layout
        body = new Pane();
        Line[] columnLines = new Line[7];
        int amount = 1100/7;

        for(int i = 1; i < 7; i++) {
            columnLines[i-1] = new Line(amount*i, 0, amount*i, 547);
            body.getChildren().add(columnLines[i-1]);
        }



        Line[] rowLines = new Line[6];
        amount = 547/6;

        for(int i = 1; i < 6; i++) {
            rowLines[i-1] = new Line(0, amount*i, 1080, amount*i);
            body.getChildren().add(rowLines[i-1]);
        }

        border.setPadding(new Insets(0, 10, 0, 10));
        body.setStyle("-fx-background-color: green");
        border.setTop(top);
        border.setCenter(body);
        border.setBottom(bottom);



        scene2 = new Scene(border, 1100, 650);
        window.setScene(scene1);
        window.show();
    }


    public void makeCircle(int column, int row, String color) {
        Color actualColor = colorConvert(color);
        int[] x = new int[] {70, 235, 390, 545, 700, 860, 1010};
        int[] y = new int[] {500, 410, 318, 227, 135, 45};
        circles[column][row] = new Circle(x[column], y[row], 40);
        circles[column][row].setFill(actualColor);

        body.getChildren().add(circles[column][row]);
    }


    public void checkPlayer(String name1, String name2, String colorchoice, String colorchoice2) {
        playerName = name1;
        playername2 = name2;
        currentplayer = name1;
        colorChoice1 = colorchoice;
        colorChoice2 = colorchoice2;
        currentColorChoice = colorchoice;

        text.setText(currentplayer + "'s turn");
        window.setScene(scene2);



    }

    public int checkRow(int column) {

        for(int i = 0; i < 6; i++) {
            if(!all[column][i])
                return i;
        }

        return -1;
    }

    public boolean checkTie() {
        for(int i = 0; i < 7; i++) {
            for(int i2 = 0; i2 < 6; i2++) {
                if(!all[i][i2])
                    return false;
            }
        }

        return true;
    }

    @Override
    public void handle(ActionEvent event) {
        int row, column;
        boolean checkWin = false;
        boolean answer;
        boolean checktie;

        if(event.getSource() == columns[0])
            column = 0;

        else if(event.getSource() == columns[1])
            column = 1;

        else if(event.getSource() == columns[2])
            column = 2;

        else if(event.getSource() == columns[3])
            column = 3;

        else if(event.getSource() == columns[4])
            column = 4;

        else if(event.getSource() == columns[5])
            column = 5;

        else
            column = 6;

        row = checkRow(column);
        all[column][row] = true;


        makeCircle(column, row, currentColorChoice);
        if(currentplayer.equals(playerName)) {
            player1[column][row] = true;
            checkWin = checkWin(player1);
            currentplayer = playername2;
            currentColorChoice = colorChoice2;
        }

        else if(currentplayer.equals(playername2)) {
            player2[column][row] = true;
            checkWin = checkWin(player2);
            currentplayer = playerName;
            currentColorChoice = colorChoice1;
        }

        if(checkWin) {
            if(currentplayer.equals(playerName))
                answer = Confirm.display("Connect4", playername2 + " is the winner and " + playerName + " is the loser"
                        + "\n\tDo you want to play again?");

            else
                answer = Confirm.display("Connect4", playerName + " is the winner and " + playername2 + " is the loser"
                        + "\n\tDo you want to play again?");

            if(answer)
                resetEverything();
            else
                window.close();
        }

        checktie = checkTie();
        if(checktie && !checkWin) {
            answer = Confirm.display("Connect4", "There was a tie between " + playerName + " and " + playername2 +
                    ". No one won\n\tDo you want to play again?");


            if(answer)
                resetEverything();
            else
                window.close();
        }


        row = checkRow(column);
        text.setText(currentplayer + "'s turn");

        if(row == -1)
            columns[column].setDisable(true);

    }

    private void resetEverything() {
        for(int i = 0; i < 7; i++) {
            for(int i2 = 0; i2 < 6; i2++) {
                if(all[i][i2])
                    body.getChildren().remove(circles[i][i2]);

                all[i][i2] = false;
                player1[i][i2] = false;
                player2[i][i2] = false;
            }
            currentplayer = playerName;
            currentColorChoice = colorChoice1;

            columns[i].setDisable(false);
        }

    }


    public Color colorConvert(String color) {
        String[] sColors = {"red", "blue", "white", "brown", "pink", "black", "purple", "turquoise"};
        Color[] cColors = {Color.RED, Color.BLUE, Color.WHITE, Color.BROWN, Color.PINK, Color.BLACK, Color.PURPLE, Color.TURQUOISE};

        for(int index = 0; index < cColors.length; index++) {
            if (color.equalsIgnoreCase(sColors[index]))
                return cColors[index];
        }

        return Color.BLACK;
    }


    public static boolean checkWin(boolean[][] player) {

        int column;
        int row;
        int amount = 0;

        for(int index = 0; index < 7; index++) {
            for(int index2 = 0; index2 < 6; index2++) {

                column = index;
                row = index2;

                if(player[column][row])
                    amount ++;

                else if(!player[column][row])
                    amount = 0;

                if(amount == 4)
                    return true;
            }
            amount = 0;
        }

        for(int index = 0; index < 6; index++) {
            for(int index2 = 0; index2 < 7; index2++) {

                column = index2;
                row = index;

                if(player[column][row])
                    amount ++;

                else if(!player[column][row])
                    amount = 0;

                if(amount == 4)
                    return true;

            }
            amount = 0;
        }

        for(int index = 0; index < 6; index++) {
            for(int index2 = 0; index2 < 7; index2++) {
                for(int index3 = 0; index3 < 6; index3++) {

                    column = index2+index3;
                    row = index3+index;

                    if(column > 6 || row >= 6)
                        continue;

                    if(player[column][row])
                        amount ++;

                    else if(!player[column][row])
                        amount = 0;

                    if(amount == 4)
                        return true;

                }
                amount = 0;
            }
        }

        for(int i2 = 0; i2 < 6; i2++) {
            for(int i = 6; i >= 0; i--) {
                for(int j = 0; j < 6; j++) {

                    column = i-j;
                    row = j+i2;

                    if(column < 0 || row >= 6)
                        continue;

                    if(player[column][row])
                        amount ++;

                    else if(!player[column][row])
                        amount = 0;

                    if(amount == 4)
                        return true;

                }
                amount = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
