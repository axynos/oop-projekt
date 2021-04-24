package me.oopprojekt.backend.database;

import static java.util.Map.entry;
import java.util.Map;

public class Task {
    private Map<String, String> note = Map.ofEntries(
        entry("uuid", ""), //auto-populated by db on insert
        entry("title",""),
        entry("start_at",""),
        entry("end_at", ""),
        entry("creator",""),//Foreign id reference, links task with user account.
        entry("content","")    
    );
}
