package com.example.tim.gentleresolve.models;

public class Achievement {
    public String name;
    public String pushId;

    public Achievement(String name) {
        this.name = name;
    }

    public Achievement() {
    }

    public String getName() {
        return name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
