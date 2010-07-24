package hld.coins.interfaces;

import hld.coins.manager.GameViewManager;
import hld.coins.manager.PreferencesManager;
import hld.coins.wrapper.Graphics;
import android.content.Intent;
import android.view.MotionEvent;

public abstract class AbstractView implements ViewInterface {

	private boolean isShowing;
	protected PreferencesManager preferences;
	protected static int height;
	protected static int width;
	protected static double scale;
	private static int startX;
	private static int startY;
	private static boolean isInit;

	public AbstractView(boolean isJoinViewManager) {
		if (isJoinViewManager)
			GameViewManager.getInstance().addView(this);
		if (!isInit) {
			preferences = PreferencesManager.getInstance();
			scale = GameViewManager.getInstance().scale;
			startX = GameViewManager.getInstance().startX;
			startY = GameViewManager.getInstance().startY;
			isInit = true;
		}
	}

	@Override
	public void hide() {
		isShowing = false;
	}

	@Override
	public void onDraw(Graphics graphics) {

	}

	@Override
	public void onIntentListener(Intent intent) {

	}

	@Override
	public boolean onTouchListener(MotionEvent event) {
		return false;
	}

	@Override
	public void show() {

	}

	/**
	 * 供{#GameViewManager}画图前检查VIew是否已经被打开
	 */
	public void checkShowing() {
		if (!isShowing)
			show();
		isShowing = true;
	}

	protected final int offsetX(int number) {
		return (int) (startX + (number * scale));
	}

	protected final int offsetY(int number) {
		return (int) (startY + (number * scale));
	}

	protected final int offsetW(int number) {
		return (int) (number * scale);
	}

	protected final int offsetH(int number) {
		return (int) (number * scale);
	}

}
