package com.android_mobile.core.base;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android_mobile.core.R;
import com.android_mobile.core.listener.IMediaImageListener;
import com.android_mobile.core.listener.IMediaPicturesListener;
import com.android_mobile.core.listener.IMediaScannerListener;
import com.android_mobile.core.listener.IMediaSoundRecordListener;
import com.android_mobile.core.listener.IMediaVideoListener;
import com.android_mobile.core.ui.pop.BottomPopWindows;
import com.android_mobile.core.utiles.Lg;
import com.android_mobile.core.utiles.MFileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mxh on 2017/6/1.
 * Describe：选择数据页面基类
 */

public abstract class SelectDataActivity extends BaseActivity {

    /*----------常用方法----------*/
    private static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    private static final int REQUEST_CODE_TAKE_VIDEO = 2;// 摄像的照相的requestCode
    private static final int RESULT_CAPTURE_RECORDER_SOUND = 3;// 录音的requestCode
    private static final int RESULT_CAPTURE_PICTURES = 4;// 本地相冊
    private static final int RESULT_SCANNER = 5;// 条码扫描
    private String strImgPath = "";// 照片文件绝对路径
    private String strVideoPath = "";// 视频文件的绝对路径
    private String strRecorderPath = "";// 录音文件的绝对路径
    private IMediaImageListener iMediaImageListener = null;
    private IMediaVideoListener iMediaVideoListener = null;
    private IMediaSoundRecordListener iMediaSoundRecordListener = null;
    private IMediaPicturesListener iMediaPicturesListener = null;
    private IMediaScannerListener iMediaScannerListener = null;
    private WindowManager.LayoutParams params;
    private BottomPopWindows popWindows;

    /**
     * 弹出 选择 图片方式组件
     */
    public void showBottomChoseView() {
        params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);
        if (popWindows == null) {
            popWindows = new BottomPopWindows(SelectDataActivity.this);
            popWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                }
            });
            popWindows.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = v.getId();
                    if (i == R.id.tv_take_photo) {
                        startCamera();
                    } else if (i == R.id.tv_pick_photo) {
                        startPictures();
                    }
                    popWindows.dismiss();
                }
            });
        }
        popWindows.showAtLocation(getRootView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 本地相册
     */
    public void startPictures() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, RESULT_CAPTURE_PICTURES);
    }

    /**
     * 照相功能
     */
    public void startCamera() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 存放照片的文件夹
        strImgPath = Environment.getExternalStorageDirectory().toString()
                + "/CONSDCGMPIC/";
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()) + ".jpg";// 照片命名
        File out = new File(strImgPath);
        if (!out.exists()) {
            out.mkdirs();
        }
        out = new File(strImgPath, fileName);
        strImgPath = strImgPath + fileName;// 该照片的绝对路径
        Uri uri = MFileProvider.getUriForFile(getThisContext(),out);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String picturePath = "";
        switch (requestCode) {
            case RESULT_CAPTURE_PICTURES:// 相册
                if (resultCode == RESULT_OK) {
                    if (iMediaPicturesListener != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            String uriStr = selectedImage.toString();
                            //部分机器获取的url不同 例如小米
                            Lg.print("picher", uriStr);
                            if (uriStr.startsWith("file://")) {
                                picturePath = uriStr.substring(7, uriStr.length());
                                if (iMediaPicturesListener != null) {
                                    iMediaPicturesListener.mediaPicturePath(picturePath);
                                }
                                return;
                            }
                            String path = uriStr.substring(10, uriStr.length());
                            if (path.startsWith("com.sec.android.gallery3d")) {
                                return;
                            }
                        }
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (filePathColumn != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);
                                cursor.close();
                            }
                        }
                        Lg.print(TAG, "select_pic_path:" + picturePath);
                        if (iMediaPicturesListener != null) {
                            iMediaPicturesListener.mediaPicturePath(picturePath);
                        }
                    }
                }
                break;
            case RESULT_CAPTURE_IMAGE:// 拍照
                if (resultCode == RESULT_OK) {
                    Lg.print(TAG, "select_capture_path:" + strImgPath);
                    if (iMediaImageListener != null) {
                        iMediaImageListener.mediaImagePath(strImgPath);
                    }
                }
                break;
            case REQUEST_CODE_TAKE_VIDEO:// 拍摄视频
                if (resultCode == RESULT_OK) {
                    Uri uriVideo = data.getData();
                    Cursor cursor = getContentResolver().query(uriVideo, null,
                            null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            /* _data：文件的绝对路径 ，_display_name：文件名 */
                            strVideoPath = cursor.getString(cursor
                                    .getColumnIndex("_data"));
                        }
                    }
                    Lg.print(TAG, "select_video_path:" + strVideoPath);
                    if (iMediaVideoListener != null) {
                        iMediaVideoListener.mediaVideoPath(strVideoPath);
                    }
                }
                break;
            case RESULT_CAPTURE_RECORDER_SOUND:// 录音
                if (resultCode == RESULT_OK) {
                    Uri uriRecorder = data.getData();
                    Cursor cursor = getContentResolver().query(uriRecorder, null,
                            null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            /* _data：文件的绝对路径 ，_display_name：文件名 */
                            strRecorderPath = cursor.getString(cursor
                                    .getColumnIndex("_data"));
                        }
                    }
                    if (iMediaSoundRecordListener != null) {
                        iMediaSoundRecordListener.mediaSoundRecordPath(strRecorderPath);
                    }
                }
                break;
            case RESULT_SCANNER:// 拍照
                if (resultCode == RESULT_OK) {
                    if (iMediaScannerListener != null) {
                        iMediaScannerListener.mediaScannerResult(data
                                .getStringExtra("result"));
                    }
                }
                break;
        }
    }

    /**
     * 设置拍照后的回调监听器
     *
     * @param listener 拍照回调
     */
    public void setMediaImageListener(IMediaImageListener listener) {
        this.iMediaImageListener = listener;
    }

    /**
     * 设置选择相册图片的回调监听器
     *
     * @param listener 相册的回调
     */
    public void setMediaPictureListener(IMediaPicturesListener listener) {
        this.iMediaPicturesListener = listener;
    }

    public void setMediaScannerListener(IMediaScannerListener listener) {
        this.iMediaScannerListener = listener;
    }

    public void setMediaVideoListener(IMediaVideoListener listener) {
        this.iMediaVideoListener = listener;
    }

    public void setMediaSoundRecordListener(IMediaSoundRecordListener listener) {
        this.iMediaSoundRecordListener = listener;
    }

}
