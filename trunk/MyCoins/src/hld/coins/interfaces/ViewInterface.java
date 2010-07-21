package hld.coins.interfaces;

import hld.coins.wrapper.Graphics;

import android.content.Intent;
import android.view.MotionEvent;

public interface ViewInterface {

	public void onDraw(Graphics graphics);

	/**
	 * 
	 * @param event
	 * @return <p>
	 *         true : ���������ݵ���һ��View
	 *         </p>
	 *         <p>
	 *         false : ������
	 *         </p>
	 */
	public boolean onTouchListener(MotionEvent event);

	public void onIntentListener(Intent intent);

	/**
	 * ÿ��View����ʱ������
	 */
	public void show();

	/**
	 * ÿ��View���ر�ʱ������
	 */
	public void hide();

}
