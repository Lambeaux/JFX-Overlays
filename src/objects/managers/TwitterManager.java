package objects.managers;

import javafx.scene.control.ListView;
import objects.data.ActorEntity;
import objects.data.ActorTweet;
import objects.util.ServiceEndpoint;
import org.apache.http.message.BasicNameValuePair;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;

/**
 * Created by Lambeaux on 9/10/2015.
 * Singleton structure that handles all data management and service/API calls for the Twitter Platform.
 * Defines the update process and stores the next Tweet to be displayed.
 *
 */
public class TwitterManager
{
    //  TODO:
    //  Store reference to the table here.

    private static final String DEFAULT_BEARER_TOKEN =
            "Bearer AAAAAAAAAAAAAAAAAAAAAKjOhQAAAAAA8C%2B8%2FDqSvCYaAomKR4jx%2FExn0Ug%3DV4hr8zBB5G4ozNjEKNgfHUZTL0y43btxPEyW7odGaFlQGi6clq";
    private static final String DEFAULT_REQUEST_URL =
            "https://api.twitter.com/1.1/search/tweets.json?q=%40GoBigBlue_PHS&result_type=recent";

    private ListView<ActorTweet> $Tweets;

    private ServiceEndpoint $TwitterApi;
    private boolean $AuthenticatedBearer;

    public ListView<ActorTweet> GetTweetList() { return $Tweets; }

    private void ProcessNewBearerToken()
    {

    }

    private void ProcessResponse()
    {
        if ($TwitterApi.GetLastResponseStatus().getStatusCode() == 200)
            ProcessJsonReturn($TwitterApi.GetJsonData());
    }

    private void ProcessJsonReturn(JsonObject jsonObject)
    {
        ArrayList<ActorTweet> entities = new ArrayList<>();
        JsonArray pulledArray = jsonObject.getJsonArray("statuses");

        for (int i = 0; i < pulledArray.size(); i++)
        {
            entities.add(new ActorTweet(pulledArray.getJsonObject(i)));
        }

        for (ActorTweet t : entities)
        {
            boolean alreadyInList = false;

            for (ActorTweet u : $Tweets.getItems())
            {
                if (t.isEqual(u))
                {
                    alreadyInList = true;
                    break;
                }
            }

            if (!alreadyInList)
                $Tweets.getItems().add(t);
        }
    }

    public TwitterManager(ListView<ActorTweet> t) throws Exception
    {
        $Tweets = t;

        $TwitterApi = new ServiceEndpoint(DEFAULT_REQUEST_URL);
        $AuthenticatedBearer = true;

        $TwitterApi.GetHeadersArray().add(
                new BasicNameValuePair("Authorization", DEFAULT_BEARER_TOKEN));

        $TwitterApi.AddOnHttpComplete(new Runnable() {
            @Override
            public void run() {
                ProcessResponse();
            }
        });
    }

    public void PullTweets() throws Exception
    {
        if ($AuthenticatedBearer)
        {
            $TwitterApi.RefreshData();
        }
    }

    public ActorTweet GetActiveTweet()
    {
        return $Tweets.getSelectionModel().getSelectedItem();
    }

}
