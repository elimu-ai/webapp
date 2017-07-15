package ai.elimu.model.enums;

public enum Team {

    ANALYTICS("team-analytics"),
    CONTENT_CREATION("team-content-creation"),
    DEVELOPMENT("team-development"),
    MARKETING("team-marketing"),
    TESTING("team-testing"),
    TRANSLATION("team-translation"),
    OTHER(null);
    
    private Team(String slackChannelName) {
        this.slackChannelName = slackChannelName;
    }
    
    private String slackChannelName;

    public String getSlackChannelName() {
        return slackChannelName;
    }
}
