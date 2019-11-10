import java.util.concurrent.BlockingQueue;

public class Cook extends Person implements Runnable {

    BlockingQueue<Message> blocking_Cook_Dispatcher;

    BlockingQueue<Message> blocking_Dispatcher_Cook;

    public Cook(BlockingQueue<Message> blocking_Cook_Dispatcher,BlockingQueue<Message> blocking_Dispatcher_Cook, String personName) {
        super(PersonType.COOK, personName);
        this.blocking_Cook_Dispatcher = blocking_Cook_Dispatcher;
        this.blocking_Dispatcher_Cook = blocking_Dispatcher_Cook;
    }

    @Override
    public void run() {
        //while (true){
        try {

            final Message messageFromDispatcher = blocking_Dispatcher_Cook.take();
            

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }
}
