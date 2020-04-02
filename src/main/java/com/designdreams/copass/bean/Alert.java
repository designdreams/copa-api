package com.designdreams.copass.bean;

public class Alert {

    private String src;

    private String dest;

    private String channel;

    private String emailId;

    private boolean isActive;

    private String pushAPI;

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    private int matchCount;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPushAPI() {
        return pushAPI;
    }

    public void setPushAPI(String pushAPI) {
        this.pushAPI = pushAPI;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "emailId='" + emailId + '\'' +
                ", source='" + src + '\'' +
                ", destination='" + dest + '\'' +
                ", matchCount='" + matchCount + '\'' +

                '}';
    }

}
