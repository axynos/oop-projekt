package front.main;

import java.time.LocalDateTime;

public class User {

    private String uuid;
    private String first_name;
    private String last_name;
    private String avatar;
    private LocalDateTime created_at;

    public User(String uuid, String first_name, String last_name, String avatar, LocalDateTime created_at) {
        this.uuid = uuid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.created_at = created_at;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
