package objects;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Created by Lambeaux on 8/29/2015.
 * Contains and organizes the images, transitions, and text fields of an overlay graphic.
 *
 */
public class MediaGraphicElement extends MediaAbstractElement
{
    // Updated Field Storage
    private ArrayList<FxTextWrapper> $TextFields;

    // State Variables
    private Group $Graphics;
    private boolean $IsLive;
    private Transition $MasterTransition;

    // Accessor Methods
    public Node GetNode() { return $Graphics; }
    public Group GetGraphicsGroup() { return $Graphics; }
    public Boolean isCurrentlyLive() { return $IsLive; }

    public MediaGraphicElement(String elementName, Group imageGraphics, Transition effect)
    {
        super(elementName);
        $Graphics = imageGraphics;
        $MasterTransition = effect;

        $MasterTransition.setCycleCount(1);
        $MasterTransition.setAutoReverse(false);

        $IsLive = false;
        $TextFields = new ArrayList<>();
    }

    public void AddUpdatableTextField(FxTextWrapper textField)
    {
        $TextFields.add(textField);
    }

    public void Update()
    {
        for (FxTextWrapper field : $TextFields)
            field.UpdateText();
    }

    public void Play()
    {
        if ($MasterTransition.getStatus() != Animation.Status.RUNNING)
        {
            if ($IsLive)
            {
                $MasterTransition.setRate(-1.0);
            }

            else
            {
                $MasterTransition.setRate(1.0);
            }

            $IsLive = !$IsLive;
            $MasterTransition.play();
        }
    }
}
