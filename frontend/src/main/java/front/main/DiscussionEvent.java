package front.main;

import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;

public class DiscussionEvent {

    private int id;
    private String content;
    private LocalDateTime created_at;
    private User creator;

    public DiscussionEvent(int id, String content, LocalDateTime created_at, User creator) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public User getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "DiscussionEvent{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                ", creator=" + creator +
                '}';
    }
}
