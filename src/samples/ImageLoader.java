package samples;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Lambeaux on 8/13/2015.
 * Working with static images, animations, and transitions.
 *
 */
public class ImageLoader
{
    private static String imagesPath =
            "file:/D:/Code Repository/Java/Live_Template/resources/images/";

    public static void ExecuteSample()
    {
        Stage drawingStage = new Stage();
        Group root = new Group();

        Scene canvas = new Scene(root, 1280, 720, new Color(1.0, 0, 1.0, 1.0));
        Image testImage = new Image(imagesPath + "lady.png", true);

        ImageView testView = new ImageView(testImage);
        root.getChildren().add(testView);



        drawingStage.setTitle("Media Canvas");
        drawingStage.setScene(canvas);
        drawingStage.show();
    }
}
