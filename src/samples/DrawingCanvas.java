package samples;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.*;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Lambeaux on 8/13/2015.
 * Research done for drawing and working with canvas elements.
 *
 */
public class DrawingCanvas
{
    public static Canvas DrawingBoard = new Canvas(1280, 720);
    public static Group Root = new Group();
    public static Text Score_PINN = new Text("0");
    public static Text Score_ENEM = new Text("0");

    public static void ExecuteSample()
    {
        Stage drawingStage = new Stage();
        Scene canvas = new Scene(Root, 1280, 720, new Color(1.0, 0, 1.0, 1.0));

        // Graphical drawing
        Draw(30.0, 30.0, DrawingBoard.getGraphicsContext2D());
        Root.getChildren().add(DrawingBoard);

        // Handling text objects
        Text t_PINN = new Text("PINN");
        Text t_ENEM = new Text("CHAN");

        String fontPath = "file:resources/fonts/OpenSans-Regular.ttf";
        Color fontColor = new Color(0.8, 0.8, 0.8, 1.0);
        int fontSize = 28;

        t_PINN.setFont(Font.loadFont(fontPath, fontSize));
        t_ENEM.setFont(Font.loadFont(fontPath, fontSize));
        Score_PINN.setFont(Font.loadFont(fontPath, fontSize));
        Score_ENEM.setFont(Font.loadFont(fontPath, fontSize));


        t_PINN.setFill(fontColor);
        t_PINN.setX(60.0);
        t_PINN.setY(70.0);
        t_ENEM.setFill(fontColor);
        t_ENEM.setX(240.0);
        t_ENEM.setY(70.0);

        Score_PINN.setFill(fontColor);
        Score_PINN.setX(170.0);
        Score_PINN.setY(70);
        Score_ENEM.setFill(fontColor);
        Score_ENEM.setX(360.0);
        Score_ENEM.setY(70.0);

        Root.getChildren().add(t_PINN);
        Root.getChildren().add(t_ENEM);
        Root.getChildren().add(Score_PINN);
        Root.getChildren().add(Score_ENEM);

        // Config Stage
        drawingStage.setTitle("Media Canvas");
        drawingStage.setScene(canvas);
        drawingStage.show();
    }

    public static void Draw(double x, double y, GraphicsContext spriteBatch)
    {
        // Variables for drawing
        final double corners = 5.0;
        final double length = 400.0;
        final double height = 60.0;
        final double bend = 20.0;
        final double score_width = 60.0;
        final double score_cushion = 2.0;
        final double score_offset_1 = 110.0;
        final double score_offset_2 = 300.0;

        // Gradient data for inner shape
        Stop[] internalStops = new Stop[]
                {
                        new Stop(0, Color.BLACK),
                        new Stop(0.3, new Color(0.2, 0.2, 0.2, 1.0)),
                        new Stop(1.0, Color.BLACK)
                };
        LinearGradient internalGrad = new LinearGradient(
                x + (length / 2.0) , y ,
                x + (length / 2.0) , y + (height) ,
                false, CycleMethod.NO_CYCLE, internalStops);

        // Gradient data for outter shape
        Stop[] externalStops = new Stop[]
                {
                        new Stop(0, new Color(0.7, 0.7, 0.7, 1.0)),
                        new Stop(0.3, new Color(0.5, 0.5, 0.5, 1.0)),
                };
        LinearGradient externalGrad = new LinearGradient(
                x , y + (height / 2.0),
                x + length , y + (height / 2.0),
                false, CycleMethod.NO_CYCLE, externalStops);

        // Configure options
        spriteBatch.setFill(internalGrad);
        spriteBatch.setStroke(externalGrad);
        spriteBatch.setLineWidth(4.0);
        spriteBatch.setLineJoin(StrokeLineJoin.ROUND);
        spriteBatch.setLineCap(StrokeLineCap.ROUND);

        Color enemyColor = Color.LIGHTBLUE;


        // Draw primary polygon
        spriteBatch.fillPolygon(
                new double[]{x, x + length, x + length + bend, x + bend},
                new double[]{y, y, y + height, y + height},
                4);
        spriteBatch.strokePolygon(
                new double[]{x, x + length, x + length + bend, x + bend},
                new double[]{y, y, y + height, y + height},
                4);

        // Draw covering polygon
        final double delta = 12.0;
        spriteBatch.setFill(new Color(0.0, 0.0, 0.0, 0.2));
        spriteBatch.fillPolygon(
                new double[]{x + delta, x + delta + length - 5.0,
                        x + delta + length + (bend / 2) - 5.0, x + delta + (bend / 2)},
                new double[]{y + (height / 2), y + (height / 2), y + height, y + height},
                4);

        spriteBatch.setGlobalBlendMode(BlendMode.OVERLAY);

        // Draw our score area
        spriteBatch.setFill(Color.BLUE);
        spriteBatch.fillPolygon(
                new double[]{x + score_offset_1, x + score_offset_1 + score_width,
                        x + score_offset_1 + score_width + bend, x + score_offset_1 + bend},
                new double[]{y + score_cushion, y + score_cushion,
                        y + height - score_cushion, y + height - score_cushion},
                4);

        // Draw opponent score area
        spriteBatch.setFill(enemyColor);
        spriteBatch.fillPolygon(
                new double[]{x + score_offset_2, x + score_offset_2 + score_width,
                        x + score_offset_2 + score_width + bend, x + score_offset_2 + bend},
                new double[]{y + score_cushion, y + score_cushion,
                        y + height - score_cushion, y + height - score_cushion},
                4);

        spriteBatch.setGlobalBlendMode(BlendMode.SRC_OVER);



    }
}
