package hld.coins.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * �������������õ���Canvas���߳����У��̲߳���ȫ,������׼ȷ���뻭��ˢ��Step�й�ϵ
 * @author Mark
 */
public class SynTimerManager {
	private ArrayList<SynTask> list = new ArrayList<SynTask>();
	private static SynTimerManager instance;

	/**
	 * 
	 * @param task
	 */
	public void addTask(SynTask task, Date when) {
		if (when.getTime() < 0) {
			throw new IllegalArgumentException();
		}
		long delay = when.getTime() - System.currentTimeMillis();
		scheduleImpl(task, delay < 0 ? 0 : delay, -1);
	}

	/**
	 * 
	 * @param task
	 */
	public void addTask(SynTask task, long delay) {
		if (delay < 0) {
			throw new IllegalArgumentException();
		}
		scheduleImpl(task, delay, -1);
	}

	/**
	 * 
	 * @param task
	 * @param firstTime
	 * @param period
	 *            ѭ����ʱ����
	 */
	public void addTask(SynTask task, Date when, long period) {
		if (period <= 0 || when.getTime() < 0) {
			throw new IllegalArgumentException();
		}
		long delay = when.getTime() - System.currentTimeMillis();
		scheduleImpl(task, delay < 0 ? 0 : delay, period);
	}

	/**
	 * 
	 * @param task
	 * @param delay
	 *            �ӳٶ���ʱ�������
	 * @param period
	 *            ѭ����ʱ����
	 */
	public void addTask(SynTask task, long delay, long period) {
		if (delay < 0 || period <= 0) {
			throw new IllegalArgumentException();
		}
		scheduleImpl(task, delay, period);
	}

	private void scheduleImpl(SynTask task, long delay, long period) {
		task.when = delay;
		task.period = period;
		list.add(task);
	}

	public void run() {
		long currentTime = System.currentTimeMillis();
		Iterator<SynTask> iterator = list.iterator();
		while (iterator.hasNext()) {
			SynTask task = iterator.next();
			if (task.cancelled) {
				iterator.remove();
				continue;
			}
			long timeToSleep = task.when - currentTime;
			if (timeToSleep > 0) {
				continue;
			}
			task.setScheduledTime(currentTime);
			task.run();
			if (task.period == -1) {
				iterator.remove();
				continue;
			}
			task.when = currentTime + task.period;
		}
	}

	public static SynTimerManager getInstance() {
		if (instance == null) {
			instance = new SynTimerManager();
		}
		return instance;
	}

}
