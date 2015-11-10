package objects.managers;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import objects.MediaAbstractElement;
import objects.MediaTransitionElement;

/**
 * Created by Lambeaux on 8/13/2015.
 * Container object that manages the program utilities and defines
 * invokable operations that the user can perform during the broadcast.
 *
 */
public class MediaManager
{
    // Configuration constants
    private static String[] FOLDER_NAMES =
            {"Score Overlays", "Status Overlays", "Data Overlays", "Videos", "Commercials",
                    "Data Panels", "Sponsor Panels"};

    private TreeView<MediaAbstractElement> $MediaTree;
    private ListView<MediaTransitionElement> $TransitionList;
    private CanvasManager $CanvasManager;

    public MediaManager(TreeView<MediaAbstractElement> mediaTreeFromGui,
                        ListView<MediaTransitionElement> transitionListFromGui,
                        CanvasManager canvasManager)
    {
        $TransitionList = transitionListFromGui;
        $CanvasManager = canvasManager;

        $MediaTree = mediaTreeFromGui;
        $MediaTree.setRoot(new TreeItem<>(new MediaAbstractElement("Root")));

        ObservableList<TreeItem<MediaAbstractElement>> children =
            $MediaTree.getRoot().getChildren();

        for (String fname : FOLDER_NAMES)
            children.add(new TreeItem<>(new MediaAbstractElement(fname)));

        $MediaTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void Add(MediaAbstractElement playableMedia, int folderIndex)
    {
        if (folderIndex < FOLDER_NAMES.length)
        {
            TreeItem<MediaAbstractElement> parent =
                    $MediaTree.getRoot().getChildren().get(folderIndex);

            parent.getChildren().add(new TreeItem<>(playableMedia));
        }
    }

    public void Add(MediaTransitionElement transition)
    {
        $TransitionList.getItems().add(transition);
    }

    public void UpdateAll()
    {
        ObservableList<TreeItem<MediaAbstractElement>> folders =
            $MediaTree.getRoot().getChildren();

        for (TreeItem<MediaAbstractElement> folder : folders)
        {
            ObservableList<TreeItem<MediaAbstractElement>> terminals =
                    folder.getChildren();

            for (TreeItem<MediaAbstractElement> item : terminals)
            {
                item.getValue().Update();
            }
        }
    }

    public void Cue()
    {
        TreeItem<MediaAbstractElement> selected =
                $MediaTree.getSelectionModel().getSelectedItem();

        if (selected.isLeaf())
        {
            if (selected.getParent().getValue().toString().equals("Videos") ||
                    selected.getParent().getValue().toString().equals("Commercials"))
            {

            }

            selected.getValue().Play();
        }
    }

    public void Halt()
    {
        TreeItem<MediaAbstractElement> selected =
                $MediaTree.getSelectionModel().getSelectedItem();
        if (selected.isLeaf())
            selected.getValue().Halt();
    }

}
