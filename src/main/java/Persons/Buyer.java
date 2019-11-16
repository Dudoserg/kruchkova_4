package Persons;

import Message.Message_Base;
import Start.Main;
import kek.Person;
import kek.PersonType;

import java.util.concurrent.BlockingQueue;

public class Buyer extends Person implements Runnable {


    BlockingQueue<Message_Base> blocking_To_Dispatcher;

    BlockingQueue<Message_Base> blocking_To_Buyer;

    BlockingQueue<Message_Base> blocking_To_Courier;

    public Buyer(BlockingQueue<Message_Base> blocking_To_Buyer, BlockingQueue<Message_Base> blocking_To_Dispatcher,
                 BlockingQueue<Message_Base> blocking_To_Courier, String name) {
        super(PersonType.BUYER, name);
        this.blocking_To_Buyer = blocking_To_Buyer;
        this.blocking_To_Dispatcher = blocking_To_Dispatcher;
        this.blocking_To_Courier = blocking_To_Courier;
    }


    @Override
    public void run() {

        while (true) {
            try {
                synchronized (this) {
                    int timeSleep;

//                    timeSleep = (int)(Math.random() * Main._1000 * 10) + 5;
//                    Start.Main.print(super.getPersonName() + " засыпает на " + timeSleep + " секунд...");
//                    sleep(timeSleep);
//                    Start.Main.print(super.getPersonName() + " окончил спать");
//                    wait();

                    Message_Base messageBaseForDispatcher = new Message_Base(this, "пицца");

                    Main.print(super.getPersonName() + " посылает сообщение диспетчеру" + "Текст сообщения: " + "пицца");
                    blocking_To_Dispatcher.put(messageBaseForDispatcher);

                    Main.print(super.getPersonName() + " начинает ожидать ответа от Диспетчера или курьера....");
                    final Message_Base take = blocking_To_Buyer.take();
                    Main.print(super.getPersonName() + " получен ответ от " + take.getPerson().getPersonName());

                    switch (take.getPerson().getPersonType()) {
                        case DISPATCHER: {
                            Main.print(super.getPersonName() + " получил ответ от диспетчера. Ответ = " + take.getMessage());
                            timeSleep = (int) (Math.random() * Main._1000 * 10) + 5;
                            Main.print(super.getPersonName() + " Пользователь обдумывает новый заказ... " + timeSleep + " сек");
                            break;
                        }
                        case COURIER: {
                            Main.print(super.getPersonName() + " получил ответ от Курьера. Ответ = " + take.getMessage());
                            sleep(Main._1000);
                            Main.print(super.getPersonName() + " Платит курьеру ");
                            sleep(Main._1000);

                            blocking_To_Courier.put(new Message_Base(this, "228"));

                            timeSleep = (int) (Math.random() * 10) + 5;
                            for (int i = 0; i < timeSleep; i++) {
                                Main.print(super.getPersonName() + " Ест свой заказ ... ");
                                sleep(Main._1500);
                            }
                            Main.print(super.getPersonName() + " Съел свой заказ ");

                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized void wakeUpSamurai() {
        notify();
    }
}
