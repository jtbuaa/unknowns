package hld.coins.task;

import java.util.TimerTask;

public class AsyTask extends TimerTask {

	public boolean isPause;

	/**
	 * ��д�˷�����һ����Ҫ���ø���Ĵ˷���
	 */
	@Override
	public void run() {
		if (isPause) {
			return;
		}
	}
}
