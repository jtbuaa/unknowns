package com.yingzheng.android.activity;

import java.util.concurrent.TimeUnit;

import com.yingzheng.android.constants.EngineConstants;
import com.yingzheng.android.manager.BitmapManager;
import com.yingzheng.android.manager.GameStatusManger;
import com.yingzheng.android.manager.GameViewManager;
import com.yingzheng.android.module.music.AsyGameMusicPlayer;
import com.yingzheng.android.task.AsyTimerManager;
import com.yingzheng.android.wrapper.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameCanvas extends SurfaceView implements Callback, Runnable {

	private volatile static boolean isRunning;
	private volatile static boolean isPause;
	private volatile static boolean isExitGame;

	private SurfaceHolder holder;
	private static boolean isFirstEnterGame;
	private Graphics graphics;
	private static Handler gameHandler;
	private AsyGameMusicPlayer musicPlayer;
	private GameViewManager viewManager;

	// test
	private int totel;
	private int count;
	private int step;
	private long start;
	private long end;
	private long usedTime;

	public GameCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		holder.addCallback(this);
		isFirstEnterGame = true;
		graphics = new Graphics();
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ������
		musicPlayer = new AsyGameMusicPlayer();
		viewManager = GameViewManager.getInstance();
		gameHandler = new Handler();
	}

	private void test() {
		totel = 0;
		count = 0;
		step = 0;
		start = 0;
		end = 0;
		usedTime = 0;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		init();
		// ����
		test();
		// ��Ⱦ �¼� ������
		gameHandler.post(this);

	}

	/**
	 * ��Ϸ��ʼ������
	 */
	private void init() {
		if (isFirstEnterGame) {
			//��һ�ν�����Ϸ��ֻ�ǳ�ʼ����Ϸ״̬
			Canvas c = null;
			while (c == null) {
				try {
					c = holder.lockCanvas();
					synchronized (holder) {
						if (c == null)
							continue;
						GameViewManager instance = GameViewManager
								.getInstance();
						instance.ScreenHight = c.getHeight();
						instance.ScreenWidth = c.getWidth();
						instance.adjust();
						// TODO
						// ��Ϸ״̬�ĳ�ʼ��
					}
				} finally {
					if (c != null)
						holder.unlockCanvasAndPost(c);
				}
				isFirstEnterGame = false;
				// �첽����
				gameHandler.postDelayed(musicPlayer, 500);
			}
		}
		isRunning= true;

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	@Override
	public void run() {
		Looper.prepare();
		musicPlayer.resumeAllMusic();
		while (isRunning) {
			while (isPause && isRunning) {
				try {
					TimeUnit.MILLISECONDS.sleep(EngineConstants.STEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			update();
		}
		if(isExitGame){
			viewManager.removeAllView();
			BitmapManager.getInstance().releaseAllImage();
			AsyTimerManager.cancel();
			musicPlayer.stopAllMusic();
			android.os.Process.killProcess(android.os.Process.myPid());
		}else{
			musicPlayer.puaseAllMusic();
		}
		Looper.loop();
	}

	private void update() {
		start = System.currentTimeMillis();
		updateState();
		updateInput();
		updateAI();
		updatePhysics();
		updateAnimations();
		updateSound();
		updateVideo();
		sleep();
	}

	private void updateState() {
		GameStatusManger.getInstance().update();
	}

	private void updateInput() {
		GameViewManager.getInstance().onIntentListener();
		GameViewManager.getInstance().onTouchListener();
	}

	private void updateAI() {
	}

	private void updatePhysics() {
	}

	private void updateAnimations() {
		Canvas c = null;
		try {
			c = holder.lockCanvas();
			synchronized (holder) {
				if (c != null) {
					graphics.setCanvas(c);
					viewManager.onDraw(graphics);
					graphics.drawString("Step : " + step, 0, 0, Graphics.LEFT
							| Graphics.TOP);
				}
			}
		} finally {
			if (c != null)
				holder.unlockCanvasAndPost(c);
		}
	}

	private void updateSound() {
	}

	private void updateVideo() {
	}
	
	private void sleep() {
		end = System.currentTimeMillis();
		usedTime = end - start;
		totel += usedTime < EngineConstants.STEP ? EngineConstants.STEP
				: usedTime;
		count++;
		if (totel >= 1000) {
			step = count;
			totel = 0;
			count = 0;
		}
		usedTime = System.currentTimeMillis() - usedTime;
		if (usedTime < EngineConstants.STEP) {
			try {
				TimeUnit.MILLISECONDS.sleep(EngineConstants.STEP - usedTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	public static boolean isExitGame() {
		return isExitGame;
	}

	public static void setExitGame(boolean isExitGame) {
		GameCanvas.isExitGame = isExitGame;
		if(isExitGame){
			GameCanvas.isRunning = false;
		}
	}

	public static boolean isRunning() {
		return isRunning;
	}

//	public static void setRunning(boolean isRunning) {
//		GameCanvas.isRunning = isRunning;
//	}

	public static boolean isPause() {
		return isPause;
	}

	public static void setPause(boolean isPause) {
		GameCanvas.isPause = isPause;
	}
}