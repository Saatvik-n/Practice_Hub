package org.example;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/*
This method is to get all the folders and XML files in a folder only,
nothing to do with the actual handling of question sets
 */
class FileHandler {
    private File f;
    private String pathname;
    FileHandler(String folder_path)
    {
        pathname = folder_path;
    }
    //this method is to get the list of all the question sets (XML Format) in the folder
    ArrayList<String> getQuestionSetList()
    {
        try{
            ArrayList<String> question_sets = new ArrayList<>();
            File folder = new File(pathname);
            File[] listOfFiles = folder.listFiles();

            String fileName;
            int lastPeriodPos;
            System.out.println("number of files = " + listOfFiles.length);
            for (int i = 0; i < listOfFiles.length; i++) {
                fileName = listOfFiles[i].getName();
                //if file length is <= 4, it means it is not an XML file, so skip it
                if (fileName.length() <= 4) {
                    break;
                }
                //Check if the file is an XML File
                System.out.println("filename = " + fileName);
                if (fileName.substring(fileName.length() - 3, fileName.length()).equals("xml")) {
                    lastPeriodPos = fileName.lastIndexOf('.');
                    fileName = fileName.substring(0, lastPeriodPos);
                    question_sets.add(fileName);
                }
            }
            return question_sets;
        }
        catch (Exception e) {
            System.out.println("error in getting question set list");
            System.out.println(e.toString());
            System.exit(0);
            return null;
        }
    }
    ArrayList<String> getFolderList()
    {
        File[] directories = new File(pathname).listFiles(File::isDirectory);
        if (directories == null) {
            return null;
        }
        ArrayList<String> folders = new ArrayList<>();
        for (File directory : directories) {
            folders.add(directory.getName() + " (folder)");
        }
        return folders;
    }
    void delete_set(String set_name) {
        String file_name = pathname;
        File file = new File(file_name);
        if (set_name.contains("(folder)")) {

        }
        else {
            file_name = pathname + "\\" + set_name + ".xml";
            file = new File(file_name);
        }
        Alert alert = new Alert(AlertType.WARNING,
                "Do you want to delete?",
                ButtonType.OK,
                ButtonType.CANCEL);
        alert.setTitle("Delete confirmation");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("file = " + file.getAbsolutePath());
            file.delete();
        }
        else {
            return;
        }
    }
    void rename_set(String set_name) {
        if (set_name.contains("(folder)")) {
            new Errors().showGenericError("No renaming folders");
            return;
        }
        String old_file_name = pathname + "\\" + set_name + ".xml";
        File oldfile = new File(old_file_name);
        String newSetName = getNewName();

        while (newSetName.isEmpty()) {
            new Errors().showGenericError("Invalid name");
            newSetName = getNewName();
        }
        System.out.println("new set name = " + newSetName);
        String newFileName = pathname + "\\" +  newSetName + ".xml";

        File newFile = new File(newFileName);
        oldfile.renameTo(newFile);

    }
    private String getNewName() {
        TextInputDialog td = new TextInputDialog("name");
        td.setTitle("Rename");
        td.setHeaderText("Renaming the question set");
        td.setContentText("Enter the new name for question set");
        Optional<String> result = td.showAndWait();

        return result.get().trim();
    }
}
