package front.main;

public class TaskFollowing {

    private User user;
    private boolean notify_state;
    private boolean notify_discussion;
    private boolean notify_edit;

    public TaskFollowing(User user, boolean notify_state, boolean notify_discussion, boolean notify_edit) {
        this.user = user;
        this.notify_state = notify_state;
        this.notify_discussion = notify_discussion;
        this.notify_edit = notify_edit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isNotify_state() {
        return notify_state;
    }

    public void setNotify_state(boolean notify_state) {
        this.notify_state = notify_state;
    }

    public boolean isNotify_discussion() {
        return notify_discussion;
    }

    public void setNotify_discussion(boolean notify_discussion) {
        this.notify_discussion = notify_discussion;
    }

    public boolean isNotify_edit() {
        return notify_edit;
    }

    public void setNotify_edit(boolean notify_edit) {
        this.notify_edit = notify_edit;
    }

    @Override
    public String toString() {
        return "TaskFollowing{" +
                "user=" + user +
                ", notify_state=" + notify_state +
                ", notify_discussion=" + notify_discussion +
                ", notify_edit=" + notify_edit +
                '}';
    }
}
