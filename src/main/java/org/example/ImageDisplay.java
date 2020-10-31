package org.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageDisplay {
    Stage window;
    Scene sc;
    Image i;
    ImageDisplay(Image i)
    {
        this.i = i;
    }
    Stage displayImage()
    {
        int height = (int)i.getHeight();
        int width = (int)i.getWidth();
        //Image
        ImageView imageView = new ImageView(i);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);

        VBox box = new VBox(0);
        box.setPadding(new Insets(10));

        box.getChildren().addAll(imageView);

        sc = new Scene(box, width+20, height+20);

        window = new Stage();
        window.setTitle("Image");
        window.setScene(sc);

        return window;
    }
}
