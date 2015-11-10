package objects.util;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import objects.FxTextWrapper;
import objects.MediaDataElement;
import objects.MediaGraphicElement;
import objects.MediaVideoElement;
import objects.managers.CanvasManager;
import objects.managers.MediaManager;
import objects.managers.TwitterManager;

import javax.print.attribute.standard.Media;
import java.io.File;

/**
 * Created by Lambeaux on 8/30/2015.
 * Helper class that will manually load our specific content until additional
 * GUI support can be implemented.
 *
 */
public class ContentLoader
{
    //  The original definition:
    //  private static String RESOURCES_PATH_START =
    //        "file:/D:/!Code/Java/Live_Template/resources/";

    private static TwitterManager TweetManagerPlaceholder;

    private static String RESOURCES_PATH_START = SetResourcesPathStart();

    private static String[] SPONSOR_NAMES = {
            "Chapman Ford", "CPK", "Arena SG", "Aquaman", "Grimaldi's",
            "iBoutique", "Jamba Juice", "Airpark", "Red Devil", "DryMax", "Colliers"
    };

    public static String GetResourcesPathStart()
    {
        return RESOURCES_PATH_START;
    }

    private static String SetResourcesPathStart()
    {
        String absolutePath = new File("resources").getAbsolutePath();

        absolutePath = absolutePath.replace('\\', '/');
        absolutePath = absolutePath.concat("/");

        return "file:/" + absolutePath;
    }

    private static Transition GenerateSponsorAnimation(ImageView node)
    {
        TranslateTransition mover = new TranslateTransition(new Duration(1500), node);
        mover.setFromX(0);
        mover.setToX(600);

        return mover;
    }

    public static void LoadContent(MediaManager mediaManager, CanvasManager canvasManager,
                                   TwitterManager tweetManager)
    {
        String f;
        TweetManagerPlaceholder = tweetManager;

        //  LOAD ALL THE SPONSOR PANELS:

        f = GetResourcesPathStart() + "images/Sponsor_Panels/";

        for (int i = 1; i < 12; i ++)
        {
            String path = f + "S-0" + Integer.toString(i) + ".png";
            ImageView panel = new ImageView(new Image(path));
            MediaGraphicElement sponsor =
                    new MediaGraphicElement(SPONSOR_NAMES[i - 1], new Group(panel),
                            GenerateSponsorAnimation(panel));

            mediaManager.Add(sponsor, 6);
            canvasManager.GetLayer(
                    CanvasManager.CanvasLayer.Overlay).getChildren().add(sponsor.GetNode());
        }

        //  LOAD ALL THE VIDEOS and GRAPHICS:

        f = GetResourcesPathStart() + "videos/";

        MediaVideoElement transitionA =
                new MediaVideoElement("Transition A", f + "Transition_A.flv");

        MediaVideoElement transitionB =
                new MediaVideoElement("Transition B", f + "Transition_B.flv");

        MediaVideoElement intro =
                new MediaVideoElement("Main Intro", f + "Intro_Opener.mp4");

        MediaVideoElement chapmanAd =
                new MediaVideoElement("Chapman Ad", f + "CF-814-1-15.mp4");

/*        MediaVideoElement clock =
                new MediaVideoElement("Clock", "http://192.168.1.128/");*/

        mediaManager.Add(chapmanAd, 3);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Video).getChildren().add(chapmanAd.GetNode());

        mediaManager.Add(intro, 3);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Video).getChildren().add(intro.GetNode());

