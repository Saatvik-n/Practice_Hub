package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateNewFile {
    File newQuestionSet;
    EditingWindow ew;
    String questionSetName;
    private ArrayList<String> questionList;
    private ArrayList<String> answerList;
    private ArrayList<String> imagePathList;
    private String directory;
    private String folderName;
    CreateNewFile(String directory, String questionSetName, ArrayList<String> questionList, ArrayList<String> answerList, ArrayList<String> imagePathList ) {
        this.directory = directory;
        this.questionSetName = questionSetName;

        this.questionList = questionList;
        this.answerList = answerList;
        this.imagePathList = imagePathList;
    }
    CreateNewFile(String directory, String folderName) {
        this.directory = directory;
        this.folderName = folderName;
    }
    void createNewFile()
    {

        /*
        ew = new EditingWindow();
        fileName = ew.getQuestion_set_name();//get filename from editingWindow
        String actual_file_name = fileName+".txt";
        */
        String tempfilename = directory + "\\" + questionSetName + ".xml";
        System.out.println("tempfile name = " + tempfilename);
        File f = new File(tempfilename);
        try
        {
            FileWriter fw = new FileWriter(f);
            fw.write("<document>");
            fw.write("\n");
            for (int i = 0; i < questionList.size(); i++) {
                fw.write("<question>\n");
                fw.write("<qinfo>\n");
                fw.write(questionList.get(i));
                fw.write("\n</qinfo>\n");
                fw.write("<answer>\n");
                fw.write(answerList.get(i));
                fw.write("\n</answer>\n");
                fw.write("<image>\n");
                fw.write(imagePathList.get(i));
                fw.write("\n</image>\n");
                fw.write("</question>\n");
            }
            fw.write("</document>");
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Mission failed");
        }

    }
    void createNewFolder() {
        String tempFodler = directory + "\\" + folderName;
        File f = new File(tempFodler);
        boolean res = f.mkdirs();
        if (!res) {
            new Errors().showGenericError("Couldn't be created");
        }
    }
}
