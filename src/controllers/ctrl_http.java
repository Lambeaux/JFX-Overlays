package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.util.ServiceEndpoint;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicNameValuePair;
import javax.json.JsonObject;
import java.util.Base64;

/**
 * Created by Lambeaux on 9/9/2015.
 * Controller class for our HTTP testing dialogue.
 *
 */
public class ctrl_http
{
    // FXML CONTROL VARIABLES:
    @FXML
    private RadioButton rdoPost;
    @FXML
    private TextArea txtResponse;
    @FXML
    private RadioButton rdoGet;
    @FXML
    private TextField txtHeaderValue;
    @FXML
    private ListView<NameValuePair> lstHeaders;
    @FXML
    private Button btnRemoveHeader;
    @FXML
    private TextField txtHeaderName;
    @FXML
    private TextArea txtRequestUrl;
    @FXML
    private TextArea txtPostBody;
    @FXML
    private Button btnAddHeader;
    @FXML
    private Button btnSend;

    //  STATE VARIABLES:

    private ToggleGroup radioGroup;
    private ServiceEndpoint endpoint;

    //  PRIVATE FUNCTIONS:

    private void DefineHandlers(ServiceEndpoint ep) throws Exception
    {
        ep.AddOnHttpComplete(new Runnable()
        {
            @Override
            public void run()
            {
                JsonObject JObject = endpoint.GetJsonData();
                StatusLine statusLine = endpoint.GetLastResponseStatus();

                String data = "CODE: " + Integer.toString(statusLine.getStatusCode()) + "\r\n" +
                        "REASON: " + statusLine.getReasonPhrase() + "\r\n\r\n" + "JSON: " + "\r\n\r\n" +
                        JObject.toString();

                txtResponse.clear();
                txtResponse.setText(data);
            }
        });
    }

    //  PUBLIC FUNCTIONS:

    public void Initialize() throws Exception
    {
        lstHeaders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        endpoint = new ServiceEndpoint("");

        radioGroup = new ToggleGroup();
        rdoGet.setToggleGroup(radioGroup);
        rdoPost.setToggleGroup(radioGroup);

        rdoGet.fire();

        //  Bearer AAAAAAAAAAAAAAAAAAAAAKjOhQAAAAAA8C%2B8%2FDqSvCYaAomKR4jx%2FExn0Ug%3DV4hr8zBB5G4ozNjEKNgfHUZTL0y43btxPEyW7odGaFlQGi6clq

        /*String encodedConsumer = "Basic " + Base64.getEncoder().encodeToString(
                "7Rmad3PQdKA7JUxasZlWmqmHU:zFHrnyl8GqpM6YGvxhoiGjWV7g3W7Fg4h9a1tdaXK0JNsjwInP"
                .getBytes());

        lstHeaders.getItems().add(new BasicNameValuePair("Authorization", encodedConsumer));
        lstHeaders.getItems().add(new BasicNameValuePair("Content-Type",
                "application/x-www-form-urlencoded;charset=UTF-8"));*/
    }


    //  EVENT CALLBACKS:

    @FXML
    void rdoGet_Clicked(Event event)
    {
        txtPostBody.setDisable(true);
    }

    @FXML
    void rdoPost_Clicked(Event event)
    {
        txtPostBody.setDisable(false);
    }

    @FXML
    void btnAddHeader_Clicked(Event event)
    {
        if (!txtHeaderName.getText().isEmpty() &&
                !txtHeaderValue.getText().isEmpty())
        {
            lstHeaders.getItems().add(new BasicNameValuePair(
                    txtHeaderName.getText(), txtHeaderValue.getText()));
        }

        lstHeaders.getSelectionModel().select(0);

        txtHeaderName.clear();
        txtHeaderValue.clear();
    }


    @FXML
    void btnRemoveHeader_Clicked(Event event)
    {
        int index = lstHeaders.getSelectionModel().getSelectedIndex();
        lstHeaders.getItems().remove(index);
    }


    @FXML
    void btnSend_Clicked(Event event) throws Exception
    {
        // SETUP:
        if (!txtRequestUrl.getText().isEmpty())
        {
            // URL ADDRESS
            endpoint = new ServiceEndpoint(txtRequestUrl.getText());

            // CALLBACKS
            this.DefineHandlers(endpoint);

            // HEADERS
            endpoint.GetHeadersArray().clear();
            for (NameValuePair p : lstHeaders.getItems())
                    endpoint.GetHeadersArray().add(p);

            // HTTP POST:
            if (rdoPost.isSelected() && !txtPostBody.getText().isEmpty())
                endpoint.UseHttpPost(txtPostBody.getText());

            // INVOKE:
            endpoint.RefreshData();
        }
    }
}
