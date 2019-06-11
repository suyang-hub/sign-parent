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
public class Main {

  public static void main(String[] args) throws InterruptedException {
    int concurrentSize = 1;
    CountDownLatch countDownLatch = new CountDownLatch(concurrentSize);
    CyclicBarrier barrier = new CyclicBarrier(concurrentSize);
    long start = System.currentTimeMillis();
    while (concurrentSize-- > 0) {
      new CreateOrderTask(countDownLatch, barrier, 1).start();
    }
    countDownLatch.await();
    long end = System.currentTimeMillis();
    System.out.println("所有线程执行结束,耗时:" + (end - start) + "ms");
  }

}
