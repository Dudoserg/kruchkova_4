import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
    }

    private static final Exchanger<Message> EXCHANGER = new Exchanger<>();

    public Main() {

        Dispatcher dispatcher = new Dispatcher(EXCHANGER,"dispatcher");
        Buyer buyer_1 = new Buyer(EXCHANGER, "buyer_1");
        Buyer buyer_2 = new Buyer(EXCHANGER, "buyer_2");
        Buyer buyer_3 = new Buyer(EXCHANGER, "buyer_3");

        List<Thread> list = new ArrayList<>();
        list.add(dispatcher);
        list.add(buyer_1);
        list.add(buyer_2);
        list.add(buyer_3);


        for(int i = 0 ; i < list.size(); i++){
            list.get(i).start();
        }

        System.out.println("end of MAIN");

    }
}
