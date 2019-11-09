import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
    }

    private static final Exchanger<Message> EXCHANGER = new Exchanger<>();

    public Main() {

        BlockingQueue<Message> blocking_Dispatcher_Buyer = new ArrayBlockingQueue<Message>(1, true);
        BlockingQueue<Message> blocking_Buyer_Dispatcher = new ArrayBlockingQueue<Message>(1, true);


        Dispatcher dispatcher = new Dispatcher(blocking_Dispatcher_Buyer, blocking_Buyer_Dispatcher, "dispatcher");
        FF buyer_1 = new Buyer(blocking_Buyer_Dispatcher, blocking_Dispatcher_Buyer, "buyer_1");




        List<Thread> list = new ArrayList<>();
        list.add(dispatcher);
        list.add(buyer_1);


        for (int i = 0; i < list.size(); i++) {
            list.get(i).start();
        }

        System.out.println("end of MAIN");

    }
}
