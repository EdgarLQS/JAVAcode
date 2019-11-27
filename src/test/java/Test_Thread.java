
import org.junit.Test;

import java.util.Scanner;

/**
 * 多线程每次运行的是 start 而不是 run ------> >>>> run 这个是多线程里面必须自己重写的方法
 */
public class Test_Thread {

    @Test
    public void testRunnableDemo(){
        RunnableDemo R1 = new RunnableDemo("Runnable-1");
        R1.start();
        R1.run();

        RunnableDemo R2 = new RunnableDemo("Runnable-2");
        R2.start();

    }

    @Test
    public void testThreadDemo(){

        // 问题是每次执行的结果为什么不一样
        ThreadDemo T1 = new ThreadDemo("Thread-1");
        ThreadDemo T2 = new ThreadDemo("Thread-2");
        T1.start();
        T2.start();

    }

    @Test
    public void testStationDemo(){
        StationDemo stationDemo1 = new StationDemo("窗口1");
        StationDemo stationDemo2 = new StationDemo("窗口2");
        StationDemo stationDemo3 = new StationDemo("窗口3");
        stationDemo1.start();
        stationDemo2.start();
        stationDemo3.start();
        stationDemo1.run();
        stationDemo2.run();
        stationDemo3.run();
    }

    public static void main(String[] args) {
        StationDemo stationDemo1 = new StationDemo("窗口1");
        StationDemo stationDemo2 = new StationDemo("窗口2");
        StationDemo stationDemo3 = new StationDemo("窗口3");
        stationDemo1.start();
        stationDemo2.start();
        stationDemo3.start();
    }

    @Test
    public void test11(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("输入值");

        if(scanner.hasNext()){
            System.out.println("===============");

            String s = scanner.next();
            System.out.println(s);
        }

        System.out.println("===============");

    }







}
