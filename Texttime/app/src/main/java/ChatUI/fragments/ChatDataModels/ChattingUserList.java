package ChatUI.fragments.ChatDataModels;

/**
 * Created by dinesh on 11/19/16.
 */

public class ChattingUserList {

    String displayName; // Username, Display Name or Contact Name
    String lastMessage; // Last Message of user send
    String lastSeen;    // Last Message sending time
    String messageStatus; // Message READ, UNREAD, DELIVER, NOT DELIVER, NOT SEND
    String jabberID;
    boolean userStatus; // ONLINE, OFFLINE
    byte[] userAvatar; //User Profile photo
    String baseImage;
    int newMessageCount; //number Of New Message of user
    String lastMessageTime;

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public int getNewMessageCount() {
        return newMessageCount;
    }

    public void setNewMessageCount(int newMessageCount) {
        this.newMessageCount = newMessageCount;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }

    public boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getJabberID() {
        return jabberID;
    }

    public void setJabberID(String jabberID) {
        this.jabberID = jabberID;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
