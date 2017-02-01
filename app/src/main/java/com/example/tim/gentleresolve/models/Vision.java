package com.example.tim.gentleresolve.models;

import android.os.Message;

import java.util.ArrayList;

/**
 * Created by TJ2001 on 12/13/2016.
 */
public class Vision {
    public String name, why, how, when, pushId;

    public Vision() {}

    public Vision(String name, String why, String how, String when) {
        this.name = name;
        this.why = why;
        this.how = how;
        this.when = when;
    }

    public String getName() {
        return name;
    }

    public String getWhy1() { return why; }

    public String getHow() { return how; }

    public String getWhen() { return when; }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