/*        mediaManager.Add(clock, 1);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Video).getChildren().add(clock.GetNode());*/

        mediaManager.Add(transitionA, 3);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Video).getChildren().add(transitionA.GetNode());

        mediaManager.Add(transitionB, 3);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Video).getChildren().add(transitionB.GetNode());

        //  ---------------------------------------------------------------------------------

        MediaGraphicElement twitterOverlay = TweetOverlay();
        mediaManager.Add(twitterOverlay, 2);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Overlay).getChildren().add(twitterOverlay.GetNode());

        MediaGraphicElement playState = SmallScoreDropDown();
        mediaManager.Add(playState, 0);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Overlay).getChildren().add(playState.GetNode());

        MediaGraphicElement smallScore = SmallScoreOverlay();
        mediaManager.Add(smallScore, 0);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Overlay).getChildren().add(smallScore.GetNode());

        MediaGraphicElement largeScore = BigScoreOverlay();
        mediaManager.Add(largeScore, 0);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Overlay).getChildren().add(largeScore.GetNode());

        MediaGraphicElement opener = GameOpener();
        mediaManager.Add(opener, 0);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Overlay).getChildren().add(opener.GetNode());

        MediaGraphicElement basicBio = PlayerBio();
        mediaManager.Add(basicBio, 2);
        canvasManager.GetLayer(
                CanvasManager.CanvasLayer.Overlay).getChildren().add(basicBio.GetNode());

    }

    private static MediaGraphicElement TweetOverlay()
    {
        String f = GetResourcesPathStart() + "images/Pinnacle_Twitter.png";
        ImageView img = new ImageView(new Image(f));
        Rectangle rect = new Rectangle(1100, 120, Color.WHITE);

        Text handle = new Text("@test");
        Text tweet = new Text("This is such an awesome stream!!! Let's do it again sometime!!");

        Group g = new Group(rect, img, handle, tweet);

        img.setScaleX(0.5);
        img.setScaleY(0.5);
        img.setX(100);
        img.setY(600);

        rect.setX(350);
        rect.setY(800);

        handle.setX(500);
        handle.setY(835);
        handle.setFont(new Font(32));

        tweet.setX(500);
        tweet.setY(870);
        tweet.setFont(new Font(24));

        TranslateTransition mover = new TranslateTransition(new Duration(1200), g);
        mover.setFromX(-1800);
        mover.setToX(100);

        MediaDataElement element =
                new MediaDataElement("Twitter Overlay", g, mover, TweetManagerPlaceholder);

        element.$TextMap.put("handle", handle);
        element.$TextMap.put("tweet", tweet);

        return element;
    }

    private static MediaGraphicElement PlayerBio()
    {
        String f = GetResourcesPathStart() + "images/Layers_PlayerBio/";

        Double targetX = 50.0;
        Double targetY = 450.0;

        ImageView bioP_3 =
                new ImageView(new Image(f + "Bio_PinnP-3.png"));
        ImageView bioLow_4 =
                new ImageView(new Image(f + "Bio_LowerBar-4.png"));
        ImageView bioBar_5 =
                new ImageView(new Image(f + "Bio_MainBar-5.png"));

        // Fill updateable text fields here
        Text example1 = new Text();
        Text example2 = new Text();
        //---------------------------------

        Group lowerCluster = new Group(bioLow_4, example1);
        Group topCluster = new Group(bioBar_5, example2);
        Group masterNode = new Group(bioP_3, lowerCluster, topCluster);

        //region TRANSITION FUNCTIONS

        FadeTransition pFader = new FadeTransition(new Duration(100), bioP_3);
        pFader.setFromValue(0.0);
        pFader.setToValue(1.0);
        TranslateTransition pMover = new TranslateTransition(new Duration(900), bioP_3);
        pMover.setFromX(2500);
        pMover.setToX(targetX);
        pMover.setFromY(targetY);
        pMover.setToY(targetY);
        FadeTransition topFader = new FadeTransition(new Duration(100), topCluster);
        topFader.setDelay(new Duration(200));
        topFader.setFromValue(0.0);
        topFader.setToValue(1.0);
        TranslateTransition topMover = new TranslateTransition(new Duration(500), topCluster);
        topMover.setDelay(new Duration(400));
        topMover.setFromX(-2000);
        topMover.setToX(targetX);
        topMover.setFromY(targetY);
        topMover.setToY(targetY);
        ParallelTransition pMaster =
                new ParallelTransition(pFader, pMover, topFader, topMover);

        FadeTransition botFader = new FadeTransition(new Duration(100), lowerCluster);
        botFader.setFromValue(0.0);
        botFader.setToValue(1.0);
        TranslateTransition botMover = new TranslateTransition(new Duration(600), lowerCluster);
        botMover.setFromX(targetX);
        botMover.setToX(targetX);
        botMover.setFromY(targetY - 60);
        botMover.setToY(targetY);
        ParallelTransition botMaster =
                new ParallelTransition(botFader, botMover);

        SequentialTransition masterTransition =
                new SequentialTransition(pMaster, botMaster);

        //endregion

        MediaGraphicElement graphicElement =
                new MediaGraphicElement("Basic Player Bio", masterNode, masterTransition);

        return graphicElement;
    }

    private static MediaGraphicElement GameOpener()
    {
        String f = GetResourcesPathStart() + "images/Layers_Opener/";

        ImageView lower =
                new ImageView(new Image(f + "Opener_Lower-1.png"));
        ImageView top =
                new ImageView(new Image(f + "Opener_Top-2.png"));
        Group masterNode =
                new Group(lower, top);

        FadeTransition lowerFader = new FadeTransition(new Duration(100), lower);
        lowerFader.setFromValue(0.0);
        lowerFader.setToValue(1.0);
        TranslateTransition lowerMover = new TranslateTransition(new Duration(600), lower);
        lowerMover.setFromY(-100.0);
        lowerMover.setToY(0.0);
        ParallelTransition lowerTransition =
                new ParallelTransition(lowerFader, lowerMover);

        FadeTransition topFader = new FadeTransition(new Duration(600), top);
        topFader.setFromValue(0.0);
        topFader.setToValue(1.0);
        TranslateTransition topMover = new TranslateTransition(new Duration(600), top);
        topMover.setFromY(300.0);
        topMover.setToY(0.0);
        ParallelTransition topTransition =
                new ParallelTransition(topMover);

        SequentialTransition masterTransition
                = new SequentialTransition(topTransition, lowerTransition);

        return new MediaGraphicElement("Game Opener", masterNode, masterTransition);
    }

    private static MediaGraphicElement BigScoreOverlay()
    {
        String f = GetResourcesPathStart() + "images/Layers_BigScore/";
        Double targetX = 250.0;
        Double targetY = 800.0;

        ImageView lower =
                new ImageView(new Image(f + "BigScore_Lower-1.png"));
        ImageView top =
                new ImageView(new Image(f + "BigScore_Top-2.png"));

        FxTextWrapper pinnScoreWrapper = new FxTextWrapper("", 2, false);
        FxTextWrapper oppoScoreWrapper = new FxTextWrapper("", 3, false);
        FxTextWrapper quarterWrapper = new FxTextWrapper(" Quarter", 4, false);
        Text pinnScore = FxTextWrapper.Theme_BigScore(pinnScoreWrapper.GetTextReference());
        Text oppoScore = FxTextWrapper.Theme_BigScore(oppoScoreWrapper.GetTextReference());
        Text quarter = FxTextWrapper.Theme_BigScore(quarterWrapper.GetTextReference());

        lower.setX(targetX);
        top.setX(targetX);
        pinnScore.setX(500);
        pinnScore.setY(130);
        oppoScore.setX(1250);
        oppoScore.setY(130);
        quarter.setX(  800);
        quarter.setY(  130);

        Group lowerNode =
                new Group(lower, pinnScore, oppoScore, quarter);
        Group masterNode =
                new Group(lowerNode, top);


        FadeTransition lowerFader = new FadeTransition(new Duration(50), lowerNode);
        lowerFader.setFromValue(0.0);
        lowerFader.setToValue(1.0);
        TranslateTransition lowerMover = new TranslateTransition(new Duration(600), lowerNode);
        lowerMover.setFromY(targetY - 80.0);
        lowerMover.setToY(targetY);
        ParallelTransition lowerTransition =
                new ParallelTransition(lowerMover, lowerFader);

        FadeTransition topFader = new FadeTransition(new Duration(600), top);
        topFader.setFromValue(0.0);
        topFader.setToValue(1.0);
        TranslateTransition topMover = new TranslateTransition(new Duration(600), top);
        topMover.setFromY(targetY + 400.0);
        topMover.setToY(targetY);
        ParallelTransition topTransition =
                new ParallelTransition(topMover);

        SequentialTransition masterTransition
                = new SequentialTransition(topTransition, lowerTransition);

        MediaGraphicElement graphicElement =
                new MediaGraphicElement("Large Scoreboard", masterNode, masterTransition);

        graphicElement.AddUpdatableTextField(pinnScoreWrapper);
        graphicElement.AddUpdatableTextField(oppoScoreWrapper);
        graphicElement.AddUpdatableTextField(quarterWrapper);

        return graphicElement;
    }

    private static MediaGraphicElement SmallScoreDropDown()
    {
        String f = GetResourcesPathStart() + "images/Layers_SmallScore/";
        Double targetX = 25.0;
        Double targetY = 50.0;

        ImageView background =
                new ImageView(new Image(f + "SmallScore_Status-4.png"));

        FxTextWrapper downWrapper = new FxTextWrapper("   & ", 5, false);
        FxTextWrapper yardsWrapper = new FxTextWrapper("", 6, false);
        Text downs = FxTextWrapper.Theme_SmallDrop(downWrapper.GetTextReference());
        Text yards = FxTextWrapper.Theme_SmallDrop(yardsWrapper.GetTextReference());
        downs.setX(90);
        downs.setY(135);
        yards.setX(185);
        yards.setY(135);

        Group masterGroup = new Group(background, downs, yards);

        //region TRANSITION FUNCTIONS

        TranslateTransition moveTransition =
                new TranslateTransition(new Duration(500), masterGroup);
        moveTransition.setToX(targetX);
        moveTransition.setToY(targetY);
        moveTransition.setFromX(targetX);
        moveTransition.setFromY(0.0);

        FadeTransition fadeTransition = new FadeTransition(new Duration(100), masterGroup);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ParallelTransition masterTransition =
                new ParallelTransition(moveTransition, fadeTransition);

        //endregion

        MediaGraphicElement graphicElement =
                new MediaGraphicElement("Play State", masterGroup, masterTransition);

        graphicElement.AddUpdatableTextField(downWrapper);
        graphicElement.AddUpdatableTextField(yardsWrapper);

        return graphicElement;
    }

    private static MediaGraphicElement SmallScoreOverlay()
    {
        // VARIABLES

        String f = GetResourcesPathStart() + "images/Layers_SmallScore/";
        Double targetX = 25.0;
        Double targetY = 50.0;


        // LAYER ELEMENTS

        ImageView base1 = new ImageView(new Image(f + "SmallScore_LeftRect-1.png"));
        ImageView base2 = new ImageView(new Image(f + "SmallScore_Accent-2.png"));

        Group left = new Group(base1, base2);
        ImageView base3 = new ImageView(new Image(f + "SmallScore_RightRect-3.png"));

        Group sides = new Group(left, base3);
        ImageView topBlock = new ImageView(new Image(f + "SmallScore_TopBar.png"));

        //region TEXT FIELDS

        Text PINN = new Text("PINN:");
        PINN = FxTextWrapper.Coloring_Pinn(PINN);
        PINN = FxTextWrapper.Theme_SmallScore(PINN);
        PINN.setX(35.0);
        PINN.setY(60.0);

        FxTextWrapper OppoWrapper = new FxTextWrapper(":", 1, true);
        Text OPPO = OppoWrapper.GetTextReference();
        OPPO = FxTextWrapper.Theme_SmallScore(OPPO);
        OPPO.setX(35.0);
        OPPO.setY(95.0);

        FxTextWrapper ScorePinnWrapper = new FxTextWrapper("", 2, false);
        FxTextWrapper ScoreOppoWrapper = new FxTextWrapper("", 3, false);
        Text ScorePinn = FxTextWrapper.Theme_SmallScore(ScorePinnWrapper.GetTextReference());
        Text ScoreOppo = FxTextWrapper.Theme_SmallScore(ScoreOppoWrapper.GetTextReference());
        ScorePinn.setFill(Color.WHITE);
        ScoreOppo.setFill(Color.WHITE);
        ScorePinn.setX(215.0);
        ScorePinn.setY(60.0);
        ScoreOppo.setX(215.0);
        ScoreOppo.setY(95.0);

        FxTextWrapper QtrValue = new FxTextWrapper("", 4, false);
        Text Qtr = FxTextWrapper.Theme_SmallScore(QtrValue.GetTextReference());
        Qtr.setFill(Color.WHITE);
        Qtr.setTextAlignment(TextAlignment.CENTER);
        Qtr.setX(285.0);
        Qtr.setY(60.0);

        Text QtrLabel = FxTextWrapper.Theme_SmallScore(new Text("Qtr"));
        QtrLabel.setFill(Color.WHITE);
        QtrLabel.setTextAlignment(TextAlignment.CENTER);
        QtrLabel.setX(285.0);
        QtrLabel.setY(90.0);

        //endregion

        Group top = new Group(topBlock, PINN, OPPO, ScorePinn, ScoreOppo, Qtr, QtrLabel);
        Group masterGroup = new Group(sides, top);


        //region TRANSITION FUNCTIONS

        TranslateTransition leftSwipe =
                new TranslateTransition(new Duration(800), left);

        leftSwipe.setDelay(new Duration(300));

        leftSwipe.setFromX(-600.0);
        leftSwipe.setFromY(targetY);
        leftSwipe.setToX(targetX);
        leftSwipe.setToY(targetY);

        TranslateTransition rightSwipe =
                new TranslateTransition(new Duration(600), base3);

        rightSwipe.setDelay(new Duration(200));

        rightSwipe.setFromX(-600.0);
        rightSwipe.setFromY(targetY);
        rightSwipe.setToX(targetX);
        rightSwipe.setToY(targetY);

        FadeTransition sidesFade =
                new FadeTransition(new Duration(1200), sides);

        sidesFade.setFromValue(0.0);
        sidesFade.setToValue(1.0);

        TranslateTransition dropDown =
                new TranslateTransition(new Duration(400), top);

        dropDown.setDelay(new Duration(200));
        dropDown.setFromX(targetX);
        dropDown.setToX(targetX);
        dropDown.setFromY(-300.0);
        dropDown.setToY(targetY);

        FadeTransition topFade =
                new FadeTransition(new Duration(400), top);

        topFade.setDelay(new Duration(300));
        topFade.setFromValue(0.0);
        topFade.setToValue(1.0);

        ParallelTransition masterTransition =
                new ParallelTransition(
                        leftSwipe, rightSwipe, dropDown);

        //endregion


        // FINISHED PRODUCT

        MediaGraphicElement graphicElement =
                new MediaGraphicElement("Small Scoreboard", masterGroup, masterTransition);

        graphicElement.AddUpdatableTextField(OppoWrapper);
        graphicElement.AddUpdatableTextField(ScorePinnWrapper);
        graphicElement.AddUpdatableTextField(ScoreOppoWrapper);
        graphicElement.AddUpdatableTextField(QtrValue);

        return graphicElement;

    }


}
