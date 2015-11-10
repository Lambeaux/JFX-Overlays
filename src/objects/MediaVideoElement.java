package objects;

import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Created by Lambeaux on 8/12/2015.
 * Simple storage for a single piece of playable media.
 *
 */
public class MediaVideoElement extends MediaAbstractElement
{
    private MediaPlayer $MPlayer;
    private MediaView $MView;

    public Node GetNode() { return $MView; }
    public MediaPlayer GetPlayer() { return $MPlayer; }
    public MediaView GetView() { return $MView; }

    public MediaVideoElement(String elementName, String path)
    {
        super(elementName);
        Media mediaData = new Media(path);

        $MPlayer = new MediaPlayer(mediaData);
        $MView = new MediaView($MPlayer);

        $MPlayer.setAutoPlay(false);
        $MView.setOpacity(0.0);

        $MPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                Halt();
            }
        });

        //$MPlayer.setOnEndOfMedia(null);
        //$MView.setBlendMode(BlendMode.ADD);

    }

    public void Play()
    {
        $MPlayer.stop();
        $MPlayer.play();
        $MView.setOpacity(1.0);
    }

    public void Halt()
    {
        $MPlayer.stop();
        $MView.setOpacity(0.0);
    }
}
