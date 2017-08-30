public class ThreadTest {
    static Object lock = new Object();
    static final int MAX_SIZE = 10;
    static int buffer[] = new int[MAX_SIZE];
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        Producer p1 = new Producer();
        Consumer c1 = new Consumer();


        Runnable  produceTask = () -> {
            for(int i = 0; i<50; i++) {
                System.out.println("Produce " + i);
                p1.produce();
            }
        };

        Runnable consumerTask = () -> {
            for(int i = 0; i<50; i++) {
                System.out.println("Consume " + i);
                c1.consume();
            }
        };
        Thread pThread = new Thread(produceTask);

        Thread cThread = new Thread(consumerTask);

        pThread.start();
        cThread.start();

        pThread.join();
        cThread.join();
//        //Create 50 threads and give them
//        Thread pThread[] = new Thread[10];
//        for(int i= 0 ;i< pThread.length; i++) {
//            pThread[i] = new Thread(produceTask);
//
//        }
//
//
//        //Create 50 threads and give them
//        Thread cThread[] = new Thread[10];
//        for(int i= 0 ;i< cThread.length; i++) {
//            cThread[i] = new Thread(consumerTask);
//        }
//
//        for(int i= 0 ;i< cThread.length; i++) {
//            pThread[i].start();
//            cThread[i].start();
//            pThread[i].join();
//            cThread[i].join();
//        }

    }

    static class Producer {
        void produce() {
            synchronized (lock) {
                if(isFull(buffer)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                buffer[count++] = 1;
                lock.notify();
            }
        }
    }

    static class Consumer {
        void consume() {
            synchronized (lock) {
                if(isEmpty(buffer)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                buffer[--count] = -1;
                lock.notify();
            }
        }
    }

    static boolean isEmpty(int[] buffer) {
        return count == 0;
    }

    static boolean isFull(int[] buffer) {
        return count == buffer.length;
    }
}