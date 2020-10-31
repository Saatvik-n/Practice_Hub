package org.example;
import java.util.ArrayList;

public class OpenEdit {
    void open_edit_window(String edit_question_set, String directory) {
        XMLHandler x1 = new XMLHandler(directory, edit_question_set);

        ArrayList<String> question_list;
        ArrayList<String> answer_list;
        ArrayList<String> image_list;
        //Gets the question list and answer list
        question_list = (x1.getQuestions());
        answer_list = (x1.getAnswers());
        image_list = x1.getImagePaths();


        //Send these sets to EditingWindow
        EditingWindow edit = new EditingWindow(question_list, answer_list, image_list, directory);
        edit.display_edit_window(edit_question_set);
    }
}
