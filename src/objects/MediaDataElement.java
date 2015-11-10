package objects;

import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.text.Text;
import objects.data.ActorTweet;
import objects.managers.TwitterManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lambeaux on 9/11/2015.
 * Synchronize data and prevent redundant graphic elements by using a single
 * image template where text fields can be updated and changed. All MediaDataElements
 * are in some form or another dependent on a type of ActorEntity.
 *
 */
public class MediaDataElement extends MediaGraphicElement
{
    private static int SPLIT_INDEX = 82;

    public TwitterManager $TweetManager;
    public HashMap<String, Text> $TextMap;

    public MediaDataElement(
            String elementName, Group imageGraphics, Transition effect, TwitterManager tweets)
    {
        super(elementName, imageGraphics, effect);
        $TextMap = new HashMap<>();
        $TweetManager = tweets;
    }

    public void Play()
    {
        // Do data updating
        ActorTweet ctweet = $TweetManager.GetActiveTweet();

        if (ctweet != null)
        {
            String tweetBody = ctweet.GetTweetBody();

            if (tweetBody.length() > SPLIT_INDEX - 10)
            {
                char[] chars = tweetBody.toCharArray();

                for (int i = SPLIT_INDEX - 20; i < tweetBody.length(); i++)
                {
                    if (chars[i] == ' ')
                    {
                        tweetBody =
                        tweetBody.substring(0, i) + "\n" +
                        tweetBody.substring(i + 1);
                        break;
                    }
                }
            }

            $TextMap.get("handle").setText(ctweet.GetUserScreenName());
            $TextMap.get("tweet").setText(tweetBody);
        }

        super.Play();
    }
}