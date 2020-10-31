package org.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
class Errors {
    Stage window;
    Scene s;
    Errors()
    {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
    }
    void showGenericError(String message) {
        HBox h = new HBox();
        h.setPadding(new Insets(10));
        Label nomorequestions = new Label(message);
        h.getChildren().addAll(nomorequestions);
        s = new Scene(h, 300, 100);

        //opening window
        window.setScene(s);
        window.showAndWait();
    }
    void NoMoreQuestions()
    {
        showGenericError("No questions left");
    }
    void ShowFolderError() {
        showGenericError("This is a folder, not a question set");
    }
}
