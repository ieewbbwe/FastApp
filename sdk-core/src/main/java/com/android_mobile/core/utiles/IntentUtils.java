package com.android_mobile.core.utiles;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.android_mobile.core.R;

import java.io.File;
import java.io.Serializable;

/**
 * Created by mxh on 2017/5/31.
 * Describe：
 */

public class IntentUtils {

    private static final int REQUEST_CODE_GALLERY = 0x11;
    private static final int REQUEST_CODE_CAMERA = 0x12;
    private final static int REQUEST_CODE_CROP = 0x13;

    /**
     * 打电话
     */
    public static void call(Context context, int phoneNum) {
        Intent intent = new Intent();
        // 启动电话程序
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://" + phoneNum));
        context.startActivity(intent);
    }


    /**
     * 打开系统摄像头录像,并保存为图片
     */
    public static void openCamera(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" + System.currentTimeMillis() + ".jpg"));
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为视频
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" + System.currentTimeMillis() + ".mp4"));
        context.startActivity(intent);
    }

    /**
     * 分享
     */
    public static void shareApplication(Context context, String packname, String url) {
        // <action android:name="android.intent.action.SEND" />
        // <category android:name="android.intent.category.DEFAULT" />
        // <data android:mimeType="text/plain" />

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        /*
         * intent.putExtra(Intent.EXTRA_TEXT,
         * "推荐您使用一款软件,下载地址为:https://play.google.com/store/apps/details?id=" +
         * packname);
         */
        intent.putExtra(Intent.EXTRA_SUBJECT, "请选择分享工具");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件,下载地址为:" + url + " ?id=" + packname);
        try {
            context.startActivity(
                    Intent.createChooser(intent,
                            context.getResources().getString(R.string.label_chose_share_comp)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, context.getString(R.string.label_share_fail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送对像
     */
    public static <T extends Serializable> void sendData(Context context, T t) {
        Intent intent = new Intent(context, t.getClass());
        intent.putExtra(t.getClass().getSimpleName(), t);// 要传递对像，对像必须是经过序列化的
        context.startActivity(intent);
    }

    /**
     * 获取对像
     */
    public static <T extends Serializable> T getData(Activity context, View view) {
        return (T) context.getIntent().getSerializableExtra("account");
    }

    /**
     * 安装文件s
     */
    public static void installApp(Context context, File apkFile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(MFileProvider.getUriForFile(context, apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载
     */
    public static void unInstallApp(Context context, File apkFile) {
        Uri packageURI = Uri.parse("package:com.andorid.main");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * 获得裁剪的图片从图片集合里
     */
    public static void getimageFromGallery(Activity context, File sdcardTempFile, int crop) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("output", MFileProvider.getUriForFile(context, sdcardTempFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框 intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    /**
     * 获得裁剪的图片从Camera里
     */
    public static void getimageFromCamera(Activity context, File sdcardTempFile, int crop) {
        Uri uri = MFileProvider.getUriForFile(context, sdcardTempFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", uri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比�? intent.putExtra("aspectY",
        // 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * 获得裁剪的图片从摄像头
     */
    public static void getImageFromCamera(Activity context, File sdcardTempFile) {
        Uri uri = MFileProvider.getUriForFile(context, sdcardTempFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(Context context, String Path) {
        File file = new File(Path);
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }


    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }


    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = MFileProvider.getUriForFile(context, file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(Context context, String Path) {
        File file = new File(Path);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(MFileProvider.getUriForFile(context, file), "application/vnd.android.package-archive");
        return intent;
    }
}
