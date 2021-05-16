import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CatFact {
    private String id;
    private String text;
    private String type;
    private String user;
    private String upvotes;
    private String userUpvoted;
    private boolean used;
    private boolean statusVerified;
    private int statusSentCount;


    public CatFact(
            @JsonProperty("_id") String id,
            @JsonProperty("text") String text,
            @JsonProperty("type") String type,
            @JsonProperty("user") String user,
            @JsonProperty("upvotes") String upvotes,
            @JsonProperty("userUpvoted") String userUpvoted,
            @JsonProperty("used") String used,
            @JsonProperty("status") Map<String, String> status
            ) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.user = user;
        this.upvotes = upvotes;
        this.userUpvoted = userUpvoted;
        this.used = Boolean.parseBoolean(used);
        this.statusVerified = Boolean.parseBoolean(status.get("verified"));
        this.statusSentCount = Integer.parseInt(status.get("sentCount"));
    }

    public String getUpvotes() {
        return upvotes;
    }

    public boolean getUsed() {
        return used;
    }

    public boolean getStatusVerified() {
        return statusVerified;
    }

    public int getSentCount() {
        return statusSentCount;
    }

    @Override
    public String toString(){
        return String.format("Cat fact № %s: %s (%sused - upvotes: %s) - from user %s [Was sent: %d time(s)]" , id, text, used == false? "not " : "" ,upvotes, user, statusSentCount);
    }
}
