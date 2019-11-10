import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Buyer extends Person implements Runnable {


    BlockingQueue<Message> blocking_Buyer_Dispatcher;

    BlockingQueue<Message> blocking_Dispatcher_Buyer;

    public Buyer(BlockingQueue<Message> blocking_Buyer_Dispatcher, BlockingQueue<Message> blocking_Dispatcher_Buyer, String name) {
        super(PersonType.BUYER, name);
        this.blocking_Dispatcher_Buyer = blocking_Dispatcher_Buyer;
        this.blocking_Buyer_Dispatcher = blocking_Buyer_Dispatcher;
    }


    @Override
    public void run() {
        //while (true) {
            try {


                Main.print(super.getPersonName() + " засыпает на 10 секунд...");
                sleep((int) (1000 * 10));
                Main.print(super.getPersonName() + " окончил спать");


                Message messageForDispatcher = new Message(this,"пицца");

                Main.print(super.getPersonName() + " посылает сообщение диспетчеру" );
                blocking_Buyer_Dispatcher.put(messageForDispatcher);

                Main.print(super.getPersonName() + " ожидает ответа от Диспетчера...." );
                final Message take = blocking_Dispatcher_Buyer.take();
                Main.print(super.getPersonName() + " получил ответ от диспетчера. Ответ = " + take.getMessage() );



            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        //}
    }
}
