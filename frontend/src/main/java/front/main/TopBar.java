package front.main;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Stack;

public class TopBar extends HBox implements Runnable {

    private ComboBox<String> tagsSelect;
    private ComboBox<String> personSelect;
    private TextField searchBox;
    private SplitMenuButton userOptions;

    @Override
    public void run() {
        HBox topBarLeft = new HBox();
        HBox topBarRight = new HBox();

        tagsSelect = new ComboBox<>();
        tagsSelect.getItems().addAll(
                "All",
                "Tag 1",
                "Tag 2",
                "Tag 3"
        );
        tagsSelect.setPromptText("Tags");
        topBarLeft.getChildren().add(tagsSelect);

        personSelect = new ComboBox<>();
        personSelect.getItems().addAll(
                "All",
                "Person 1",
                "Person 2",
                "Person 3"
        );
        personSelect.setPromptText("People");
        topBarLeft.getChildren().add(personSelect);

        searchBox = new TextField();
        searchBox.setPromptText("Search");
        topBarLeft.getChildren().add(searchBox);

        userOptions = new SplitMenuButton();
        userOptions.setText("User Name");
        userOptions.getItems().add(new MenuItem("Settings"));
        userOptions.getItems().add(new MenuItem("Log out"));
        topBarRight.getChildren().add(userOptions);

        StackPane avatar = new Avatar(20.0, "UN");
        topBarRight.getChildren().add(avatar);

        getChildren().addAll(topBarLeft, topBarRight);

        //setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(topBarRight, Priority.ALWAYS);
        topBarLeft.setAlignment(Pos.CENTER_LEFT);
        topBarRight.setAlignment(Pos.CENTER_RIGHT);
    }

    private StackPane makeAvatar(String text, double size) {
        final StackPane avatar = new StackPane();
        final Circle circle = new Circle(size, Color.CYAN);
        final Text textNode = new Text(text);
        avatar.getChildren().addAll(circle, textNode);

        return avatar;
    }
}
