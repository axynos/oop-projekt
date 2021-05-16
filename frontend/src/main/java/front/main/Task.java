package front.main;

import jdk.vm.ci.meta.Local;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Task {

    private String title;
    private LocalDateTime start_at;
    private LocalDateTime end_at;
    private LocalDateTime created_at;
    private User creator;
    private Set<TaskFollowing> followers;
    private Set<Tag> tags;
    private State state;
    private LocalDateTime state_modified;
    private List<DiscussionEvent> discussion;

    public Task(String title, LocalDateTime start_at, LocalDateTime end_at, LocalDateTime created_at, User creator, Set<TaskFollowing> followers, Set<Tag> tags, State state, LocalDateTime state_modified, List<DiscussionEvent> discussion) {
        this.title = title;
        this.start_at = start_at;
        this.end_at = end_at;
        this.created_at = created_at;
        this.creator = creator;
        this.followers = followers;
        this.tags = tags;
        this.state = state;
        this.state_modified = state_modified;
        this.discussion = discussion;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStart_at() {
        return start_at;
    }

    public LocalDateTime getEnd_at() {
        return end_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public User getCreator() {
        return creator;
    }

    public Set<TaskFollowing> getFollowers() {
        return followers;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public State getState() {
        return state;
    }

    public LocalDateTime getState_modified() {
        return state_modified;
    }

    public List<DiscussionEvent> getDiscussion() {
        return discussion;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", start_at=" + start_at +
                ", end_at=" + end_at +
                ", created_at=" + created_at +
                ", creator=" + creator +
                ", followers=" + followers +
                ", tags=" + tags +
                ", state=" + state +
                ", state_modified=" + state_modified +
                ", discussion=" + discussion +
                '}';
    }
}
