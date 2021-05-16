package front.main;

public final class UserSession {

    private static UserSession instance;

    private String uuid;

    private UserSession(String uuid) {
        this.uuid = uuid;
    }

    public static UserSession getInstance(String uuid) {
        if(instance == null) {
            instance = new UserSession(uuid);
        }
        return instance;
    }

    public String getUuid() {
        return uuid;
    }

    public void cleanUserSession() {
        uuid = "";
    }

    @Override
    public String toString() {
        return "UserSession{" +
                ", uuid=" + uuid +
                '}';
    }
}