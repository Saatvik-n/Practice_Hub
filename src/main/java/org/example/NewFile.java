package org.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

class NewFile {
    Stage newFileName;
    private String pathname;
    private ArrayList<String> question_sets;
    NewFile(String pathname, ArrayList<String> question_sets) {
        this.pathname = pathname;
        this.question_sets = question_sets;
    }
    void create_new_file()
    {
        this.pathname = pathname;
        newFileName = new Stage();
        newFileName.setTitle("Create new Set");
        Scene s;
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        //items on screen
        Label l = new Label("Enter name of new question set");
        TextField set_name = new TextField();
        Button create = new Button("Create");
        create.setOnAction(e ->{
            System.out.println("pathname = " + pathname);
            if (checkAmongExistingSets(set_name.getText().trim())) {
                new Errors().showGenericError("Question set with this name already exists");
                return;
            }
            new EditingWindow(pathname).display_edit_window(set_name.getText().trim());
            newFileName.close();
        } );

        box.getChildren().addAll(l, set_name, create);

        s = new Scene(box, 400, 120);
        newFileName.setScene(s);
        newFileName.show();
    }
    void create_new_folder() {
        this.pathname = pathname;
        newFileName = new Stage();
        newFileName.setTitle("Create new Set");
        Scene s;
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        //items on screen
        Label l = new Label("Enter name of new folder");
        TextField set_name = new TextField();
        Button create = new Button("Create");
        create.setOnAction(e ->{
            System.out.println("pathname = " + pathname);
            if (checkAmongExistingSets(set_name.getText().trim())) {
                new Errors().showGenericError("Question set or Folder with this name already exists");
                return;
            }
            new CreateNewFile(pathname, set_name.getText().trim()).createNewFolder();
            newFileName.close();
        } );

        box.getChildren().addAll(l, set_name, create);

        s = new Scene(box, 400, 120);
        newFileName.setScene(s);
        newFileName.show();
    }

    private boolean checkAmongExistingSets(String newQuestionSetName) {
        for (String question_set: question_sets) {
            if (newQuestionSetName.trim().equals(question_set)) {
                return true;
            }
        }
        String temp = newQuestionSetName + " (folder)";
        for (String question_set : question_sets) {
            if (temp.equals(question_set)) {
                return true;
            }
        }
        return false;
    }

}
