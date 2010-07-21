package com.yingzheng.android.constants;

import android.graphics.Bitmap;

public class EngineConstants {

	// ��Ϸ����ˢ������
	public static final int STEP = 30;
	// Ĭ����Ϸ�ı�׼��С
	public static final float GAME_WIDTH = 320.0f;
	public static final float GAME_HEIGHT = 480.0f;
	// �Ƿ���ʾDebug��Ϣ
	public static boolean isShowDebugText = true;

	public static final String GAME_DISPATCHER = "dispatcher";
	public static final int GAME_MUSIC = 100;

	// �˳���Ϸ
	public static final String GAME_EXIT_OK = "ok";
	public static final String GAME_EXIT_CANCEL = "cancel";
	public static final String GAME_EXIT_TITLE = "�˳�";
	public static final String GAME_EXIT_MSG = "ȷ���˳���Ϸ��";
	
	/**
	 * Bitmap.Config.ARGB_8888
	 */
	public static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	/**
	 * Bitmap.Config.RGB_565
	 */
	public static final Bitmap.Config FAST_BITMAP_CONFIG = Bitmap.Config.RGB_565;

}
