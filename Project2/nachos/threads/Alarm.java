package nachos.threads;

import java.util.LinkedList;

import javax.crypto.Mac;

import nachos.machine.*;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
	/**
	 * Allocate a new Alarm. Set the machine's timer interrupt handler to this
	 * alarm's callback.
	 * 
	 * <p>
	 * <b>Note</b>: Nachos will not function correctly with more than one alarm.
	 */
	private static LinkedList<KThread> sleepQ = new LinkedList<KThread>();
	
	public Alarm() {
		Machine.timer().setInterruptHandler(new Runnable() {
			public void run() {
				timerInterrupt();
			}
		});
	}

	/**
	 * The timer interrupt handler. This is called by the machine's timer
	 * periodically (approximately every 500 clock ticks). Causes the current
	 * thread to yield, forcing a context switch if there is another thread that
	 * should be run.
	 */
	public void timerInterrupt(){

		Machine.interrupt().disable();
		
		if(!sleepQ.isEmpty()){
			for(int i=0; i < sleepQ.size(); i++){ 
				KThread temp = sleepQ.get(i);
				if(temp.wakeTime() <= Machine.timer().getTime()){
					sleepQ.remove(i);
					temp.ready();
				}
			}
		}
		
		KThread.currentThread().yield();
		
		Machine.interrupt().enable();
		
	}

	/**
	 * Put the current thread to sleep for at least <i>x</i> ticks, waking it up
	 * in the timer interrupt handler. The thread must be woken up (placed in
	 * the scheduler ready set) during the first timer interrupt where
	 * 
	 * <p>
	 * <blockquote> (current time) >= (WaitUntil called time)+(x) </blockquote>
	 * 
	 * @param x the minimum number of clock ticks to wait.
	 * 
	 * @see nachos.machine.Timer#getTime()
	 */
	public void waitUntil(long x) {
		// for now, cheat just to get something working (busy waiting is bad)
		Machine.interrupt().disable();
		KThread currThread = KThread.currentThread();
		long wakeTime = Machine.timer().getTime() + x;
	/*	while (wakeTime > Machine.timer().getTime())
			KThread.yield();
	*/	
		currThread.setWakeTime(wakeTime);
		sleepQ.add(currThread);
		currThread.sleep();
		
		Machine.interrupt().enable();
	}
	
}