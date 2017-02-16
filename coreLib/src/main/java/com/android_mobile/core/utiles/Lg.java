package com.android_mobile.core.utiles;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Formatter;

public class Lg {

    public static boolean DEBUG = true;
    public static String tag = "lg";
    public static Gson g = new Gson();

    public static void printJson(String o) {
        if (DEBUG) {
            if (o == null || o.equals("")) {
                return;
            }
            try {
                System.err.println(StringTookit.JSONStringFormat(o));
            } catch (Exception e) {

            }
        }
    }

    public static void printJson(Object o) {
        if (DEBUG) {
            System.err.println(StringTookit.JSONStringFormat(g.toJson(o)));
        }
    }

    public static void println(Object obj, boolean showMemory) {
        if (DEBUG && showMemory) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
            Log.d(tag, "\n★★★★★★★★★★★★★★★★★★★★★★★★★★");
            Log.d(tag, "★[Class:"
                    + Thread.currentThread().getStackTrace()[3]
                    .getClassName()
                    + "  Method:"
                    + Thread.currentThread().getStackTrace()[3]
                    .getMethodName()
                    + "  Line:"
                    + Thread.currentThread().getStackTrace()[3]
                    .getLineNumber()
                    + "]★  \n★[Memory:"
                    + df.format((Runtime.getRuntime().totalMemory() - Runtime
                    .getRuntime().freeMemory()) / 1024.0 / 1024.0)
                    + " M / "
                    + df.format(Runtime.getRuntime().maxMemory() / 1024.0 / 1024.0)
                    + " M]★");
            if (obj != null) {
                Log.d(tag, obj.toString());
            }
            Log.d(tag, "★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
        } else if (DEBUG && !showMemory) {
            Log.d(tag, obj.toString());
        }
    }

    public static void print(Object obj) {
        if (DEBUG) {
            System.out.println(obj);
        }
    }

    public static void print(String tag, Object obj) {
        if (DEBUG) {
            Log.v(tag, obj + "");
        }
    }

    public static void print(String msg, Object... args) {
        if (DEBUG) {
            System.out.println(format(msg, args));
        }
    }

    public static String format(String msg, Object... args) {
        //TODO: String.format is extremely slow on Android, replace it with a custom/simplified implementation
        ReusableFormatter formatter = thread_local_formatter.get();
        return formatter.format(msg, args);
    }

    private static final ThreadLocal<ReusableFormatter> thread_local_formatter = new ThreadLocal<ReusableFormatter>() {
        protected ReusableFormatter initialValue() {
            return new ReusableFormatter();
        }
    };

    private static class ReusableFormatter {
        private Formatter formatter;
        private StringBuilder builder;

        public ReusableFormatter() {
            builder = new StringBuilder();
            formatter = new Formatter(builder);
        }

        public String format(String msg, Object... args) {
            formatter.format(msg, args);
            String s = builder.toString();
            builder.setLength(0);
            return s;
        }
    }

}
