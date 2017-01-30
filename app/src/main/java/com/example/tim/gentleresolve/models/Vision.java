package com.example.tim.gentleresolve.models;

import android.os.Message;

import java.util.ArrayList;

/**
 * Created by TJ2001 on 12/13/2016.
 */
public class Vision {
    public String name, why1, why2, why3, how, when, pushId;

    public Vision() {}

    public Vision(String name, String why1, String why2, String why3, String how, String when) {
        this.name = name;
        this.why1 = why1;
        this.why2 = why2;
        this.why3 = why3;
        this.how = how;
        this.when = when;
    }

    public String getName() {
        return name;
    }

    public String getWhy1() { return why1; }

    public String getWhy2() { return why2; }

    public String getWhy3() { return why3; }

    public String getHow() { return how; }

    public String getWhen() { return when; }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
