package test.barBean;

import java.io.Serializable;

/**
 * �����������һ��TaskBean�࣬��ʵ��java.lang.Runnable�ӿڣ�
 * ��run()������һ����JSPҳ�棨start.jsp�������Ķ����߳������С�
 * ��ֹrun()����ִ������һ��JSPҳ��stop.jsp����
 * http://blog.knowsky.com/
 * TaskBean�໹ʵ����java.io.Serializable�ӿڣ�
 * ����JSPҳ��Ϳ��Խ�����ΪJavaBean����
 * */
public class TaskBean
    implements Runnable, Serializable {

  private int counter;

  private int sum;

  private boolean started;

  private boolean running;

  private int sleep;

  public TaskBean() {

    counter = 0;

    sum = 0;

    started = false;

    running = false;

    sleep = 100;

  }
  /**
   * TaskBean�����ġ����������Ǽ���1+2+3��+100��ֵ��
   * ��������ͨ��100*(100+1)/2=5050��ʽ���㣬������run()����
   * ����work()����100����ɼ��㡣work()�����Ĵ���������ʾ��
   * ���е���Thread.sleep()��Ϊ��ȷ�������ܺ�ʱԼ10�롣
   * */
  protected void work() {

    try {

      Thread.sleep(sleep);

      counter++;

      sum += counter;

    }
    catch (InterruptedException e) {
      setRunning(false);

    }

  }
  //status.jspҳ��ͨ�����������getPercent()���������������״����
  public synchronized int getPercent() {

    return counter;

  }
  //��������Ѿ�������isStarted()����������true��
  public synchronized boolean isStarted() {

    return started;

  }
  //��������Ѿ���ɣ�isCompleted()����������true
  public synchronized boolean isCompleted() {

    return counter == 100;

  }
  //��������������У�isRunning()����������true��
  public synchronized boolean isRunning() {

    return running;

  }
  /**
   * SetRunning()������start.jsp��stop.jsp���ã�
   * ��running������trueʱ��SetRunning()������Ҫ��������Ϊ���Ѿ���������
   * ����setRunning(false)��ʾҪ��run()����ִֹͣ�С�
   * */
  public synchronized void setRunning(boolean running) {

    this.running = running;

    if (running) {

      started = true;
    }

  }
  //����ִ����Ϻ󣬵���getResult()�������ؼ����������������δִ����ϣ�������null��
  public synchronized Object getResult() {

    if (isCompleted()) {

      return new Integer(sum);
    }

    else {

      return null;
    }

  }
  /**
   * ��running���Ϊtrue��completed���Ϊfalseʱ��
   * run()��������work()����ʵ��Ӧ���У�run()����Ҳ��Ҫ
   * ִ�и��ӵ�SQL��ѯ����������XML�ĵ������ߵ������Ĵ���
   * CPUʱ���EJB������ע�⡰���ص����񡱿���Ҫ��Զ�̷�����
   * ��ִ�С���������JSPҳ��������ѡ�񣺻��ߵȴ��������������ʹ��һ����������
   * */
  public void run() {

    try {

      setRunning(true);

      while (isRunning() && !isCompleted()) {

        work();
      }

    }
    finally {

      setRunning(false);

    }

  }

}
