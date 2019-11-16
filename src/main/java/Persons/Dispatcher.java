package Persons;

import Message.Message_Base;
import Start.Main;
import javafx.application.Platform;
import kek.Person;
import kek.PersonType;

import java.util.concurrent.BlockingQueue;

//@Data
public class Dispatcher extends Person implements Runnable {


    BlockingQueue<Message_Base> blocking_To_Dispatcher;
    BlockingQueue<Message_Base> blocking_To_Buyer;
    BlockingQueue<Message_Base> blocking_To_Cook;

    int money = 0;

    public Dispatcher(BlockingQueue<Message_Base> blocking_To_Dispatcher, BlockingQueue<Message_Base> blocking_To_Buyer,
                      BlockingQueue<Message_Base> blocking_To_Cook, String name) {
        super(PersonType.DISPATCHER, name);
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
        while (true) {
            try {
                synchronized (this) {
//                    Start.Main.print(super.getPersonName() + " засыпает на 10 секунд...");
//                    sleep((int) (Main._1000 * 1));
//                    Start.Main.print(super.getPersonName() + " окончил спать");
//                    Start.Main.print(super.getPersonName() + " будит покупателя");
//                    sleep((int) (Main._1000 * 1));
//                    byuer.wakeUpSamurai();

                    Main.print(super.getPersonName() + " ждет какое либо сообщение...");
                    Message_Base messageBaseTaked = blocking_To_Dispatcher.take();

                    switch (messageBaseTaked.getPerson().getPersonType()) {
                        case BUYER: {
                            Main.print(super.getPersonName() + " получил сообщение от покупателя " + messageBaseTaked.getPerson().getPersonName());
                            sleep(Main._1500);
                            Main.print(super.getPersonName() + " диспетчер обрабатывает заказ в течении 5 секунд....");
                            sleep((int) (Main._1000 * 5));    //      sleep((int) (Math.random() * Main._1000 * 5));
                            blocking_To_Cook.put(new Message_Base(this, messageBaseTaked.getMessage()));
                            Main.print(super.getPersonName() + "диспетчер обработал заказ, передав его повару");
                            sleep(Main._1500);
//                            Message_Base.Message_Base messageForBuyer = new Message_Base.Message_Base(this, "пицца готовая()");
//                            Start.Main.print(super.getPersonName() + "диспетчер отвечает покупателю");
//                            blocking_To_Buyer.put(messageForBuyer);

                            break;
                        }
                        case COOK: {
                            Main.print(super.getPersonName() + " получил сообщение от повара " + messageBaseTaked.getPerson().getPersonName());
                            sleep(Main._1000);
                            Main.print(super.getPersonName() + " звонит покупателю с сообщением об отсутствием...");
                            sleep(Main._1000);
                            blocking_To_Buyer.put(new Message_Base(this, messageBaseTaked.getMessage()));
                            Main.print(super.getPersonName() + " сообщил покупателю...");
                            sleep(Main._1000);
                            sleep(Main._1000);

                            break;
                        }
                        case COURIER: {
                            Main.print(super.getPersonName() + " получил сообщение от курьера " + messageBaseTaked.getPerson().getPersonName());
                            sleep(Main._1000);
                            money += Integer.valueOf(messageBaseTaked.getMessage());
                            Main.print(super.getPersonName() + " казна пополнена: +" + messageBaseTaked.getMessage() + " Всего: " + money);
                            sleep(Main._1000);
                            break;
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
