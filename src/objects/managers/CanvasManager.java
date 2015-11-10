package objects.managers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Created by Lambeaux on 8/29/2015.
 * SINGLETON --
 * Responsible for managing the IO for the canvas in terms of layers,
 * groups, and other executable media.
 *
 */
public class CanvasManager
{
    public enum CanvasResolution { R1080p, R720p }
    public enum CanvasLayer
    {
        Overlay(0), Video(1), Data(2), Transition(3);

        private final int value;

        CanvasLayer(int value)
        {
            this.value = value;
        }

        public int AsInteger()
        {
            return value;
        }
    }

    private static CanvasManager instance = null;

    private static int pAspectWidth = 1920;
    private static int pAspectHeight = 1080;
    private static int LAYER_COUNT = 4;

    private Stage $CanvasStage;
    private Scene $CanvasScene;
    private Group $CanvasRoot;

    private ArrayList<Group> $Layers;

    public static CanvasManager GetInstance()
    {
        if (instance == null)
            instance = new CanvasManager();
        return instance;
    }

    protected CanvasManager()
    {
        $CanvasStage = new Stage();
        $CanvasRoot = new Group();

        $Layers = new ArrayList<>();

        int i = 0;
        while (i < LAYER_COUNT)
        {
            $Layers.add(new Group());
            i++;
        }

        for (Group g : $Layers)
            $CanvasRoot.getChildren().add(g);

        $CanvasScene = new Scene($CanvasRoot, pAspectWidth, pAspectHeight);
        $CanvasScene.setFill(Color.MAGENTA);

        $CanvasStage.setAlwaysOnTop(true);
        $CanvasStage.setTitle("Media Canvas");
        $CanvasStage.setScene($CanvasScene);
        $CanvasStage.show();

    }

    public Group GetLayer(CanvasLayer layer)
    {
        int index = layer.AsInteger();
        if (index > 3 || index < 0)
            return null;
        else
            return $Layers.get(index);
    }

    public void CutScene()
    {

    }

    public void ToggleFullScreen()
    {
        $CanvasStage.setFullScreen(
                !$CanvasStage.isFullScreen()
        );
    }

    public void KillAllGraphics()
    {
        for (Group g : $Layers)
            g.getChildren().clear();
    }

}
