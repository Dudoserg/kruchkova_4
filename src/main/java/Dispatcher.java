import java.util.concurrent.BlockingQueue;

//@Data
public class Dispatcher extends  Person implements Runnable{


    BlockingQueue<Message> blocking_Buyer_Dispatcher;
    BlockingQueue<Message> blocking_Dispatcher_Buyer;


    public Dispatcher(BlockingQueue<Message> blocking_Dispatcher_Buyer , BlockingQueue<Message> blocking_Buyer_Dispatcher, String name) {
        super(PersonType.BUYER, name);
        this.blocking_Dispatcher_Buyer = blocking_Dispatcher_Buyer;
        this.blocking_Buyer_Dispatcher = blocking_Buyer_Dispatcher;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Message take = blocking_Buyer_Dispatcher.take();
                switch (take.getPerson().getPersonType()){
                    case BUYER:{
                        break;
                    }
                    case COOK:{
                        break;
                    }
                    case COURIER:{
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
