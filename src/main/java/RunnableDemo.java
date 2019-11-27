

// 创建一个线程，最简单的方法是创建一个实现 Runnable 接口的类。
// 为了实现 Runnable，一个类只需要执行一个方法调用 run()
class RunnableDemo implements Runnable {

    private Thread t;
    private String threadName;

    RunnableDemo( String name) {
        threadName = name;
//        System.out.println("Creating " +  threadName );
    }

    // 调用线程就需要重写run
    public void run() {
//        System.out.println("Running " +  threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
        }
//        System.out.println("Thread " +  threadName + " exiting.");
    }

    // 线程创建之后，你调用它的 start() 方法它才会运行。
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}