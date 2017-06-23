package com.android_mobile.core.utiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class BitmapUtils {

    /**
     * 水平方向模糊度
     */
    private static float hRadius = 10;
    /**
     * 竖直方向模糊度
     */
    private static float vRadius = 10;
    /**
     * 模糊迭代度
     */
    private static int iterations = 7;

    /**
     * Drawable 转换为Bitmap
     */
    public static Bitmap convertToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            try {
                int width = drawable.getBounds().width();
                int height = drawable.getBounds().height();
                Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(temp);
                drawable.draw(tempCanvas);
                int minSize = Math.min(width, height);
                return ThumbnailUtils.extractThumbnail(temp, minSize, minSize);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static Bitmap convertToBitmap(String path, int w, int h, int degree) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
                    BitmapFactory.decodeFile(path, opts));
            Matrix matrix = new Matrix();
            if (degree == 90) {
                matrix.postRotate(90); //顺时针翻转90度
            }if (degree == 270) {
                matrix.postRotate(270);
            }
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak
                    .get().getWidth(), weak.get().getHeight(), matrix, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 模糊效果 ,对Bitmap 进行高斯模糊处理
     */
    public static Bitmap processBitmapBlur(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newColor = 0;
        int[][] colors = new int[9][3];
        for (int i = 1, length = width - 1; i < length; i++) {
            for (int k = 1, len = height - 1; k < len; k++) {
                for (int m = 0; m < 9; m++) {
                    int s = 0;
                    int p = 0;
                    switch (m) {
                        case 0:
                            s = i - 1;
                            p = k - 1;
                            break;
                        case 1:
                            s = i;
                            p = k - 1;
                            break;
                        case 2:
                            s = i + 1;
                            p = k - 1;
                            break;
                        case 3:
                            s = i + 1;
                            p = k;
                            break;
                        case 4:
                            s = i + 1;
                            p = k + 1;
                            break;
                        case 5:
                            s = i;
                            p = k + 1;
                            break;
                        case 6:
                            s = i - 1;
                            p = k + 1;
                            break;
                        case 7:
                            s = i - 1;
                            p = k;
                            break;
                        case 8:
                            s = i;
                            p = k;
                    }
                    pixColor = bmp.getPixel(s, p);
                    colors[m][0] = Color.red(pixColor);
                    colors[m][1] = Color.green(pixColor);
                    colors[m][2] = Color.blue(pixColor);
                }

                for (int m = 0; m < 9; m++) {
                    newR += colors[m][0];
                    newG += colors[m][1];
                    newB += colors[m][2];
                }

                newR = (int) (newR / 9F);
                newG = (int) (newG / 9F);
                newB = (int) (newB / 9F);

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                newColor = Color.argb(255, newR, newG, newB);
                bitmap.setPixel(i, k, newColor);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        return bitmap;
    }

    /**
     * 柔化效果(高斯模糊)(优化后比上面快三倍)
     */
    public static Bitmap processBitmapBlurFast(Bitmap bmp) {
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int delta = 50; // 值越小图片会越亮，越大则越暗
        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 高斯模糊
     */
    public static Bitmap progressBitmapPoxBlur(Bitmap bmp) {
        if (bmp != null) {
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            int[] inPixels = new int[width * height];
            int[] outPixels = new int[width * height];
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < iterations; i++) {
                blur(inPixels, outPixels, width, height, hRadius);
                blur(outPixels, inPixels, height, width, vRadius);
            }
            blurFractional(inPixels, outPixels, width, height, hRadius);
            blurFractional(outPixels, inPixels, height, width, vRadius);
            bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
            return bitmap;
        }
        return null;
    }

    /**
     * 根据图片路径 压缩图片并且覆盖成压缩后的图片
     */
    public static void processBitmapCompress(String imagePath, int w, int h) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 不把图片读到内存中,但依然可以计算出图片的大小
        BitmapFactory.decodeFile(imagePath, options);//压缩到到bitmap为null
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > h || width > w) {
            final int heightRatio = Math.round((float) height
                    / (float) h);
            final int widthRatio = Math.round((float) width / (float) w);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // 在内存中创建bitmap对象，这个对象按照缩放大小创建的
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        int degree = obtainBitmapDegree(imagePath);
        bitmap = processBitmapRotate(bitmap, -degree);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        try {
            FileOutputStream fos = new FileOutputStream(imagePath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按像素图片压缩，然后覆盖
     *
     * @param strImgPath2
     */
    public static void processBitmapCompress(String strImgPath2) {
        Bitmap bmp = obtainBitmap(strImgPath2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;//
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 300 && options > 10) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(strImgPath2);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     */
    public static byte[] processImgByteArray(String imagePath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;// 不把图片读到内存中,但依然可以计算出图片的大小
            BitmapFactory.decodeFile(imagePath, options);//压缩到到bitmap为null
            int height = options.outHeight;
            int width = options.outWidth;
            int inSampleSize = 1;
            int reqWidth = 1200;
            int reqHeight = 1200;
            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            // 在内存中创建bitmap对象，这个对象按照缩放大小创建的
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            int degree = BitmapUtils.obtainBitmapDegree(imagePath);
            bitmap = BitmapUtils.processBitmapRotate(bitmap, -degree);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            return baos.toByteArray();
        } catch (Exception | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 旋转照片
     */
    public static Bitmap processBitmapRotate(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return null;
    }

    /**
     * 模糊
     */
    public static void blur(int[] in, int[] out, int width, int height, float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];
        for (int i = 0; i < 256 * tableSize; i++)
            divide[i] = i / tableSize;
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }
            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];
                int i1 = x + r + 1;
                if (i1 > widthMinus1)
                    i1 = widthMinus1;
                int i2 = x - r;
                if (i2 < 0)
                    i2 = 0;
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];
                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }

    public static void blurFractional(int[] in, int[] out, int width, int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];
                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;
        }
    }

    public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    /**
     * 获取图片
     * 支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    public static Bitmap obtainBitmapStream(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        int size = 1024;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), size);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, size);
            byte[] b = new byte[size];
            int read;
            while ((read = in.read(b)) != -1) {
                out.write(b, 0, read);
            }
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap obtainBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        // opt.inSampleSize =2;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 根据指定宽高和角度获取图片
     *
     * @param path   图片路径
     * @param w      宽
     * @param h      高
     * @param degree 角度
     * @return
     */
    public static Bitmap obtainBitmap(String path, int w, int h, int degree) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
                    BitmapFactory.decodeFile(path, opts));
            Matrix matrix = new Matrix();
            if (degree == 90) {
                matrix.postRotate(90); //顺时针翻转90度
            }
            if (degree == 270) {
                matrix.postRotate(270);
            }
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak
                    .get().getWidth(), weak.get().getHeight(), matrix, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按宽高压缩图片
     *
     * @param path 图片路径
     * @return
     */
    public static Bitmap obtainBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 400);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be + 3;
        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
        bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap != null) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            System.out.println(w + "   " + h);
        }
        return bitmap;
    }

    /**
     * 按照屏幕比例压缩图片
     *
     * @param ctx      上下文
     * @param pathName 图片路径
     * @return
     */
    public static Bitmap obtainBitmap(Context ctx, String pathName) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap srcPic = BitmapFactory.decodeFile(pathName, opts);
        int scrWidth = opts.outWidth;
        int srcHeight = opts.outHeight;
        WindowManager ws = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = ws.getDefaultDisplay();
        int screenWidth = defaultDisplay.getWidth();
        int screenHeight = defaultDisplay.getHeight();
        int sclae = 1;
        int widthSc = scrWidth / screenWidth;//1348/370
        int heightSc = srcHeight / screenHeight;//768/480 1
        if (widthSc > heightSc && widthSc > 1) {
            sclae = widthSc;
        } else if (heightSc > widthSc && heightSc > 1) {
            sclae = heightSc;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = sclae;
        System.out.println("w:" + opts.outWidth + "..h:" + opts.outHeight);
        return BitmapFactory.decodeFile(pathName, opts);
    }

    /**
     * 获取圆形图片
     */
    public static Bitmap obtainRoundBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获得圆角图片
     */
    public static Bitmap obtainRoundBitmap(Context context, int res) {
        return obtainRoundBitmap(BitmapFactory.decodeResource(
                context.getResources(), res));
    }

    /**
     * 获取图片角度
     *
     * @param path 图片路径
     * @return
     */
    public static int obtainBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * View 截屏
     *
     * @param view
     * @return
     */
    public static Bitmap obtainBitmapByView(@NonNull View view) {
        if (view.getWidth() > 0 && view.getHeight() > 0) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), config);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }
        return null;
    }

}
