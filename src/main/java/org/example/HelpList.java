package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpList {
    private Stage helpWindow;
    private Scene s;
    public void showAbout()
    {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        Label about_text = new Label("App to help you study");
        box.getChildren().addAll(about_text);
        s = new Scene(box, 200,100);

        //setting scene and displaying window
        helpWindow = new Stage();
        helpWindow.setScene(s);
        helpWindow.show();
    }
}
