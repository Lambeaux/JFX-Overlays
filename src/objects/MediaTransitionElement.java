package objects;

import javafx.util.Duration;

/**
 * Created by Lambeaux on 8/31/2015.
 * A special type of video element that can automatically fire via events to enable
 * smooth transitions of starting and stopping canvas video elements.
 *
 */
public class MediaTransitionElement extends MediaVideoElement
{
    private Duration transitionPoint;

    public MediaTransitionElement(String name, String path)
    {
        super(name, path);

        // Proceed
    }

    public void Play()
    {
        super.Play();

        // Proceed
    }
}
