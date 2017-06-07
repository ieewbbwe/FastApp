package com.android_mobile.share;


/**
 * @author mxh
 */
public final class SharePlatform {

    public  int icon;
    public String name;
    public String shareMedia;

    public SharePlatform(int icon, String name, String media) {
        this.icon = icon;
        this.name = name;
        this.shareMedia = media;
    }

    public SharePlatform() {
    }
}
