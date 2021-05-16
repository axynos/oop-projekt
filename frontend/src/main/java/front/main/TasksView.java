package front.main;

import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TasksView extends GridPane implements Runnable {

    private String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
    private String[] assignedPeople = new String[] {"RT", "JS", "LI", "UN"};

    @Override
    public void run() {
        VBox col1 = new VBox();
        //col1.setStyle("-fx-background-color: -fx-background; -fx-background: red ;");
        col1.getChildren().add(new Label("Todo"));
        VBox col2 = new VBox();
        col2.getChildren().add(new Label("In progress"));
        //col2.setStyle("-fx-background-color: -fx-background; -fx-background: green ;");
        VBox col3 = new VBox();
        col3.getChildren().add(new Label("Done"));
        //col3.setStyle("-fx-background-color: -fx-background; -fx-background: blue ;");

        VBox[] cols = new VBox[] {col1, col2, col3};
        for (VBox col : cols) {
            for (int i = 0; i < 5; i++) {
                Task_FX taskFX = new Task_FX("Lorem ipsum", "Design", lorem, assignedPeople);
                taskFX.run();
                col.getChildren().add(taskFX);
            }
        }

        add(col1, 0, 0);
        add(col2, 1, 0);
        add(col3, 2, 0);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/3.0);
            cc.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(cc);
        }

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        getRowConstraints().add(rc);
    }
}
