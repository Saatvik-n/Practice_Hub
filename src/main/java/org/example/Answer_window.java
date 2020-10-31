package org.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

class Answer_window {
    private ArrayList<String> question_list;
    private ArrayList<String> answer_list;
    private ArrayList<String> image_path_list;
    XMLHandler x1;
    Stage ans_window;
    private int count = 0;
    private int ans_count = 0;
    Errors e;
    void displaywindow(String question_set, String directory)
    {
        e = new Errors();

        if (question_set.length() > 8){
            if (question_set.substring(question_set.length() - 8, question_set.length()).equals("(folder)")) {
                e.ShowFolderError();
                return;
            }
        }
        //Get questions from file handler onto the window
        x1 = new XMLHandler(directory, question_set);

        //Try to check if selected question set is there
        if (x1.getFile() == null || x1.getAnswers() == null || x1.getQuestions() == null)
        {
            notfound();
            return;
        }
        //Gets the question list and answer list
        question_list = (x1.getQuestions());
        answer_list = (x1.getAnswers());
        image_path_list = x1.getImagePaths();

        //New stage and title, and disables from using other window
        ans_window = new Stage();
        ans_window.initModality(Modality.APPLICATION_MODAL);
        ans_window.setTitle("Question set");
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));

        //Selected question
        Label selected_question_set = new Label("Selected question set is: ");
        TextField q_set = new TextField();
        q_set.setDisable(true); q_set.setOpacity(1);
        q_set.setText(question_set);

        box.getChildren().addAll(selected_question_set, q_set);

        //Current question count
        Label current_question_count = new Label("Current question is  ");
        TextField see_count = new TextField(); //The current question
        see_count.setPrefWidth(50);
        see_count.setDisable(true);
        see_count.setOpacity(1);
        see_count.setText(Integer.toString(count + 1));

        //The label "out of" and another text field
        Label out_of = new Label("out of ");
        TextField total_q = new TextField(); //The total no of questions
        total_q.setPrefWidth(50);
        total_q.setDisable(true);
        total_q.setOpacity(1);
        total_q.setText(Integer.toString(question_list.size()));

        //Make a HBox to hold label, and TextField
        HBox question_count_box = new HBox(20);
        question_count_box.setPadding(new Insets(0));
        question_count_box.getChildren().addAll(current_question_count, see_count, out_of, total_q);

        box.getChildren().add(question_count_box);

        //Set the file
        FileHandler fh = new FileHandler(question_set);

        //The question
        Label question = new Label("Question");
        TextArea q_select = new TextArea();
        q_select.setOpacity(1);
        q_select.setDisable(true);
        q_select.setPrefHeight(70);
        q_select.setPrefWidth(200);
        q_select.setStyle("-fx-font-size: 14");

        q_select.setText(question_list.get(count));

        //Your answer
        Label answer = new Label("Your answer");
        TextArea answer_text = new TextArea();
        answer_text.setPrefHeight(200);
        answer_text.setPrefWidth(200);
        answer_text.setStyle("-fx-font-size: 14");

        //The given answer
        Label actual_answer = new Label("Actual Answer");
        TextArea actual_answer_text = new TextArea();
        actual_answer_text.setOpacity(1);
        actual_answer_text.setEditable(false);
        actual_answer_text.setPrefHeight(300);
        actual_answer_text.setPrefWidth(200);
        actual_answer_text.setStyle("-fx-font-size: 14");

        //The button to go to next question
        Button next = new Button("Next");
        next.setOnAction(e -> next_question(q_select, actual_answer_text, answer_text, see_count));

        //The button to go back
        Button back  = new Button("Back");
        back.setOnAction(e -> back_question(q_select, actual_answer_text, answer_text, see_count));

        //The button to show answer
        Button show_answer_button = new Button("Show Answer");
        show_answer_button.setOnAction(e -> show_answer(actual_answer_text));

        //The button to show the image
        Button show_image_button = new Button("Show Image");
        show_image_button.setOnAction(e -> show_image());

        //All the buttons should be in one HBox
        HBox button_row = new HBox(30);
        button_row.getChildren().addAll(back, show_answer_button, show_image_button, next);

        box.getChildren().addAll(question, q_select, answer, answer_text);
        box.getChildren().addAll(actual_answer, actual_answer_text, button_row);

        Scene sc = new Scene(box, 800, 700);
        ans_window.setScene(sc);
        ans_window.show();
    }
    void next_question(TextArea q, TextArea ans_box, TextArea your_ans, TextField counter)
    {
        if (count == question_list.size()-1)
        {
            e.NoMoreQuestions();
            return;
        }
        ++count;
        //sets the nest question from 'question_list'
        q.setText(question_list.get(count));
        //sets the count value
        counter.setText(Integer.toString(count + 1));

        ans_box.clear();
        your_ans.clear();
    }
    void back_question(TextArea q, TextArea ans_box, TextArea your_ans, TextField counter)
    {
        if (count <= 0)
        {
            return;
        }
        --count;
        //sets the previous question from list
        q.setText(question_list.get(count));
        //sets the count value
        counter.setText(Integer.toString(count + 1));

        ans_box.clear();
        your_ans.clear();
    }
    void show_answer(TextArea ans_box)
    {
        if (count == answer_list.size())
        {
            return;
        }
        ans_count = count;
        ans_box.setText(answer_list.get(ans_count));
    }
    void show_image() {
        String path = image_path_list.get(count).strip();
        if (path.equals("") || path == null) {
            new Errors().showGenericError("No image for this question");
            return;
        }
        Image i = new Image("file:" + path); //this needs to be there
        if (i.isError()) {
            new Errors().showGenericError("No image for this question");
            return;
        }
        Stage imageDisplay = new ImageDisplay(i).displayImage();
        imageDisplay.showAndWait();
    }

    //incase file is not found
    void notfound()
    {
        //initializing
        ans_window = new Stage();
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        //adding label
        Label error = new Label("File not found or error");
        hbox.getChildren().add(error);

        Scene sc = new Scene(hbox, 300, 100);

        ans_window.setTitle("ERROR");
        ans_window.setScene(sc);
        ans_window.show();
    }

}
