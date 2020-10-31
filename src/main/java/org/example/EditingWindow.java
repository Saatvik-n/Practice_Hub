package org.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

class EditingWindow {
    private Stage EditWindow;
    private Scene sc;
    private String question_set_name, directory;
    int count; // this is used to keep track of the current question
    int max;
    static TextArea q_select;
    static TextArea answer_text;
    /*
    Save the questions and answers onto an array list
    Then using copy them from the arraylist to XML document
     */
    public ArrayList<String> question_list;
    public ArrayList<String> answer_list;
    public ArrayList<String> image_path_list;
    EditingWindow(String directory)
    {
        count = 0;
        max = 0;
        question_list = new ArrayList<>();
        answer_list = new ArrayList<>();
        image_path_list = new ArrayList<>();
        this.directory = directory;
    }
    //Constructor to use when opening window in edit mode

    EditingWindow(ArrayList<String> question_list, ArrayList<String> answer_list, ArrayList<String> image_path_list, String directory)
    {
        this.question_list = question_list;
        this.answer_list = answer_list;
        this.image_path_list = image_path_list;

        this.directory = directory;
        count = 0;
        max = question_list.size();
    }
    void display_edit_window(String question_set)
    {
        //
        question_set_name = question_set;
        //The window
        EditWindow = new Stage();
        EditWindow.initModality(Modality.APPLICATION_MODAL);
        EditWindow.setTitle("Create or Edit");
        //VBox is used
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));

        //Name of question set
        Label selected_question_set = new Label("Question set is: ");
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
        if (max == 0) {
            total_q.setText("1");
        }
        else {
            total_q.setText(Integer.toString(max));

        }

        HBox question_info = new HBox(10);
        question_info.setPadding(new Insets(10));

        question_info.getChildren().addAll(current_question_count, see_count, out_of, total_q);

        box.getChildren().add(question_info);


        // Question parts
        Label question = new Label("Question");
        q_select = new TextArea();
        //if editing, then display the first question
        if (max != 0) {
            q_select.setText(question_list.get(0));
        }
        q_select.setPrefHeight(70);
        q_select.setPrefWidth(200);
        q_select.setStyle("-fx-font-size: 14");

        // Answer parts
        Label answer = new Label("The answer");
        answer_text = new TextArea();
        //if editing, then display the first answer
        if (max != 0) {
            answer_text.setText(answer_list.get(0));
        }
        answer_text.setPrefHeight(300);
        answer_text.setPrefWidth(200);
        answer_text.setStyle("-fx-font-size: 14");

        //Adding question and answer parts
        box.getChildren().addAll(question, q_select, answer, answer_text);

        //The buttons
        Button next = new Button("Next");
        next.setOnAction(e -> {onClickNext(see_count, total_q);});

        Button back = new Button("Back");
        back.setOnAction(e -> {onClickBack(see_count);});

        Button addImage = new Button("Add or Change Image");
        addImage.setOnAction(e -> onClickAddImage());

        Button done = new Button("Done");
        done.setOnAction(e -> {onClickDone();});

        //Creating a HBox to place these buttons
        HBox button_row = new HBox(30);
        button_row.getChildren().addAll(back, next, addImage, done);

        //Adding this HBox to the main VBox
        box.getChildren().add(button_row);


        // Setting scene and displaying window
        sc = new Scene(box, 700, 600);
        EditWindow.setScene(sc);
        EditWindow.show();
    }
    //basic getters
    String getQuestion()
    {
        return q_select.getText().trim();
    }
    String getAnswer()
    {

        return answer_text.getText().trim();
    }
    String getQuestion_set_name()
    {
        return question_set_name;
    }
    //these methods are what the buttons do
    void onClickNext(TextField counter, TextField maxTF)
    {
        String question = getQuestion();
        String answer = getAnswer();
        if (question.equals("") && answer.equals("")) {
            new Errors().showGenericError("Enter something before going to next question");
            return;
        }
        if (count == max)
        {
            //add the current question and answer to the list
            question_list.add(question);
            answer_list.add(answer);
            //increment count for the next question, and clear the textarea
            ++count;
            ++max;
            q_select.clear();
            answer_text.clear();
            System.out.println("question added = " + question_list.get(count-1) + ", count = " + count);

            //if there
            if (image_path_list.size() < count) {
                image_path_list.add("");
            }
            counter.setText(Integer.toString(count + 1));
            maxTF.setText(Integer.toString(max + 1));
        }
        else
        {
            //change the current question and answers
            question_list.set(count, question);
            answer_list.set(count, answer);
            //increment count to show the next set
            ++count;
            //set the textArea to show the next question and answers
            if (count == max)
            {
                q_select.clear();
                answer_text.clear();
                maxTF.setText(Integer.toString(max + 1));
            }
            else
            {
                q_select.setText(question_list.get(count));
                answer_text.setText(answer_list.get(count));
            }
            counter.setText(Integer.toString(count + 1));
        }
        System.out.println("count = " + count + ", max = " + max);
    }
    void onClickBack(TextField counter)
    {
        //these are the question and answers for the current count
        String question = getQuestion();
        String answer = getAnswer();
        if (count == 0)
        {
            return;
        }
        else
        {
            //this is if we are on the last question, and we go back to make changes
            //You still want the last question and answer you have put to be saved
            if (count == max)
            {
                question_list.add(question);
                answer_list.add(answer);
                if (image_path_list.size() <= max) {
                    image_path_list.add("");
                }
                ++max;
            }
            else
            {
                question_list.set(count, question);
                answer_list.set(count, answer);
            }
            System.out.println("question at count = " + count + " set to = " + question);
            //decrement count
            --count;
            //set the textArea to show the previous question and answers
            q_select.setText(question_list.get(count));
            answer_text.setText(answer_list.get(count));

            counter.setText(Integer.toString(count + 1));
        }
    }
    void onClickAddImage() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(EditWindow); //it opens the file chooser on this window
        String imagePath = selectedFile.getAbsolutePath();
        System.out.println("image path = " + imagePath);
        if (count == max) {
            image_path_list.add(imagePath);
            return;
        }
        else {
            image_path_list.set(count, imagePath);
        }

    }
    void onClickDone()
    {
        //making sure that, whichever the question that count is at, gets included in the ArrayList
        String question = getQuestion();
        String answer = getAnswer();
        if (question.equals("") && answer.equals("")) {

        }
        else if (count == max)
        {
            System.out.println("count = max");
            question_list.add(question);
            answer_list.add(answer);
            if (image_path_list.size() <= max) {
                System.out.println("adding another image at last index");
                image_path_list.add("");
            }
        }
        else
        {
            question_list.set(count, question);
            answer_list.set(count, answer);
        }

        //creating a new file, and setting its ArrayLists as the ones from this Window
        System.out.println("directory = " + directory);
        CreateNewFile cr = new CreateNewFile(directory, question_set_name, question_list, answer_list, image_path_list);

        System.out.println("image path list");
        for (String path : image_path_list) {
            System.out.println(path);
        }
        cr.createNewFile();
        //close the window
        EditWindow.close();
    }


}
