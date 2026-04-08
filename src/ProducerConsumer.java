class SharedResource {
    int item;
    boolean available = false;

    // synchronized put method
    synchronized void put(int item) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        this.item = item;
        available = true;
        System.out.println("Produced: " + item);
        notify();
    }

    // synchronized get method
    synchronized void get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Consumed: " + item);
        available = false;
        notify();
    }
}

class Producer extends Thread {
    SharedResource resource;

    // Constructor
    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.put(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource resource;

    // Constructor
    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.get();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        // Create shared resource
        SharedResource obj = new SharedResource();

        // Create threads
        Producer p = new Producer(obj);
        Consumer c = new Consumer(obj);

        // Start threads
        p.start();
        c.start();
    }
}
