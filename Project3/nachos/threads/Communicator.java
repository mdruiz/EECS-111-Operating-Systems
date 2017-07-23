package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>, and multiple
 * threads can be waiting to <i>listen</i>. But there should never be a time
 * when both a speaker and a listener are waiting, because the two threads can
 * be paired off at this point.
 */
public class Communicator {
	/**
	 * Allocate a new communicator.
	 */
	                 
	private int ready;  
    private Lock lock;                    
    private Condition2 speakerCondition, listenerCondition;       
    private int numListener,numSpeaker = 0;                         
    private int word = 0; 
	
	public Communicator() {
		
		this.ready = 0;
        this.lock = new Lock(); 
        this.speakerCondition  = new Condition2(lock);
        this.listenerCondition = new Condition2(lock);
		
	}

	/**
	 * Wait for a thread to listen through this communicator, and then transfer
	 * <i>word</i> to the listener.
	 * 
	 * <p>
	 * Does not return until this thread is paired up with a listening thread.
	 * Exactly one listener should receive <i>word</i>.
	 * 
	 * @param word the integer to transfer.
	 */
	public void speak(int word) {
		
		lock.acquire();
		numSpeaker = numSpeaker + 1;
		
		while (numListener == 0 || ready == 1 ) { 
            speakerCondition.sleep();  
        }
	
		this.word = word;         
        ready = 1;       
        listenerCondition.wakeAll();     
        numSpeaker = numSpeaker - 1;                
        lock.release(); 
	}

	/**
	 * Wait for a thread to speak through this communicator, and then return the
	 * <i>word</i> that thread passed to <tt>speak()</tt>.
	 * 
	 * @return the integer transferred.
	 */
	public int listen() {
		int tempWord;
        lock.acquire();          
        numListener = numListener + 1;             
        while(ready == 0) {     
            speakerCondition.wakeAll();
            listenerCondition.sleep();       
        }        
        tempWord = this.word; 
        ready = 0;      
        numListener = numListener - 1;             
        lock.release();    
        return tempWord;   
	}
}
