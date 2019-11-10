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
        //while (true) {
            try {
                Main.print(super.getPersonName() + " ждет какое либо сообщение");
                Message messageTaked = blocking_Buyer_Dispatcher.take();

                switch (messageTaked.getPerson().getPersonType()){
                    case BUYER:{
                        Main.print(super.getPersonName() + " получил сообщение от покупателя " + messageTaked.getPerson().getPersonName() );

                        Main.print(super.getPersonName() + " диспетчер обрабатывает заказ в течении 5 секунд....");
                        sleep((int) (1000 * 5));    //      sleep((int) (Math.random() * 1000 * 5));
                        Main.print(super.getPersonName() + "диспетчер обработал заказ");

                        Message messageForBuyer = new Message(this, "пицца готовая()");
                        Main.print(super.getPersonName() + "диспетчер отвечает покупателю");
                        blocking_Dispatcher_Buyer.put(messageForBuyer);


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
        //}
    }
}
