package objects.data;

import javax.json.JsonObject;

/**
 * Created by Lambeaux on 9/10/2015.
 * Data model for displaying Tweets on the canvas.
 *
 */
public class ActorTweet extends ActorEntity
{
    private Long   $TweetId;
    private String $TweetBody;
    private String $TweetCreationTime;
    private String $TweetUserFormalName;
    private String $TweetUserScreenName;
    private String $TweetUserLocation;


    public String GetTweetBody() { return $TweetBody; }
    public String GetTweetCreationTime() { return $TweetCreationTime; }
    public String GetUserFormalName() { return $TweetUserFormalName; }
    public String GetUserScreenName() { return $TweetUserScreenName; }
    public String GetUserLocation() { return $TweetUserLocation; }


    //! Construction of a Tweet Actor requires the JsonObject that contains it.
    public ActorTweet(JsonObject statusObject)
    {
        $TweetCreationTime = statusObject.getString("created_at");
        $TweetId = statusObject.getJsonNumber("id").longValueExact();
        $TweetBody = statusObject.getString("text");

        JsonObject user = statusObject.getJsonObject("user");

        $TweetUserFormalName = user.getString("name");
        $TweetUserScreenName = "@" + user.getString("screen_name");
        $TweetUserLocation = user.getString("location");
    }

    //! Compare two tweets for equality.
    public boolean isEqual(ActorTweet tweet)
    {
        return this.$TweetId.equals(tweet.$TweetId);
    }

    //! Get concatenated string.
    public String toString()
    {
        return $TweetUserScreenName + " : " + $TweetBody;
    }
}
