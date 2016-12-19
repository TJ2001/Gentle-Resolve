package com.example.tim.gentleresolve.models;

public class Achievement {
    public String name, pushId;

    public Achievement() {
    }

    public Achievement(String name) {
        this.name = name;
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
