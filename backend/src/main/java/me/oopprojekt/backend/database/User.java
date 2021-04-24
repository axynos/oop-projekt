package me.oopprojekt.backend.database;

import static java.util.Map.entry;
import java.util.Map;

public class User {
    private Map<String, String> userPublic = Map.ofEntries(
        entry("uuid", ""), // auto-populated by db on insert
        entry("name", ""),
        entry("created_at", ""), // auto-populated by db on insert
        entry("avatar", "")
    );

    private Map<String, String> userPrivate = Map.ofEntries(
        entry("user_uuid", ""), //Foreign id reference
        entry("email", ""),
        entry("github_id", "")
    );
    public Map<String, String> getUserPublic() {
        return userPublic;
    }

    
    public Map<String, String> getUserPrivate() {
            return userPublic;
        }
}