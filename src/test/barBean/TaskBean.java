package test.barBean;

import java.io.Serializable;

/**
 * 首先我们设计一个TaskBean类，它实现java.lang.Runnable接口，
 * 其run()方法在一个由JSP页面（start.jsp）启动的独立线程中运行。
 * 终止run()方法执行由另一个JSP页面stop.jsp负责。
 * http://blog.knowsky.com/
 * TaskBean类还实现了java.io.Serializable接口，
 * 这样JSP页面就可以将它作为JavaBean调用
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
   * TaskBean包含的“繁重任务”是计算1+2+3…+100的值，
   * 不过它不通过100*(100+1)/2=5050公式计算，而是由run()方法
   * 调用work()方法100次完成计算。work()方法的代码如下所示，
   * 其中调用Thread.sleep()是为了确保任务总耗时约10秒。
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
  //status.jsp页面通过调用下面的getPercent()方法获得任务的完成状况：
  public synchronized int getPercent() {

    return counter;

  }
  //如果任务已经启动，isStarted()方法将返回true：
  public synchronized boolean isStarted() {

    return started;

  }
  //如果任务已经完成，isCompleted()方法将返回true
  public synchronized boolean isCompleted() {

    return counter == 100;

  }
  //如果任务正在运行，isRunning()方法将返回true：
  public synchronized boolean isRunning() {

    return running;

  }
  /**
   * SetRunning()方法由start.jsp或stop.jsp调用，
   * 当running参数是true时。SetRunning()方法还要将任务标记为“已经启动”。
   * 调用setRunning(false)表示要求run()方法停止执行。
   * */
  public synchronized void setRunning(boolean running) {

    this.running = running;

    if (running) {

      started = true;
    }

  }
  //任务执行完毕后，调用getResult()方法返回计算结果；如果任务尚未执行完毕，它返回null：
  public synchronized Object getResult() {

    if (isCompleted()) {

      return new Integer(sum);
    }

    else {

      return null;
    }

  }
  /**
   * 当running标记为true、completed标记为false时，
   * run()方法调用work()。在实际应用中，run()方法也许要
   * 执行复杂的SQL查询、解析大型XML文档，或者调用消耗大量
   * CPU时间的EJB方法。注意“繁重的任务”可能要在远程服务器
   * 上执行。报告结果的JSP页面有两种选择：或者等待任务结束，或者使用一个进度条。
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
