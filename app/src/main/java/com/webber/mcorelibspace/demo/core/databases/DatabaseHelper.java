package com.webber.mcorelibspace.demo.core.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android_mobile.core.utiles.Lg;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.webber.mcorelibspace.demo.core.databases.tableBean.UsageTimer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mxh on 2017/7/4.
 * Describe：
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "core.db";
    private static final String TAG = "DatabaseHelper";
    private static DatabaseHelper instance;
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    private final Context ctx;

    private DatabaseHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
        this.ctx = context;
    }

    public static synchronized DatabaseHelper getHelper(Context context, int version) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context, version);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UsageTimer.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Lg.print(TAG, "数据库版本更新：" + newVersion);

    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
