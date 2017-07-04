package com.webber.mcorelibspace.demo.core.databases;

import android.content.Context;

import com.android_mobile.core.utiles.Utiles;
import com.j256.ormlite.dao.Dao;
import com.webber.mcorelibspace.demo.core.databases.tableBean.UsageTimer;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mxh on 2017/7/4.
 * Describe：
 */

public class DataDao {

    private Dao<UsageTimer, Integer> usageDao;
    private DatabaseHelper helper;

    public DataDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context, Utiles.getAppVersionCode(context));
            usageDao = helper.getDao(UsageTimer.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUsage(UsageTimer usageTimer) {
        try {
          /*  // select count(*) from tb_usage where uuid = 'usageTimer.getUuid()';
            List timers = usageDao.queryBuilder().where().eq("uuid", usageTimer.getUuid()).query();
            if (timers != null && timers.size() > 0) {
                // update tb_usage set versionCode = 'usageTimer.getVersionCode()',openTime = 'usageTimer.getOpenTime()' where uuid = 'usageTimer.getUuid()';
                usageDao.updateBuilder().updateColumnValue("versionCode", usageTimer.getVersionCode())
                        .updateColumnValue("openTime", usageTimer.getOpenTime()).where().eq("uuid", usageTimer.getUuid());
            } else {
                usageDao.create(usageTimer);
            }*/
            usageDao.create(usageTimer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UsageTimer> queryAllUsage() {
        try {
            return usageDao.queryBuilder().query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
