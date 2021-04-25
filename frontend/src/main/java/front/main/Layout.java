package front.main;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class Layout extends GridPane implements Runnable {

    private TopBar topbar;
    private TasksView tasksView;

    @Override
    public void run() {

        topbar = new TopBar();
        topbar.run();
        add(topbar, 0, 0);
        setMargin(topbar, new Insets(40, 40, 40, 40));

        Text viewHeading = new Text("Tasks");
        viewHeading.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
        add(viewHeading, 0, 1);
        setMargin(viewHeading, new Insets(0, 40, 0, 40));

        tasksView = new TasksView();
        tasksView.run();
        add(tasksView, 0, 2);
        setMargin(tasksView, new Insets(20, 40, 0, 40));

        setHgrow(tasksView, Priority.ALWAYS);
        setVgrow(tasksView, Priority.ALWAYS);
    }
}
