package Persons;

import Message.Message_Base;
import Start.Main;
import kek.Person;
import kek.PersonType;

import java.util.concurrent.BlockingQueue;

//@Data
public class Dispatcher extends Person implements Runnable {


    BlockingQueue<Message_Base> blocking_To_Dispatcher;
    BlockingQueue<Message_Base> blocking_To_Buyer;
    BlockingQueue<Message_Base> blocking_To_Cook;


    public Dispatcher(BlockingQueue<Message_Base> blocking_To_Dispatcher, BlockingQueue<Message_Base> blocking_To_Buyer, BlockingQueue<Message_Base> blocking_To_Cook, String name) {
        super(PersonType.BUYER, name);
        this.blocking_To_Buyer = blocking_To_Buyer;
        this.blocking_To_Dispatcher = blocking_To_Dispatcher;
        this.blocking_To_Cook = blocking_To_Cook;
    }

    Buyer byuer;

    public void setByuer(Buyer byuer) {
        this.byuer = byuer;
    }

    @Override
    public void run() {
        //while (true) {
        try {
            synchronized (this) {
//                    Start.Main.print(super.getPersonName() + " засыпает на 10 секунд...");
//                    sleep((int) (1000 * 1));
//                    Start.Main.print(super.getPersonName() + " окончил спать");
//                    Start.Main.print(super.getPersonName() + " будит покупателя");
//                    sleep((int) (1000 * 1));
//                    byuer.wakeUpSamurai();

                Main.print(super.getPersonName() + " ждет какое либо сообщение...");
                Message_Base messageBaseTaked = blocking_To_Dispatcher.take();

                switch (messageBaseTaked.getPerson().getPersonType()) {
                    case BUYER: {
                        Main.print(super.getPersonName() + " получил сообщение от покупателя " + messageBaseTaked.getPerson().getPersonName());
                        sleep(1500);
                        Main.print(super.getPersonName() + " диспетчер обрабатывает заказ в течении 5 секунд....");
                        sleep((int) (1000 * 5));    //      sleep((int) (Math.random() * 1000 * 5));
                        blocking_To_Cook.put(new Message_Base(this, messageBaseTaked.getMessage()));
                        Main.print(super.getPersonName() + "диспетчер обработал заказ, передав его повару");
                        sleep(1500);
//                            Message_Base.Message_Base messageForBuyer = new Message_Base.Message_Base(this, "пицца готовая()");
//                            Start.Main.print(super.getPersonName() + "диспетчер отвечает покупателю");
//                            blocking_To_Buyer.put(messageForBuyer);

                        break;
                    }
                    case COOK: {
                        break;
                    }
                    case COURIER: {
                        break;
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //}
    }
}
