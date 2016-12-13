package com.example.tim.gentleresolve.models;

import android.os.Message;

import org.parceler.Parcel;

import java.util.ArrayList;

public class Vision {
    private String name;
    private String pushId;

    public Vision() {
    }

    public Vision(String name) {
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