package objects;

import javafx.scene.Node;
import objects.managers.CanvasManager;
import objects.managers.CanvasManager.CanvasLayer;

/**
 * Created by Lambeaux on 8/29/2015.
 * Abstract base wrapper for all elements in the media pool.
 *
 */
public class MediaAbstractElement
{
    protected static CanvasManager $Container;
    public static void SetCanvasManager(CanvasManager canvas)
    {
        $Container = canvas;
    }

    protected String $Name;
    protected CanvasLayer $Layer;
    protected int $Index;

    public Node GetNode() { return null; }
    public void Update(){}
    public void Play(){}
    public void Halt(){}

    public MediaAbstractElement(String elementName)
    {
        $Name = elementName;
    }

    @Override
    public String toString()
    {
        return $Name;
    }
}
