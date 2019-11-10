import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
    }

    private static final Exchanger<Message> EXCHANGER = new Exchanger<>();

    public static Semaphore semaphorePrint = new Semaphore(1,true);
    public static void print(String str){
        try {
            LocalTime localTime = LocalTime.now();
            String currentTime = "[ " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond() ;
            if(localTime.getHour() < 10)
                currentTime += " ";
            if(localTime.getMinute() < 10)
                currentTime += " ";
            if(localTime.getSecond() < 10)
                currentTime += " ";
            currentTime += " ]";
            semaphorePrint.acquire();
            System.out.println(currentTime + "  " + str);
            semaphorePrint.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public Main() {

        BlockingQueue<Message> blocking_Dispatcher_Buyer = new ArrayBlockingQueue<Message>(1, true);
        BlockingQueue<Message> blocking_Buyer_Dispatcher = new ArrayBlockingQueue<Message>(1, true);

        Person dispatcher = new Dispatcher(blocking_Dispatcher_Buyer, blocking_Buyer_Dispatcher, "dispatcher");
        Person buyer_1 = new Buyer(blocking_Buyer_Dispatcher, blocking_Dispatcher_Buyer, "buyer_1");



        // Список задач
        List<Runnable> list_Runnable = new ArrayList<>();
        list_Runnable.add(dispatcher);
        list_Runnable.add(buyer_1);

        // Список потоков
        List<Thread> list_Thread =list_Runnable.stream().map(runnable -> new Thread(runnable)).collect(Collectors.toList());

        // Запускаем потоки
        for (int i = 0; i < list_Thread.size(); i++) {
            list_Thread.get(i).start();
        }

        System.out.println("end of MAIN");

    }
}
