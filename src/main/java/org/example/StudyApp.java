package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/*
This is the opening window, where you see the question sets

 */
public class StudyApp extends Application {
    private static String directory;
    private static ArrayList<String> q_sets;
    @Override
    public void start(Stage PrimaryStage) throws IOException
    {

        //Directory Chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        //This is the initial directory from where we take the question sets
        directoryChooser.setInitialDirectory(new File("C:\\Users\\Saatvik\\Documents\\Java"));
        //The initial path
        directory = directoryChooser.getInitialDirectory().getAbsolutePath();
        //For the Menu bar
        MenuBar mb = new MenuBar();
        //Item list for file menu
        Menu file = new Menu("File");
        MenuItem file1 = new MenuItem("New Set"); //to create a new set
        MenuItem file2 = new MenuItem("Select Folder");// to select the folder of files
        MenuItem file3 = new MenuItem("Refresh"); //to refresh the question sets in current folder
        MenuItem file4 = new MenuItem("New Folder");
        file.getItems().addAll(file1, file2, file3, file4);

        //Item list for edit menu
        Menu edit = new Menu("Edit");
        MenuItem edit1 = new MenuItem("Edit set");
        MenuItem edit2 = new MenuItem("Delete set");
        MenuItem edit3 = new MenuItem("Rename set");
        edit.getItems().addAll(edit1, edit2, edit3);


        //Item list for about menu
        Menu help = new Menu("Help");
        MenuItem help1 = new MenuItem("About");
        help.getItems().add(help1);
        help1.setOnAction(e ->
        {
            new HelpList().showAbout();
        });

        //Add file, edit menu to MenuBar
        mb.getMenus().addAll(file, edit, help);

        //Tha main window
        PrimaryStage.setTitle("Study App 1.1");
        Scene s1;
        //gets question list set
        q_sets = (getQuestionSetList(directory));

        //Main Vbox to hold the menubar, and the 2nd VBox
        VBox main = new VBox(0);
        main.setPadding(new Insets(0));//no padding for main VBox
        main.getChildren().add(mb);//adds menubar

        //Makes the 2nd VBox 'toprow', and adds label
        VBox toprow = new VBox(10);
        toprow.setPadding(new Insets(10, 20, 20, 20));//top, right, bottom, left
        Label l1 = new Label("Select the question paper");
        toprow.getChildren().addAll(l1);

        //List for question papers
        ListView<String> question_sets = new ListView<>();

        //Add question sets list one by one
        for (int i = 0; i < q_sets.size(); i++) {
            question_sets.getItems().add(q_sets.get(i));
        }
        question_sets.getSelectionModel().select(0);//selects 1st question set as default
        toprow.getChildren().addAll(question_sets);

        //In this section, the menu items take action

        //Where new files are created
        file1.setOnAction(e ->
        {
            new NewFile(directory, q_sets).create_new_file();
            System.out.println(directory);

        });

        //this is the menu button to change the folder
        file2.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(PrimaryStage);
            if (selectedDirectory == null) {
                return;
            }
            updateListView(question_sets, selectedDirectory.getAbsolutePath());
            System.out.println("after changing, directory = " + directory);
        });

        //This is the menu button to update the list
        file3.setOnAction(e -> {
            updateListView(question_sets, directory);
        });

        //This is the button to create a new Folder
        file4.setOnAction(e -> {
            new NewFile(directory, q_sets).create_new_folder();
        });

        //edit the selected question set
        edit1.setOnAction(e -> {
            String set = question_sets.getSelectionModel().getSelectedItem();
            if (set == null) {
                return;
            }
            System.out.println("directory at study app = " + directory);
            new OpenEdit().open_edit_window(set, directory);
            //sends a command to open edit window with the selected question set

        });
        //delete the selected set
        edit2.setOnAction(e -> {
            String set = question_sets.getSelectionModel().getSelectedItem();
            if (set == null) {
                return;
            }
            new FileHandler(directory).delete_set(set);
        });
        //rename the set
        edit3.setOnAction(e -> {
            String set = question_sets.getSelectionModel().getSelectedItem();
            if (set == null) {
                return;
            }
            new FileHandler(directory).rename_set(set);
        });


        //Button to select the question paper set
        Button selection = new Button("Select");
        toprow.getChildren().add(selection);
        selection.setOnAction(e -> {
            String temp = question_sets.getSelectionModel().getSelectedItem(); //temp string to check if item is a folder
            if (temp == null) {
                return;
            }
            if (temp.length() > 9) {
                if (temp.substring(temp.length() - 8, temp.length()).equals("(folder)")) {
                    String newDirectory = directory +"\\" + temp.substring(0, temp.length()-9);
                    updateListView(question_sets, newDirectory);
                    return;
                }
            }
            new Answer_window().displaywindow(question_sets.getSelectionModel().getSelectedItem(), directory);
        });



        main.getChildren().add(toprow);
        //This is the question set selection screen
        s1 = new Scene(main, 300, 400);
        PrimaryStage.setScene(s1);
        PrimaryStage.show();
    }
    private static ArrayList<String> getQuestionSetList(String folderName)
    {
        FileHandler temp = new FileHandler(folderName);
        ArrayList<String> res = temp.getQuestionSetList();
        ArrayList<String> res2 = temp.getFolderList();
        res.addAll(res2);

        return res;
    }
    private static void updateListView(ListView<String> question_sets, String folderName) {
        System.out.println("updatelistview = " + folderName);
        directory = new String(folderName);
        ArrayList<String> items = getQuestionSetList(folderName);
        q_sets = items;
        question_sets.getItems().clear();
        if (items.size() == 0) {
            question_sets.getItems().clear();
        }
        for (int i = 0; i < items.size(); i++) {
            question_sets.getItems().add(items.get(i));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
