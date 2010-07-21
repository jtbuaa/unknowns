package hld.coins.task;

import java.util.Date;
import java.util.Timer;



/**
 * Java.util.Timer�Ĺ�����
 * 
 * @author Administrator
 * 
 */
public class AsyTimerManager {

	private static Timer timer;

	/**
	 * task ������һ���߳������еģ�ע���̰߳�ȫ
	 * 
	 * @param task
	 */
	public static void addTask(AsyTask task, Date when) {
		checkTimerNull();
		timer.schedule(task, when);
	}

	/**
	 * task ������һ���߳������еģ�ע���̰߳�ȫ
	 * 
	 * @param task
	 */
	public static void addTask(AsyTask task, long delay) {
		checkTimerNull();
		timer.schedule(task, delay);
	}

	/**
	 * task ������һ���߳������еģ�ע���̰߳�ȫ
	 * 
	 * @param task
	 * @param firstTime
	 * @param period
	 *            ѭ����ʱ����
	 */
	public static void addTask(AsyTask task, Date when, long period) {
		checkTimerNull();
		timer.schedule(task, when, period);
	}

	/**
	 * task ������һ���߳������еģ�ע���̰߳�ȫ
	 * 
	 * @param task
	 * @param delay
	 *            �ӳٶ���ʱ�������
	 * @param period
	 *            ѭ����ʱ����
	 */
	public static void addTask(AsyTask task, long delay, long period) {
		checkTimerNull();
		timer.schedule(task, delay, period);
	}

	/**
	 * <p>
	 * task ������һ���߳������еģ�ע���̰߳�ȫ��
	 * <p/>
	 * <p>
	 * �ڹ̶�����ִ���У������Ѱ��ŵĳ�ʼִ��ʱ��������ÿ��ִ�С���������κ�ԭ����ӳ���ĳ��ִ�У��򽫿��������س������λ�����ִ�У��Ӷ�ʹ����ִ���ܹ�
	 * ��׷����������
	 * <p/>
	 * 
	 * @param task
	 * @param firstTime
	 * @param period
	 *            ѭ����ʱ����
	 */
	public static void addFixedRateTask(AsyTask task, Date firstTime, long period) {
		checkTimerNull();
		timer.scheduleAtFixedRate(task, firstTime, period);
	}

	/**
	 * <p>
	 * task ������һ���߳������еģ�ע���̰߳�ȫ��
	 * <p/>
	 * <p>
	 * �ڹ̶�����ִ���У������Ѱ��ŵĳ�ʼִ��ʱ��������ÿ��ִ�С���������κ�ԭ����ӳ���ĳ��ִ�У��򽫿��������س������λ�����ִ�У��Ӷ�ʹ����ִ���ܹ�
	 * ��׷����������
	 * <p/>
	 * 
	 * @param task
	 * @param delay
	 *            �ӳٶ���ʱ�������
	 * @param period
	 *            ѭ����ʱ����
	 */
	public static void addFixedRateTask(AsyTask task, long delay, long period) {
		checkTimerNull();
		timer.scheduleAtFixedRate(task, delay, period);
	}

	private static void checkTimerNull() {
		if (timer == null) {
			timer = new Timer("GameTimer", true);
		}
	}
	
	public static void cancel(){
		if (timer != null) {
			timer.cancel();
		}
	}
	
	public static void purge(){
		if (timer != null) {
			timer.purge();
		}
	}

}
