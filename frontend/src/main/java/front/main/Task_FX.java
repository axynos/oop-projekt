package front.main;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;

public class Task_FX extends VBox implements Runnable {

    private String taskTitle;
    private String tag;
    private String taskContent;
    private String[] assignedPeople;

    public Task_FX(String taskTitle, String tag, String taskContent, String[] assignedPeople) {
        this.taskTitle = taskTitle;
        this.tag = tag;
        this.taskContent = taskContent;
        this.assignedPeople = assignedPeople;
    }

    @Override
    public void run() {
        HBox taskHeader = new HBox();
        taskHeader.getChildren().add(new Text(taskTitle));
        taskHeader.getChildren().add(makeTag(tag));
        getChildren().add(taskHeader);

        Text text = new Text(taskContent);
        text.setWrappingWidth(400);
        getChildren().add(text);

        HBox assignedAvatars = new HBox();
        for (String assignedPerson : assignedPeople) {
            assignedAvatars.getChildren().add(new Avatar(10.0, assignedPerson));
        }
        getChildren().add(assignedAvatars);
    }

    private StackPane makeTag(String text) {
        final StackPane avatar = new StackPane();
        final Text textNode = new Text(text);
        final Rectangle rectangle = new Rectangle(80, 20, Color.CYAN);
        avatar.getChildren().addAll(rectangle, textNode);

        return avatar;
    }
}
