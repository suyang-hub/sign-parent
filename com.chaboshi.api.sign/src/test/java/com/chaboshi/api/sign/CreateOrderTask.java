/**
 * 
 */

package com.chaboshi.api.sign;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lee
 *
 */
public class CreateOrderTask extends Thread {

  private CountDownLatch countDownLatch;
  private CyclicBarrier barrier;
  private int size;

  public CreateOrderTask(CountDownLatch countDownLatch, CyclicBarrier barrier, int size) {
    this.countDownLatch = countDownLatch;
    this.barrier = barrier;
    this.size = size;
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();
    try {
      System.out.println(getName() + " 等待开始");
      barrier.await();
      System.out.println(getName() + " 开始...");
      while (size-- >= 0) {
        ReportService.getReport();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      countDownLatch.countDown();
      long end = System.currentTimeMillis();
      System.out.println(getName() + " 结束,耗时:" + (end - start) + "ms");
    }
  }
}
