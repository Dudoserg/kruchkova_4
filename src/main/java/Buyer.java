import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Buyer extends  Person implements Runnable{


    BlockingQueue<Message> blocking_Buyer_Dispatcher;

    BlockingQueue<Message> blocking_Dispatcher_Buyer;

    public Buyer(BlockingQueue<Message> blocking_Buyer_Dispatcher, BlockingQueue<Message> blocking_Dispatcher_Buyer, String name) {
        super(PersonType.BUYER, name);
        this.blocking_Dispatcher_Buyer = blocking_Dispatcher_Buyer;
        this.blocking_Buyer_Dispatcher = blocking_Buyer_Dispatcher;

        List<Integer> asd = new ArrayList<Integer>(){{
            add(21);
        }} ;
    }



    @Override
    public void run() {
        while (true) {
            try {

                sleep((int) (Math.random() * 1000 * 10));

                blocking_Buyer_Dispatcher.put(new Message( this, "pizza"));

                final Message take = blocking_Dispatcher_Buyer.take();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
