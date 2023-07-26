package com.notification.model;

public enum SubscribedEventType {
    Success("Success"),
    Fail("Fail");

    private String text;

    SubscribedEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static SubscribedEventType fromString(String text) {
        for (SubscribedEventType b : SubscribedEventType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
