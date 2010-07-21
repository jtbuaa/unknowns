package hld.coins.animation;

import hld.coins.interfaces.sprite.Sprite;
import hld.coins.wrapper.Graphics;
import hld.coins.wrapper.Image;

//������ɫ��
public class Animator extends Sprite {
	private Calculagraph cal = null;

	public Animator(Image img, int frameWidth, int frameHeight, int loopTime,
			boolean isJoinViewManager) {
		super(img, frameWidth, frameHeight, isJoinViewManager);
		cal = new Calculagraph(loopTime);
	}

	public Animator(Image[] imgs, int loopTime, boolean isJoinViewManager) {
		super(imgs, isJoinViewManager);
		cal = new Calculagraph(loopTime);
	}

	/**
	 * ���Ŷ���
	 * 
	 */
	public void PlayAnimation(Graphics g) {
		if (cal.getLoopTime() > 0) {
			// �����ʱ�������¼�ʱ��������һFrame
			if (cal.isTimeout()) {
				cal.reset();
				this.nextFrame(current_mode);
				// paint(g,current_mode);
				onDraw(g);

			}
			// ���������ʱ
			else {
				cal.calculate();
				// paint(g,current_mode);
				onDraw(g);
			}
		}
	}

	/**
	 * ֹͣ���Ŷ���
	 * 
	 */
	public void StopAnimation() {
		cal.reset();
	}
}
