package Concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mutex_SecurityGate {
    private boolean occupied = false;  // true if security gate is occupied
    private Lock mutex = new ReentrantLock(); 
    private Condition entering = mutex.newCondition(); 
    private Condition leaving = mutex.newCondition();
    
    public void enterArea() throws InterruptedException {
        mutex.lock();
        try{
            while(occupied){
                entering.await();
            }
            //entering bla bla
        }finally{
            mutex.unlock();
        }
    }
 
    public void leaveArea() throws InterruptedException {
        mutex.lock();
        try{
            while(occupied){
                leaving.await();
            }
            //leave bla bla
            gateEmpty();
        }finally{
            mutex.unlock();
        }
    }
    
    public void gateEmpty() {
        occupied = false;
        leaving.signal();
        entering.signal();
    }
}