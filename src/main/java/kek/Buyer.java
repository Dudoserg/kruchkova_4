package kek;

import Message.Message_Base;
import Start.Main;

import java.util.concurrent.BlockingQueue;

public class Buyer extends Person implements Runnable {


    BlockingQueue<Message_Base> blocking_To_Dispatcher;

    BlockingQueue<Message_Base> blocking_To_Buyer;

    public Buyer(BlockingQueue<Message_Base> blocking_To_Buyer, BlockingQueue<Message_Base> blocking_To_Dispatcher, String name) {
        super(PersonType.BUYER, name);
        this.blocking_To_Buyer = blocking_To_Buyer;
        this.blocking_To_Dispatcher = blocking_To_Dispatcher;
    }


    @Override
    public void run() {

        //while (true) {
        try {
            synchronized (this) {
                int timeSleep;

//                    timeSleep = (int)(Math.random() * 1000 * 10) + 5;
//                    Start.Main.print(super.getPersonName() + " засыпает на " + timeSleep + " секунд...");
//                    sleep(timeSleep);
//                    Start.Main.print(super.getPersonName() + " окончил спать");
//                    wait();

                Message_Base messageBaseForDispatcher = new Message_Base(this, "пицца");

                Main.print(super.getPersonName() + " посылает сообщение диспетчеру" + "Текст сообщения: " + "пицца");
                blocking_To_Dispatcher.put(messageBaseForDispatcher);

                Main.print(super.getPersonName() + " начинает ожидать ответа от Диспетчера или курьера....");
                final Message_Base take = blocking_To_Buyer.take();

                switch (take.getPerson().getPersonType()) {
                    case DISPATCHER: {
                        Main.print(super.getPersonName() + " получил ответ от диспетчера. Ответ = " + take.getMessage());
                        timeSleep = (int) (Math.random() * 1000 * 10) + 5;
                        Main.print(super.getPersonName() + " Пользователь обдумывает новый заказ... " + timeSleep + " сек");
                        break;
                    }
                    case COURIER: {
                        Main.print(super.getPersonName() + " получил ответ от Курьера. Ответ = " + take.getMessage());
                        timeSleep = (int) (Math.random() * 1000 * 10) + 5;
                        Main.print(super.getPersonName() + " Ест свой заказ ... " + timeSleep + " сек");
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }

    public synchronized void wakeUpSamurai() {
        notify();
    }
}
