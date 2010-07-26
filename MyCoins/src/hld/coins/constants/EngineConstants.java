package hld.coins.constants;

import android.graphics.Bitmap;

public class EngineConstants {

	// ��Ϸ����ˢ������
	public static final int STEP = 30;
	// Ĭ����Ϸ�ı�׼��С
	public static final float GAME_WIDTH = 480.0f;
	public static final float GAME_HEIGHT = 320.0f;
	// �Ƿ���ʾDebug��Ϣ
	public static boolean isShowDebugText = true;

	public static final String GAME_DISPATCHER = "dispatcher";
	public static final int GAME_MUSIC = 100;

	// �˳���Ϸ
	public static final String GAME_EXIT_OK = "������";
	public static final String GAME_EXIT_CANCEL = "����һ�°�";
	public static final String GAME_EXIT_TITLE = "�뿪";
	public static final String GAME_EXIT_MSG = "����Ĳ�����ô��";
	
	/**
	 * �Ƿ�������
	 */
	public static final String IS_OPEN_SOUND = "openSound";
	/**
	 * �Ƿ���������Ĭ��ѡ��
	 */
	public static final boolean DEFAULT_OPEN_SOUND = true;
	/**
	 * �Ƿ���ʾ������ʾ
	 */
	public static final String IS_SHOW_HELP = "showHelp";
	/**
	 * �Ƿ���ʾ������ʾ��Ĭ��ѡ��
	 */
	public static final boolean DEFAULT_SHOW_HELP = true;
	
	/**
	 * Bitmap.Config.ARGB_8888
	 */
	public static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	/**
	 * Bitmap.Config.RGB_565
	 */
	public static final Bitmap.Config FAST_BITMAP_CONFIG = Bitmap.Config.RGB_565;

}
