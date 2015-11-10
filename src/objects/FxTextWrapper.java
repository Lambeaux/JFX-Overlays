package objects;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import objects.data.GameState;
import objects.util.ContentLoader;

/**
 * Created by Lambeaux on 8/30/2015.
 * Wrap the text instances of media elements to keep data and format synchronized.
 * Without this class, we could not update our text on the canvas.
 * We also use this to organize text themes for cleaner code.
 *
 */
public class FxTextWrapper
{
    private static String FONT_PATH_START =
            ContentLoader.GetResourcesPathStart() + "fonts/";

    private String $FollowSymbols;
    private Text $TextReference;
    private boolean $IsEnemyThemed;
    private int $DataIndex;

    public Text GetTextReference() { return $TextReference; }

    public static Text Coloring_Pinn(Text input)
    {
        input.setFill(Color.NAVY);
        input.setStroke(Color.WHITE);
        return input;
    }

    public static Text Coloring_Oppon(Text input)
    {
        input.setFill(GameState.$OpponentColorPrimary);
        input.setStroke(GameState.$OpponentColorSecondary);
        return input;
    }

    public static Text Theme_SmallDrop(Text input)
    {
        Font ScoreFont = Font.loadFont(FONT_PATH_START + "OpenSans-Regular.ttf", 24);

        input.setFont(ScoreFont);
        input.setFill(Color.WHITE);
        return input;
    }

    public static Text Theme_SmallScore(Text input)
    {
        Font ScoreFont = Font.loadFont(FONT_PATH_START + "OpenSans-Bold.ttf", 30);

        input.setFont(ScoreFont);
        input.setStrokeWidth(1.5);
        return input;
    }

    public static Text Theme_BigScore(Text input)
    {
        Font ScoreFont = Font.loadFont(FONT_PATH_START + "XBall.ttf", 40);

        input.setFont(ScoreFont);
        input.setFill(Color.WHITE);
        return input;
    }

    public FxTextWrapper(String followSymbols, int dataIndex, boolean useEnemyColors)
    {
        $FollowSymbols = followSymbols;

        $TextReference = new Text();
        $IsEnemyThemed = useEnemyColors;
        $DataIndex = dataIndex;

        this.UpdateText();
    }

    public void UpdateText()
    {
        $TextReference.setText(GameState.MapStrings($DataIndex) + $FollowSymbols);

        if ($IsEnemyThemed)
        {
            $TextReference = Coloring_Oppon($TextReference);
        }
    }

}
