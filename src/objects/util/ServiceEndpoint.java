package objects.util;

import javax.json.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventType;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by Lambeaux on 8/13/2015.
 * Basic HTTP and JSON abstraction for working with RESTful data sets. Implements
 * my custom DropStack for use as temporary data history so bad API calls do not
 * impact the application with null data.
 *
 */
public class ServiceEndpoint
{
    private static final int CONST_MAX_HISTORY_COUNT = 4;

    private DropStack<JsonObject> $DataStack;
    private ArrayList<NameValuePair> $RequestHeaders;
    private HttpEntity $DataBody;

    private String $DataRequestString;
    private Thread $DataWorker;

    private HttpClient $client;
    private StatusLine $lastResponseStatus;

    private boolean $UseHttpPost;
    private ArrayList<Runnable> $OnHttpComplete;

    private void DataRefreshStarter() throws Exception
    {
        $DataWorker = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    DataRefreshWorker();
                }

                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });

        $DataWorker.start();
    }

    private void DataRefreshWorker() throws Exception
    {
        $client = HttpClientBuilder.create().build();

        try
        {
            // The RESTful HTTP client library will process the request
            HttpUriRequest request;

            if ($UseHttpPost)
                request = new HttpPost($DataRequestString);

            else
                request = new HttpGet($DataRequestString);

            // The request may need HTTP headers
            if (!$RequestHeaders.isEmpty())
                for (NameValuePair p : $RequestHeaders)
                        request.addHeader(p.getName(), p.getValue());

            if ($UseHttpPost && $DataBody != null)
                ((HttpPost)request).setEntity($DataBody);

            // Retrieve the response and record the status for later review
            HttpResponse response = $client.execute(request);
            $lastResponseStatus = response.getStatusLine();

            // The JSON library will take over parsing of the returned data
            JsonReader JReader = Json.createReader(
                    new InputStreamReader(response.getEntity().getContent()));
            JsonObject JObject = JReader.readObject();

            // Push the refreshed JSON data into the queue
            $DataStack.Push(JObject);

            // Call the subscribed runnable
            if (!$OnHttpComplete.isEmpty())
            {
                for (Runnable r : $OnHttpComplete)
                {
                    Thread followUpAction = new Thread(r);
                    followUpAction.start();
                }
            }
        }

        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    //! Constructor without any authentication
    public ServiceEndpoint(String serviceRequestUrl) throws Exception
    {
        $client = HttpClientBuilder.create().build();
        $RequestHeaders = new ArrayList<>();
        $lastResponseStatus = null;

        $DataStack = new DropStack<>(CONST_MAX_HISTORY_COUNT);
        $DataRequestString = serviceRequestUrl;
        $DataWorker = null;

        $UseHttpPost = false;
        $OnHttpComplete = new ArrayList<>();
    }

    //! Constructor that implements basic HTTP authentication
    public ServiceEndpoint(String serviceRequestUrl, String userName,
                           String password) throws Exception
    {
        URL serviceAddress = new URL(serviceRequestUrl);

        CredentialsProvider creds = new BasicCredentialsProvider();
        creds.setCredentials(
                new AuthScope(serviceAddress.getHost(), 80, null),
                new UsernamePasswordCredentials(userName, password));

        $client = HttpClientBuilder.create().setDefaultCredentialsProvider(creds).build();
        $RequestHeaders = new ArrayList<>();
        $lastResponseStatus = null;

        $DataStack = new DropStack<>(CONST_MAX_HISTORY_COUNT);
        $DataRequestString = serviceRequestUrl;
        $DataWorker = null;

        $UseHttpPost = false;
        $OnHttpComplete = new ArrayList<>();
    }

    //! Get the JSON object that was retrieved by the last request.
    public JsonObject GetJsonData()
    {
        return $DataStack.Peek();
    }

    //! Get the response status of the last request.
    public StatusLine GetLastResponseStatus()
    {
        return $lastResponseStatus;
    }

    //! Get the ArrayList of header fields to be used in each request.
    public ArrayList<NameValuePair> GetHeadersArray()
    {
        return $RequestHeaders;
    }

    //! Sets the URL for the request.
    public void SetDataRequestUrl(String urlString)
    {
        $DataRequestString = urlString;
    }

    //! Sets if the request should use GET or POST.
    public void UseHttpPost(String body) throws Exception
    {
        $UseHttpPost = true;
        $DataBody = new ByteArrayEntity(body.getBytes("UTF-8"));
    }
    public void UseHttpGet()
    {
        $UseHttpPost = false;
        $DataBody = null;
    }

    //! Sets the Runnable to be executed when a request completes.
    public void AddOnHttpComplete(Runnable value)
    {
        $OnHttpComplete.add(value);
    }


    //! Revert the current JSON object to the result of the last request.
    /*! Use to prevent data corruption when a request fails.*/
    public void RevertToLastDataSet()
    {
        $DataStack.Pop();
    }

    //! Start the RESTful HTTP request invocation in another thread.
    public void RefreshData() throws Exception
    {
        if ($DataWorker == null)
            DataRefreshStarter();

        else if (!$DataWorker.isAlive())
            DataRefreshStarter();
    }
}
