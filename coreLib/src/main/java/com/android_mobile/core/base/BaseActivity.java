package com.android_mobile.core.base;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;
import android.widget.RelativeLayout;

import com.android_mobile.core.BasicActivity;

public abstract class BaseActivity extends BasicActivity {

	private int modalAnimTime = 300;

	public void pushModalComponent(BaseComponent bc, int widthDip) {
		if (bc.isDisplay() == true)
			return;
		bc.getRoot().setVisibility(View.VISIBLE);
		bc.setOffset(widthDip);
		RelativeLayout.LayoutParams imagebtn_params = (RelativeLayout.LayoutParams) bc
				.getRoot().getLayoutParams();
		imagebtn_params.height = (int) SCREEN_HEIGHT;
		imagebtn_params.width = widthDip;
		bc.getRoot().setLayoutParams(imagebtn_params);
		ObjectAnimator
				.ofFloat(bc.getRoot(), "translationX", SCREEN_WIDTH,
						SCREEN_WIDTH - widthDip).setDuration(modalAnimTime)
				.start();
		bc.setDisplay(true);
	}

	public void popModalComponent(BaseComponent bc) {
		if (!bc.isDisplay())
			return;
		ObjectAnimator
				.ofFloat(bc.getRoot(), "translationX",
						SCREEN_WIDTH - bc.getOffset(), SCREEN_WIDTH)
				.setDuration(modalAnimTime).start();
		bc.setDisplay(false);
	}

	public Bitmap getCroppedBitmap(Bitmap bmp) {

		int w = bmp.getWidth();
		int h = bmp.getHeight();
		int radius = w > h ? h : w;

		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffa19774;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
				sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		Paint paint1 = new Paint();

		paint1.setColor(Color.WHITE);
		paint1.setStrokeWidth(2);
		paint1.setStyle(Style.STROKE);
		paint1.setAntiAlias(true);
		canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
				sbmp.getWidth() / 2 - 1, paint1);
		return output;
	}
}
