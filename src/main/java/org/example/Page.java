package org.example;

public class Page {
    private String html;
    private boolean AccessibleByEveryone;

    public Page(String html, boolean accessibleByEveryone) {
        this.html = html;
        this.AccessibleByEveryone = accessibleByEveryone;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isAccessibleByEveryone() {
        return AccessibleByEveryone;
    }

    public void setAccessibleByEveryone(boolean accessibleByEveryone) {
        AccessibleByEveryone = accessibleByEveryone;
    }
}
