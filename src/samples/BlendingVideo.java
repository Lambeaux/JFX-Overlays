package samples;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.MediaVideoElement;

/**
 * Created by Lambeaux on 8/13/2015.
 * Research done for coding overlay video elements.
 *
 */
public class BlendingVideo
{
    private static final String MEDIA_ROOT = "file:/C:/Users/Lambeaux/Desktop/TestVids/";

    public static void ExecuteSample() throws Exception
    {
        Stage drawingStage = new Stage();
        Group root = new Group();

        Scene canvas = new Scene(root, 1280, 720, new Color(1.0, 0, 1.0, 1.0));
        String path = MEDIA_ROOT + "s1.mp4";

        MediaVideoElement e1 = new MediaVideoElement("", path);
        ((Group) canvas.getRoot()).getChildren().add(e1.GetView());
        e1.GetPlayer().setStartTime(new Duration(6000));
        e1.GetPlayer().play();

        MediaVideoElement e2 = new MediaVideoElement("", path);
        ((Group) canvas.getRoot()).getChildren().add(e2.GetView());
        e2.GetPlayer().play();

        drawingStage.setTitle("Media Canvas");
        drawingStage.setScene(canvas);
        drawingStage.show();
    }
}
