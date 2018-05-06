package br.com.vision.model;

public class Chat {
    private String message;
    private boolean isBot;

    public Chat(String message, boolean isBot) {
        this.message = message;
        this.isBot = isBot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }
}
