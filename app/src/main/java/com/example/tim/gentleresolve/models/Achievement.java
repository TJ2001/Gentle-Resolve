package com.example.tim.gentleresolve.models;

public class Achievement {
    public String name;
    public String pushId;
//    public String index;

    public Achievement(String name) {
        this.name = name;
//        this.index = "not_specified";
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
