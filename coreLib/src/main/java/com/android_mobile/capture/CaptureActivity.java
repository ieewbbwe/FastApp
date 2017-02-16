package com.android_mobile.capture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android_mobile.core.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


public final class CaptureActivity extends Activity implements
		SurfaceHolder.Callback, View.OnClickListener {

	public static String SCAN="scan";
	private static final float BEEP_VOLUME = 0.10f;
	private static final long VIBRATE_DURATION = 200L;
	private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES;

	static {
		DISPLAYABLE_METADATA_TYPES = new HashSet<ResultMetadataType>(5);
		DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ISSUE_NUMBER);
		DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.SUGGESTED_PRICE);
		DISPLAYABLE_METADATA_TYPES
				.add(ResultMetadataType.ERROR_CORRECTION_LEVEL);
		DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.POSSIBLE_COUNTRY);
	}

	private enum Source {
		NATIVE_APP_INTENT, PRODUCT_SEARCH_LINK, ZXING_LINK, NONE
	}

	private CaptureActivityHandler captureHandler;

	private ViewfinderView viewfinderView;
	private MediaPlayer mediaPlayer;
	private Result lastResult;
	private boolean hasSurface;
	private boolean playBeep = true;
	private Source source;
	private String returnUrlTemplate;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private Button backButton;
	private TextView topTV, bottomTV;

	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return captureHandler;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		// 定义全屏参数
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// 获得当前窗体对象
		Window window = this.getWindow();
		// 设置当前窗体为全屏显示
		window.setFlags(flag, flag);
		setContentView(R.layout.scan);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		backButton = (Button) findViewById(R.id.capture_back_btn);
		topTV = (TextView) findViewById(R.id.scan_top_tv);
		bottomTV = (TextView) findViewById(R.id.scan_bottom_tv);
		backButton.setOnClickListener(this);
		captureHandler = null;
		lastResult = null;
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		resetStatusView();

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);

		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}

		source = Source.NONE;
		decodeFormats = null;
		characterSet = null;
		initBeepSound();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (captureHandler != null) {
			captureHandler.quitSynchronously();
			captureHandler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (source == Source.NATIVE_APP_INTENT) {
				setResult(RESULT_CANCELED);
			} else if ((source == Source.NONE || source == Source.ZXING_LINK)
					&& lastResult != null) {
				resetStatusView();
				if (captureHandler != null) {
					captureHandler.sendEmptyMessage(R.id.restart_preview);
				}
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_FOCUS
				|| keyCode == KeyEvent.KEYCODE_CAMERA) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.capture_back_btn){
			finish();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * 
	 * @param rawResult
	 * @param barcode
	 */
	public void handleDecode(Result rawResult, Bitmap barcode) {
		inactivityTimer.onActivity();
		lastResult = rawResult;
		if (barcode == null) {
			handleDecodeInternally(rawResult, null);
		} else {
			playBeepSoundAndVibrate();
			drawResultPoints(barcode, rawResult);

			switch (source) {
			case NATIVE_APP_INTENT:
			case PRODUCT_SEARCH_LINK:
				handleDecodeExternally(rawResult, barcode);
				break;
			case ZXING_LINK:
				if (returnUrlTemplate == null) {
					handleDecodeInternally(rawResult, barcode);
				} else {
					handleDecodeExternally(rawResult, barcode);
				}
				break;
			case NONE: {
				handleDecodeInternally(rawResult, barcode);
			}
				break;
			}
		}
	}

	public String getLastResult() {
		return lastResult.getText();
	}

	private void drawResultPoints(Bitmap barcode, Result rawResult) {
		ResultPoint[] points = rawResult.getResultPoints();
		if (points != null && points.length > 0) {
			Canvas canvas = new Canvas(barcode);
			Paint paint = new Paint();
			paint.setColor(getResources().getColor(R.color.result_image_border));
			paint.setStrokeWidth(3.0f);
			paint.setStyle(Paint.Style.STROKE);
			Rect border = new Rect(2, 2, barcode.getWidth() - 2,
					barcode.getHeight() - 2);
			canvas.drawRect(border, paint);
			paint.setColor(getResources().getColor(R.color.result_points));
			if (points.length == 2) {
				paint.setStrokeWidth(4.0f);
				drawLine(canvas, paint, points[0], points[1]);
			} else if (points.length == 4
					&& (rawResult.getBarcodeFormat()
							.equals(BarcodeFormat.UPC_A))
					|| (rawResult.getBarcodeFormat()
							.equals(BarcodeFormat.EAN_13))) {
				drawLine(canvas, paint, points[0], points[1]);
				drawLine(canvas, paint, points[2], points[3]);
			} else {
				paint.setStrokeWidth(10.0f);
				for (ResultPoint point : points) {
					canvas.drawPoint(point.getX(), point.getY(), paint);
				}
			}
		}
	}

	private static void drawLine(Canvas canvas, Paint paint, ResultPoint a,
			ResultPoint b) {
		canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
	}

	/**
	 * @param rawResult
	 * @param barcode
	 */
	private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
		// TODO 结果
		viewfinderView.setVisibility(View.GONE);
		String content = rawResult.getText();
//		System.out.println("结果：" + content);
//		if (content.indexOf("http://")>= 0) {
//			Uri uri = Uri.parse(content);
//			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//			this.startActivity(intent);
//		}
//		BasicEventDispatcher.dispatcher(new BasicEvent(SCAN));
		Toast.makeText(this, content, Toast.LENGTH_LONG).show();
		Intent i=new Intent();
		Bundle b = new Bundle();  
        b.putString("result", content);  
        i.putExtras(b);  
        setResult(-1,i);  
		this.finish();
	}

	private void handleDecodeExternally(Result rawResult, Bitmap barcode) {
		viewfinderView.drawResultBitmap(barcode);
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(VIBRATE_DURATION);
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
			if (CameraManager.get().isPortrait()) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				topTV.setPadding(0, DensityUtil.dip2px(this, 80), 0, 0);
				bottomTV.setPadding(DensityUtil.dip2px(this, 10),
						DensityUtil.dip2px(this, 10), 0,
						DensityUtil.dip2px(this, 60));
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		} catch (IOException ioe) {
			displayFrameworkBugMessageAndExit();
			return;
		} catch (RuntimeException e) {
			displayFrameworkBugMessageAndExit();
			return;
		}
		if (captureHandler == null) {
			captureHandler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.account_tip));
		builder.setMessage(getString(R.string.msg_camera_framework_bug));
		builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

	private void resetStatusView() {
		viewfinderView.setVisibility(View.VISIBLE);
		lastResult = null;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}
}
