import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class main {

    static Object test = 1;
    static List<String> stringList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        new ThreadA().start();
        new ThreadB().start();
    }
    static class ThreadA extends Thread {

        @Override
        public void run(){
            Scanner scanner = new Scanner(System.in);
            while(true) {
                synchronized (test) {
                    System.out.println("ThreadA  START CYNC");
                    System.out.println("ThreadA ВВОДИТ ЧИСЛО");
                    stringList.add(scanner.nextLine());
                    System.out.println("ThreadA notify");
                    test.notify();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    static class ThreadB extends Thread {

        @Override
        public void run(){

            while(stringList.isEmpty()) {
                System.out.println("ThreadB ВЕЙТИТ CYNC");

                synchronized (test) {
                    System.out.println("ThreadB START CYNC");
                    try{
                        System.out.println("ThreadB ВЕЙТИТ");
                        test.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("ThreadB :");
                    System.out.println(stringList.remove(0));

                }
            }
        }
    }

}
