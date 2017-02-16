package com.android_mobile.capture;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.android_mobile.core.R;
import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View {

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192,
			128, 64 };
	private static final long ANIMATION_DELAY = 100L;
	private static final int OPAQUE = 0xFF;

	private final Paint paint;
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;
	@SuppressWarnings("unused")
	private final int frameColor;
	private final int laserColor;
	private final int resultPointColor;
	private final int cornerColor;
	private int scannerAlpha;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;
	private boolean laserLinePortrait = true;

	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		paint = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		frameColor = resources.getColor(R.color.viewfinder_frame);
		laserColor = resources.getColor(R.color.viewfinder_laser);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		cornerColor = resources.getColor(R.color.viewfinder_corner);
		scannerAlpha = 0;
		possibleResultPoints = new HashSet<ResultPoint>(5);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		int side=70;
		// Draw the exterior (i.e. outside the framing rect) darkened
		// 画边上的蒙层
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, width, frame.top+side, paint);
		canvas.drawRect(0, frame.top+side, frame.left+side, frame.bottom-side, paint);
		canvas.drawRect(frame.right-side, frame.top+side, width, frame.bottom-side, paint);
		canvas.drawRect(0, frame.bottom-side, width, height, paint);

		// 画4个角
		paint.setColor(cornerColor);
		canvas.drawRect(frame.left+side - 10, frame.top+side - 10, frame.left+side + 10,
				frame.top+side - 5, paint);
		canvas.drawRect(frame.left+side - 10, frame.top+side - 10, frame.left+side - 5,
				frame.top+side + 10, paint);
		canvas.drawRect(frame.right-side - 10, frame.top+side - 10, frame.right-side + 10,
				frame.top+side - 5, paint);
		canvas.drawRect(frame.right-side + 5, frame.top+side - 10, frame.right-side + 10,
				frame.top+side + 10, paint);
		canvas.drawRect(frame.left+side - 10, frame.bottom-side - 10, frame.left+side - 5,
				frame.bottom-side + 10, paint);
		canvas.drawRect(frame.left+side - 10, frame.bottom-side + 5, frame.left+side + 10,
				frame.bottom-side + 10, paint);
		canvas.drawRect(frame.right-side + 5, frame.bottom-side - 10, frame.right-side + 10,
				frame.bottom-side + 10, paint);
		canvas.drawRect(frame.right-side - 10, frame.bottom-side + 5, frame.right-side + 10,
				frame.bottom-side + 10, paint);

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			// Draw a two pixel solid black border inside the framing rect
			// 画出扫描区域
			// paint.setColor(frameColor);
			// canvas.drawRect(frame.left, frame.top, frame.right, frame.bottom,
			// paint);
			// canvas.drawRect(frame.left, frame.top, frame.right + 1,
			// frame.top + 2, paint);
			// canvas.drawRect(frame.left, frame.top + 2, frame.left + 2,
			// frame.bottom - 1, paint);
			// canvas.drawRect(frame.right - 1, frame.top, frame.right + 1,
			// frame.bottom - 1, paint);
			// canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1,
			// frame.bottom + 1, paint);

			// 画出红色的中线
			// Draw a red "laser scanner" line through the middle to show
			// decoding is active
			paint.setColor(laserColor);
			paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
			scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
			int middle = frame.height() / 2 + frame.top;

			if (laserLinePortrait) {
				canvas.drawRect(frame.left+side, middle - 1, frame.right-side,
						middle + 1, paint);
			} else {
				float left = frame.left + (frame.right - frame.left) / 2 - 2;
				@SuppressWarnings("unused")
				float top = frame.top - (frame.right - frame.left) / 2 - 2;
				canvas.drawRect(left, frame.top, left + 2, frame.bottom - 2,
						paint);
			}
			Collection<ResultPoint> currentPossible = possibleResultPoints;
			Collection<ResultPoint> currentLast = lastPossibleResultPoints;
			if (currentPossible.isEmpty()) {
				lastPossibleResultPoints = null;
			} else {
				possibleResultPoints = new HashSet<ResultPoint>(5);
				lastPossibleResultPoints = currentPossible;
				paint.setAlpha(OPAQUE);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentPossible) {
					// canvas.drawCircle(frame.left + point.getX(), frame.top +
					// point.getY(), 6.0f, paint);
					canvas.drawCircle(frame.left + point.getY(), frame.top
							+ point.getX(), 6.0f, paint);
				}
			}
			if (currentLast != null) {
				paint.setAlpha(OPAQUE / 2);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 3.0f, paint);
				}
			}

			// Request another update at the animation interval, but only
			// repaint the laser line,
			// not the entire viewfinder mask.
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
					frame.right, frame.bottom);
		}
	}

	public void changeLaser() {
		if (laserLinePortrait) {
			laserLinePortrait = false;
		} else {
			laserLinePortrait = true;
		}
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

}
