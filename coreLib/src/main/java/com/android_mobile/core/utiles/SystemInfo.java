package com.android_mobile.core.utiles;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SystemInfo {

    // 系统信息
    public static String fetch_version_info() {
        String result = null;
        CMDExecute cmdexe = new CMDExecute();
        try {
            String[] args = {"/system/bin/cat", "/proc/version"};
            result = cmdexe.run(args, "system/bin/");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 运营商信息
     */
    public static String getOperatorInfo(Context cx) {
        String result = null;
        TelephonyManager tm = (TelephonyManager) cx
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = " ";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion()
                + "\n";
        // TODO: Do something ...
        int mcc = cx.getResources().getConfiguration().mcc;
        int mnc = cx.getResources().getConfiguration().mnc;
        str += "IMSI MCC (Mobile Country Code): " + String.valueOf(mcc) + "\n";
        str += "IMSI MNC (Mobile Network Code): " + String.valueOf(mnc) + "\n";
        result = str;
        return result;
    }

    /**
     * 获取CPU信息
     */
    public static String getCpuInfo() {
        String result = null;
        CMDExecute cmdexe = new CMDExecute();
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            result = cmdexe.run(args, "/system/bin/");
            Log.i("result", "result=" + result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 2 *系统内存情况查看 3
     */
    public static String getMemoryInfo(Context context) {
        StringBuffer memoryInfo = new StringBuffer();

        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);

        memoryInfo.append("\nTotal Available Memory :")
                .append(outInfo.availMem >> 10).append("k");
        memoryInfo.append("\nTotal Available Memory :")
                .append(outInfo.availMem >> 20).append("k");
        memoryInfo.append("\nIn low memory situation:").append(
                outInfo.lowMemory);

        String result = null;
        CMDExecute cmdexe = new CMDExecute();
        try {
            String[] args = {"/system/bin/cat", "/proc/meminfo"};
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            Log.i("fetch_process_info", "ex=" + ex.toString());
        }
        return (memoryInfo.toString() + "\n\n" + result);
    }

    /**
     * 获取磁盘信息
     */
    public static String getDiskInfo() {
        String result = null;
        CMDExecute cmdexe = new CMDExecute();
        try {
            String[] args = {"/system/bin/df"};
            result = cmdexe.run(args, "/system/bin/");
            Log.i("result", "result=" + result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 获取网路信息
     */
    public static String getNetInfo() {
        String result = null;
        CMDExecute cmdexe = new CMDExecute();

        try {
            String[] args = {"/system/bin/netcfg"};
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            Log.i("fetch_process_info", "ex=" + ex.toString());
        }
        return result;
    }

    /**
     * 获取显示屏信息
     */
    public static String getDisplayMetrics(Context cx) {
        String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = cx.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        str += "The absolute width: " + String.valueOf(screenWidth)
                + "pixels\n";
        str += "The absolute heightin: " + String.valueOf(screenHeight)
                + "pixels\n";
        str += "The logical density of the display. : "
                + String.valueOf(density) + "\n";
        str += "X dimension : " + String.valueOf(xdpi) + "pixels per inch\n";
        str += "Y dimension : " + String.valueOf(ydpi) + "pixels per inch\n";
        return str;
    }

    /**
     * 获取已经安装的软件信息
     */
    public ArrayList<HashMap<String, Object>> fetch_installed_apps(Context c) {
        List<ApplicationInfo> packages = c.getPackageManager()
                .getInstalledApplications(0);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>(
                packages.size());

        for (ApplicationInfo aPackage : packages) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String packageName = aPackage.packageName;
            String label = " ";
            try {
                label = c.getPackageManager().getApplicationLabel(aPackage)
                        .toString();
            } catch (Exception e) {
                Log.i("Exception", e.toString());
            }
            map = new HashMap<String, Object>();
            map.put("name", label);
            map.put("desc", packageName);
            list.add(map);
        }
        return list;
    }

    /**
     * 获取运行中服务列表
     */
    public static String getRunningServicesInfo(Context context) {
        StringBuilder serviceInfo = new StringBuilder();
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> services = activityManager
                .getRunningServices(100);

        for (RunningServiceInfo si : services) {
            serviceInfo.append("pid: ").append(si.pid);
            serviceInfo.append("\nprocess: ").append(si.process);
            serviceInfo.append("\nservice: ").append(si.service);
            serviceInfo.append("\ncrashCount: ").append(si.crashCount);
            serviceInfo.append("\nclicentCount: ").append(si.clientCount);
            // serviceInfo.append("\nactiveSince:").append(ToolHelper.formatData(si.activeSince));
            // serviceInfo.append("\nlastActivityTime: ").append(ToolHelper.formatData(si.lastActivityTime));
            serviceInfo.append("\n\n ");
        }
        return serviceInfo.toString();
    }

    /**
     * 获取正在运行的Task信息
     */
    public static String getRunningTaskInfo(Context context) {
        StringBuilder sInfo = new StringBuilder();
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = activityManager.getRunningTasks(100);
        for (RunningTaskInfo ti : tasks) {
            sInfo.append("id: ").append(ti.id);
            sInfo.append("\nbaseActivity: ").append(
                    ti.baseActivity.flattenToString());
            // sInfo.append("\nnumActivities: ").append(ti.nnumActivities);
            sInfo.append("\nnumRunning: ").append(ti.numRunning);
            sInfo.append("\ndescription: ").append(ti.description);
            sInfo.append("\n\n");
        }
        return sInfo.toString();
    }

    /**
     * 获取正在运行的进程信息
     */
    public static String getProcessInfo() {
        Log.i("fetch_process_info", "start. . . . ");
        String result = null;
        CMDExecute cmdexe = new CMDExecute();

        try {
            String[] args = {"/system/bin/top", "-n", "1"};
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            Log.i("fetch_process_info", "ex=" + ex.toString());
        }
        return result;
    }

    public static String fetch_properties() {
        Properties p = System.getProperties();
        return p.toString();
    }

    /**
     * 获取手机信息
     */
    public static String getPhoneInfo(Context c) {
        StringBuilder sInfo = new StringBuilder();
        TelephonyManager phoneMgr = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        sInfo.append("网络类型:").append(phoneMgr.getNetworkType());
        sInfo.append("手机型号:").append(Build.MODEL);
        sInfo.append("本机电话号码:").append(phoneMgr.getLine1Number());
        sInfo.append("SDK版本号:").append(Build.VERSION.SDK_INT);
        sInfo.append("Firmware/OS 版本号:").append(Build.VERSION.RELEASE);
        return sInfo.toString();
    }

    // 得到鍵盤信息,使用的語言，手機的網絡代碼（mnc）,手機的國家代碼(mcc),手機的模式，手機的方向，觸摸屏的判斷等，通過以下語句獲取：
    public Configuration fetch_configuration(Context c) {
        Configuration config = c.getResources().getConfiguration();
        return config;
    }

    // 用来存储设备信息信息
    private static Map<String, String> infos = new HashMap<String, String>();

    /* 手机设备信息 */
    public static String fetch_deviceInfo(Context ctx) {
        StringBuilder sb = new StringBuilder();

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e("", "an error occured when collect crash info", e);
            }
        }
        ApplicationInfo info;
        try {
            info = ctx.getPackageManager().getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (info.metaData != null) {
                String msg = info.metaData.getString("tel");
                infos.put("tel", msg);
                msg = info.metaData.getString("channel");
                infos.put("channel", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        return sb.toString();
    }
}
