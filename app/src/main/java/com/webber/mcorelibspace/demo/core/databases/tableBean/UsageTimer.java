package com.webber.mcorelibspace.demo.core.databases.tableBean;

import com.android_mobile.core.listener.ISelectItem;
import com.android_mobile.core.utiles.TimerUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mxh on 2017/7/4.
 * Describe：
 */
@DatabaseTable(tableName = "tb_usage")
public class UsageTimer implements ISelectItem {
    @DatabaseField
    private String uuid;
    @DatabaseField
    private String versionCode;
    @DatabaseField
    private long openTime;

    public UsageTimer() {
    }

    public UsageTimer(String uuid, String versionCode, long openTime) {
        this.uuid = uuid;
        this.versionCode = versionCode;
        this.openTime = openTime;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取对象名
     */
    @Override
    public String getName() {
        return "uuid：" + uuid + "versionCode：" + versionCode + "openTime：" + TimerUtils.formatTime(openTime);
    }

    /**
     * 获取对象id
     */
    @Override
    public String getCode() {
        return null;
    }
}
