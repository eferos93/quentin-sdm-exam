package quentin.UI.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.stream.Stream;

public class GameInterface {
    private final GUI gui;

    public GameInterface(GUI gui) {
        this.gui = gui;
    }

    private Button createNewButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefWidth(80);
        button.setPrefHeight(35);
        button.setOnAction(handler);
        return button;
    }

    private Stream<Button> initialButtons() {

        Button startButton = createNewButton("Start", (ActionEvent e) -> {
            gui.getStage().close();
            GUIInputHandler guiInputHandler = new GUIInputHandler();
            int size = guiInputHandler.askSize();
            String namePlayer1 = guiInputHandler.askPlayerName("1");
            String namePlayer2 = guiInputHandler.askPlayerName("2");

            gui.initGameInterface(size, namePlayer1, namePlayer2);
        });

        Button endButton = createNewButton("Exit", (ActionEvent e) -> gui.stop());

        Button rulesButton = createNewButton("Rules", (ActionEvent e) -> gui.getHostServices().showDocument("https://boardgamegeek.com/boardgame/124095/quentin"));
        return Stream.of(startButton, endButton, rulesButton);
    }

    private Stream<Button> replayButtons() {
        Button yesButton = createNewButton("Yes", (ActionEvent e) -> initUI());
        Button noButton = createNewButton("No", (ActionEvent e) -> gui.stop());
        return Stream.of(yesButton, noButton);
    }

    private void structureUI(Stream<Button> buttonStream, String content, int size, int buttonsSpacing) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setVgap(20);

        Text text = new Text(content);
        text.setFont(Font.font("Tahoma", size));

        pane.add(text, 0, 0);
        GridPane.setHalignment(text, HPos.CENTER);

        HBox hBox = new HBox();
        buttonStream.forEach(button -> hBox.getChildren().add(button));

        pane.add(hBox, 0, 1);
        hBox.setSpacing(buttonsSpacing);
        GridPane.setHalignment(hBox, HPos.CENTER);

        Scene scene = new Scene(pane);

        gui.getStage().setTitle("Quentin");
        gui.getStage().setScene(scene);
        gui.getStage().show();
    }

    protected void initUI() {
        structureUI(initialButtons(), "Quentin Game", 40, 15);
    }

    protected void endUI() {
        structureUI(replayButtons(), "Do you wanna play again?", 20, 100);
    }
}