package objects.data;

import javafx.scene.paint.Color;

/**
 * Created by Lambeaux on 8/30/2015.
 * Stores a copy of the current football game status in static memory.
 */
public class GameState
{
    public static String[] DENOM_FOUR_LABELS = {
            "1st", "2nd", "3rd", "4th"
    };

    public static String $OpponentName = "Horizon";
    public static String $OpponentShort = "HHS";
    public static Color $OpponentColorPrimary = Color.YELLOW;
    public static Color $OpponentColorSecondary = Color.DARKGREEN;

    public static String $Score_Pinnacle = "0";
    public static String $Score_Opponent = "0";
    public static Integer $Score_Quarter_Index = 0;

    public static Integer $Play_Down_Index = 0;
    public static String $Play_Yards = "10";

    public static String MapStrings(int index)
    {
        switch (index)
        {
            case 0:
                return $OpponentName;
            case 1:
                return $OpponentShort;
            case 2:
                return $Score_Pinnacle;
            case 3:
                return $Score_Opponent;
            case 4:
                return DENOM_FOUR_LABELS[$Score_Quarter_Index];
            case 5:
                return DENOM_FOUR_LABELS[$Play_Down_Index];
            case 6:
                return $Play_Yards;
            default:
                return "ERROR";
        }
    }


}
