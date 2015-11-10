package samples;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.util.ServiceEndpoint;

import javax.json.JsonObject;

/**
 * Created by Lambeaux on 8/13/2015.
 * Research done for coding the overlay API data.
 *
 */
public class OverlayApiData
{
    public static void ExecuteAuthSample() throws Exception
    {
        Stage drawingStage = new Stage();
        Group root = new Group();

        Scene canvas = new Scene(root, 1280, 720, Color.WHITE);

        ServiceEndpoint endpoint = new ServiceEndpoint(
                "http://census.daybreakgames.com/s:lambeaux/get/ps2/character/5428010618043458481");

        while (endpoint.GetJsonData() == null)
            Thread.sleep(500);

        JsonObject obj = endpoint.GetJsonData()
                .getJsonArray("character_list")
                .getJsonObject(0);

        Text api_name = new Text(
                obj.getJsonObject("name").getString("first")
        );

        Text api_certs = new Text(
                obj.getJsonObject("certs").getString("available_points")
        );

        Text api_br = new Text(
                obj.getJsonObject("battle_rank").getString("value")
        );

        String fontPath = "file:resources/fonts/OpenSans-Regular.ttf";

        api_name.setFont(Font.loadFont(fontPath, 40));
        api_certs.setFont(Font.loadFont(fontPath, 40));
        api_br.setFont(Font.loadFont(fontPath, 40));

        api_name.setX(50.0);
        api_name.setY(50.0);
        api_certs.setX(50.0);
        api_certs.setY(100.0);
        api_br.setX(50.0);
        api_br.setY(150.0);

        ((Group) canvas.getRoot()).getChildren().add(api_name);
        ((Group) canvas.getRoot()).getChildren().add(api_certs);
        ((Group) canvas.getRoot()).getChildren().add(api_br);

        drawingStage.setTitle("Media Canvas");
        drawingStage.setScene(canvas);
        drawingStage.show();
    }

    public static void ExecuteSample() throws Exception
    {
        Stage drawingStage = new Stage();
        Group root = new Group();

        Scene canvas = new Scene(root, 1280, 720, Color.WHITE);

        ServiceEndpoint endpoint = new ServiceEndpoint(
                "http://census.daybreakgames.com/s:lambeaux/get/ps2/character/5428010618043458481");

        while (endpoint.GetJsonData() == null)
            Thread.sleep(500);

        JsonObject obj = endpoint.GetJsonData()
                .getJsonArray("character_list")
                .getJsonObject(0);

        Text api_name = new Text(
                obj.getJsonObject("name").getString("first")
        );

        Text api_certs = new Text(
                obj.getJsonObject("certs").getString("available_points")
        );

        Text api_br = new Text(
                obj.getJsonObject("battle_rank").getString("value")
        );

        String fontPath = "file:resources/fonts/OpenSans-Regular.ttf";

        api_name.setFont(Font.loadFont(fontPath, 40));
        api_certs.setFont(Font.loadFont(fontPath, 40));
        api_br.setFont(Font.loadFont(fontPath, 40));

        api_name.setX(50.0);
        api_name.setY(50.0);
        api_certs.setX(50.0);
        api_certs.setY(100.0);
        api_br.setX(50.0);
        api_br.setY(150.0);

        ((Group) canvas.getRoot()).getChildren().add(api_name);
        ((Group) canvas.getRoot()).getChildren().add(api_certs);
        ((Group) canvas.getRoot()).getChildren().add(api_br);

        drawingStage.setTitle("Media Canvas");
        drawingStage.setScene(canvas);
        drawingStage.show();
    }
}
