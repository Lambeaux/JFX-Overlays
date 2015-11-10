package controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.MediaAbstractElement;
import objects.MediaTransitionElement;
import objects.data.ActorEntity;
import objects.data.ActorTweet;
import objects.data.GameState;
import objects.managers.CanvasManager;
import objects.managers.MediaManager;
import objects.managers.TwitterManager;
import objects.util.ContentLoader;

import java.net.URL;


public class ctrl_base
{
    // Injected GUI control variables:
    //----------------------------------------------

    @FXML
    private TreeView<MediaAbstractElement> lstTreeViewMaster;

    //region SEQUENCE CONTROL Variables
    @FXML
    private Button btnCue;
    @FXML
    private Button btnPush;
    @FXML
    private Button btnRoll;
    @FXML
    private Button btnAbort;

    @FXML
    private ListView<MediaTransitionElement> lstTransitions;
    @FXML
    private ListView<ActorEntity> lstActors;

    //endregion


    //region GAME STATE Variables

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<String> ddbDown;
    @FXML
    private TextField txtYards;

    //region OPPONANT
    @FXML
    private TextField txtOpponantName;
    @FXML
    private TextField txtOpponantShort;
    @FXML
    private ColorPicker cpOpponPrimary;
    @FXML
    private ColorPicker cpOpponSecondary;
    //endregion

    //region SCORE
    @FXML
    private Tab tbScore;
    @FXML
    private TextField txtScorePinn;
    @FXML
    private TextField txtScoreOpponent;
    @FXML
    private ChoiceBox<String> ddbQuarter;
    //endregion

    //endregion


    //region TWITTER Variables
    @FXML
    private Button btnTweetPull;
    @FXML
    private Button btnTweetTableClear;
    @FXML
    private Button btnTweetGrab;
    @FXML
    private Button btnTweetGrabbedClear;
    @FXML
    private TextArea txtTweetPreview;

    @FXML
    private ListView<ActorTweet> lstTweets;
    //endregion


    // Primary state variables:
    //----------------------------------------------
    private CanvasManager ApplicationCanvas;
    private MediaManager ApplicationMedia;
    private TwitterManager ApplicationTwitter;

    public void Initialize() throws Exception
    {
        ApplicationCanvas = CanvasManager.GetInstance();
        ApplicationMedia = new MediaManager(
                lstTreeViewMaster, lstTransitions, ApplicationCanvas);

        ApplicationTwitter = new TwitterManager(lstTweets);

        for (String str : GameState.DENOM_FOUR_LABELS)
        {
            ddbQuarter.getItems().add(str);
            ddbDown.getItems().add(str);
        }

        MediaAbstractElement.SetCanvasManager(ApplicationCanvas);
        ContentLoader.LoadContent(
                ApplicationMedia, ApplicationCanvas, ApplicationTwitter);
        this.Cue_All();

    }

    public void Cue_All() throws Exception
    {
        TreeItem<MediaAbstractElement> root = lstTreeViewMaster.getRoot();
        for (TreeItem<MediaAbstractElement> folder : root.getChildren())
        {
            for (TreeItem<MediaAbstractElement> element : folder.getChildren())
            {
                lstTreeViewMaster.getSelectionModel().select(element);
                ApplicationMedia.Cue();
            }
        }
    }


    // Controller methods:

    @FXML
    void tabChanged(Event event)
    {
        txtOpponantName.setText(GameState.$OpponentName);
        txtOpponantShort.setText(GameState.$OpponentShort);
        cpOpponPrimary.setValue(GameState.$OpponentColorPrimary);
        cpOpponSecondary.setValue(GameState.$OpponentColorSecondary);

        txtScorePinn.setText(GameState.$Score_Pinnacle);
        txtScoreOpponent.setText(GameState.$Score_Opponent);

        ddbQuarter.getSelectionModel().select(GameState.$Score_Quarter_Index);
        ddbDown.getSelectionModel().select(GameState.$Play_Down_Index);
        txtYards.setText(GameState.$Play_Yards);
    }

    @FXML
    void Sequence_Cue(Event event)
    {
        ApplicationMedia.Cue();
    }

    @FXML
    void Sequence_Halt(Event event) { ApplicationMedia.Halt(); }

    @FXML
    void Sequence_Abort(Event event)
    {
        ApplicationCanvas.KillAllGraphics();
    }

    @FXML
    void GameState_UpdateAll(Event event)
    {
        GameState.$OpponentName = txtOpponantName.getText();
        GameState.$OpponentShort = txtOpponantShort.getText();
        GameState.$OpponentColorPrimary = cpOpponPrimary.getValue();
        GameState.$OpponentColorSecondary = cpOpponSecondary.getValue();

        GameState.$Score_Pinnacle = txtScorePinn.getText();
        GameState.$Score_Opponent = txtScoreOpponent.getText();

        GameState.$Score_Quarter_Index = ddbQuarter.getSelectionModel().getSelectedIndex();
        GameState.$Play_Down_Index = ddbDown.getSelectionModel().getSelectedIndex();
        GameState.$Play_Yards = txtYards.getText();

        ApplicationMedia.UpdateAll();
    }

    @FXML
    void Tweet_Grab(Event event)
    {

    }

    @FXML
    void Tweet_ClearGrabbed(Event event)
    {

    }

    @FXML
    void Tweet_ClearTable(Event event)
    {
        lstTweets.getItems().clear();
    }

    @FXML
    void Tweet_Pull(Event event) throws Exception
    {
        ApplicationTwitter.PullTweets();
    }

    @FXML
    void lstTweets_Clicked(Event event)
    {
        if (!lstTweets.getItems().isEmpty())
        {
            txtTweetPreview.clear();
            txtTweetPreview.setText(
                    lstTweets.getSelectionModel().getSelectedItem().GetTweetBody());
        }
    }

    @FXML
    void Canvas_FullScreen(Event event)
    {
        ApplicationCanvas.ToggleFullScreen();
    }

    @FXML
    void Canvas_CueAll(Event event) throws Exception
    {
        this.Cue_All();
    }


    @FXML
    void Services_HttpBuilder(Event event) throws Exception
    {
        Stage builderStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent gui = fxmlLoader.load(
                getClass().getResource("../fxml/dialog_http.fxml").openStream());

        ctrl_http builderController = fxmlLoader.getController();
        builderController.Initialize();

        builderStage.setTitle("HTTP Builder");
        builderStage.setScene(new Scene(gui, 830, 400));
        builderStage.show();
    }
}
