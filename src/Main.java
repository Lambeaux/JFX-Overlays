import controllers.ctrl_base;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import objects.MediaVideoElement;
import objects.managers.CanvasManager;


public class Main extends Application
{

    private void LoadGui(Stage sampleStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent gui = fxmlLoader.load(
                getClass().getResource("fxml/gui_base.fxml").openStream());

        ctrl_base baseController = fxmlLoader.getController();
        baseController.Initialize();

        sampleStage.setTitle("Canvas Controller");
        sampleStage.setScene(new Scene(gui, 985, 744));
        sampleStage.show();
    }

    private void LoadTestGui(Stage testingStage) throws Exception
    {
        Group root = new Group();

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load("http://t.co/UHCMplzOfN");

        MediaVideoElement video = new MediaVideoElement("sports", "http://t.co/UHCMplzOfN");
        root.getChildren().add(webView);
        //video.GetPlayer().play();

        testingStage.setTitle("Testing Canvas");
        testingStage.setScene(new Scene(root, 985, 744));
        testingStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        LoadGui(primaryStage);
        //LoadTestGui(primaryStage);
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
