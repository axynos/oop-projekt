package front.main;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Task extends VBox implements Runnable {

    private String taskTitle;
    private String tag;
    private String taskContent;
    private String[] assignedPeople;

    public Task(String taskTitle, String tag, String taskContent, String[] assignedPeople) {
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

        text.setOnMouseClicked(event -> {
            GridPane layout = new GridPane();
            Scene newScene = new Scene(layout, 854, 480);

            //Gaps between components
            layout.setHgap(10);
            layout.setVgap(10);

            //Resizing constraints
            ColumnConstraints colmn1 = new ColumnConstraints();
            colmn1.setHgrow(Priority.ALWAYS);
            ColumnConstraints colmn2 = new ColumnConstraints();
            colmn2.setHgrow(Priority.SOMETIMES);

            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.NEVER);
            RowConstraints row2 = new RowConstraints();
            row2.setVgrow(Priority.ALWAYS);

            layout.getColumnConstraints().addAll(colmn1, colmn2);
            layout.getRowConstraints().addAll(row1, row2);

            //Elements of the layout
            Label title = new Label(taskTitle);
            ComboBox tags = new ComboBox<String>();
            tags.getItems().add(tag);
            TextField textField = new TextField(taskContent);
            textField.setLayoutY(300);
            Button deleteButton = new Button("DELETE");

            //Alignment of elements
            GridPane.setHalignment(title, HPos.LEFT);
            GridPane.setHalignment(deleteButton, HPos.RIGHT);
            GridPane.setHalignment(textField, HPos.LEFT);

            //Adding elements to the layout
            layout.add(title, 0, 0, 1, 1);
            layout.add(tags, 1, 0, 1, 1);
            layout.add(deleteButton, 5, 0, 1, 1);
            layout.add(textField, 0, 1, 3, 3);

            //New window
            Stage newWindow = new Stage();
            newWindow.setTitle("Task");
            newWindow.setScene(newScene);

            //Position of second window, in relation to first window
            newWindow.setX(getScene().getX() + 320);
            newWindow.setY(getScene().getY() + 120);
            newWindow.setAlwaysOnTop(true);

            //Blurring the previous window
            GaussianBlur g = new GaussianBlur();
            getParent().getParent().getParent().setEffect(g);

            newWindow.show();

            //If the task window is closed, the main window unblurs.
            newWindow.setOnCloseRequest(event1 -> {
                getParent().getParent().getParent().setEffect(null);
            });
        });

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
