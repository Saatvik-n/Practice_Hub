package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
/*
This class is to get the question and answer list from the XML file,
nothing to do with the folders and question set list
 */
public class XMLHandler {
    final private File f; //Path of the question set XML file
    XMLHandler(String pathname, String questionSetName) {
        String x = pathname + "\\" + questionSetName + ".xml";
        f = new File(x);
        System.out.println(f.getAbsolutePath());
    }
    Document getFile() {
        //This returns the document of the XML file if parsed successfully,
        //else null
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            return doc;
        }
        catch (Exception e)
        {
            System.out.println("Error in getting file");
            return null;
        }
    }
    //Get the questions list
    ArrayList<String> getQuestions()
    {
        ArrayList<String> question_list = new ArrayList<>();
        Document question_xml = getFile();
        if(question_xml == null)
        {
            question_list.add("Error");
            return null;
        }
        try {
            NodeList questions = question_xml.getElementsByTagName("qinfo");
            if (questions == null || questions.getLength() == 0)
            {
                return null;
            }
            int no_of_questions = questions.getLength();
            for (int i = 0; i < no_of_questions; i++) {
                Node question1 = questions.item(i);
                Element q1 = (Element)question1;
                String question_string = q1.getTextContent();
                question_string = question_string.strip(); //this is a good way to remove newlines from the string
                question_list.add(question_string);
            }
            return question_list;
        }
        catch (Exception e)
        {
            System.out.println("Error in getting questions");
            return null;
        }
    }
    //get the answer set list
    ArrayList<String> getAnswers()
    {
        ArrayList<String> answer_list = new ArrayList<>();
        Document question_xml = getFile();
        if(question_xml == null)
        {
            return null;
        }
        try {
            NodeList answers = question_xml.getElementsByTagName("answer");
            if (answers == null || answers.getLength() == 0)
            {
                return null;
            }
            int no_of_answers = answers.getLength();
            for (int i = 0; i < no_of_answers; i++) {
                Node answer1 = answers.item(i);
                Element a1 = (Element)answer1;
                String answer_string = a1.getTextContent();
                answer_string = answer_string.strip(); //this is a good way to remove newlines from the string
                answer_list.add(answer_string);
            }
            return answer_list;
        }
        catch (Exception e)
        {
            System.out.println("Error in getting answers");
            return null;
        }
    }
    ArrayList<String> getImagePaths() {
        ArrayList<String> image_path_list = new ArrayList<>();
        Document question_xml = getFile();
        if (question_xml == null) {
            new Errors().showGenericError("Error");
            return null;
        }
        try {
            NodeList imagePaths = question_xml.getElementsByTagName("image");
            if (imagePaths == null || imagePaths.getLength() == 0) {
                return null;
            }
            for (int i = 0; i < imagePaths.getLength(); i++) {
                Node imagePathNode = imagePaths.item(i);
                String imagePath = ((Element)imagePathNode).getTextContent().strip();
                image_path_list.add(imagePath);
            }
            return image_path_list;

        }
        catch (Exception e) {
            System.out.println("Error in getting iamges");
            return null;
        }
    }
}
