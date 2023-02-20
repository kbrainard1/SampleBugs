import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Example2 {
	
	private static final int NUM_WORK_THREADS = 5;
	
	private BlockingQueue<Integer> workQueue = new ArrayBlockingQueue<>(20);
	private AtomicLong sum; 
	private volatile boolean waitForWork = true;
	
	// Takes work from the queue and processes it. Must be interrupted to terminate
	private class Worker implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			while (!workQueue.isEmpty() || waitForWork) {
				Integer workUnit = workQueue.poll(1, TimeUnit.SECONDS);

				if (workUnit != null) {
					long prevSum = sum.get();
					sum.set(prevSum + workUnit*workUnit);
				}
			}
			return null;
		}
	}
	
	// Populates the work queue, waiting as needed if the queue fills up
	private class Reader implements Callable<Void> {
		
		private final int sumUpTo;
		
		public Reader(int sumUpTo) {
			this.sumUpTo = sumUpTo;
		}

		@Override
		public Void call() throws Exception {
			for (int i = 1; i <= sumUpTo; i++) {
				workQueue.put(i);
			}
			return null;
		}
	}
		
	public long findSumOfSquares(int sumUpTo) throws Exception {
		return findSumOfSquares(sumUpTo, NUM_WORK_THREADS);
	}
	
	public synchronized long findSumOfSquares(int sumUpTo, int numWorkThreads) throws Exception {
		// Reset from previous runs
		sum = new AtomicLong();
		waitForWork = true;
		
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < numWorkThreads; i++) {
			threadPool.submit(new Worker());
		}
		
		Future<Void> readerThread = threadPool.submit(new Reader(sumUpTo));
		readerThread.get();
		
		// alert the worker threads that the queue won't get any more elements added
		waitForWork = false;

		threadPool.shutdown();
		threadPool.awaitTermination(10, TimeUnit.SECONDS);
		return sum.get();
	}
	
	protected void testHook() {
		// only implemented by test code
	}

}
