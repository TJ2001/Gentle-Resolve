package com.example.tim.gentleresolve.models;

import android.os.Message;

import java.util.ArrayList;

/**
 * Created by TJ2001 on 12/13/2016.
 */
public class Vision {
    public String name;
    public String pushId;

    public Vision(String name) {
        this.name = name;
    }

    public Vision() {
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
