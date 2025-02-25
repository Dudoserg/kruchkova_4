package Persons;

import Message.Message_Base;
import Start.Main;
import kek.Person;
import kek.PersonType;

import java.util.concurrent.BlockingQueue;

public class Courier extends Person implements Runnable {

    BlockingQueue<Message_Base> blocking_To_Dispatcher;
    BlockingQueue<Message_Base> blocking_To_Courier;
    BlockingQueue<Message_Base> blocking_To_Buyer;

    public Courier(BlockingQueue<Message_Base> blocking_To_Courier, BlockingQueue<Message_Base> blocking_To_Dispatcher,
                   BlockingQueue<Message_Base> blocking_To_Buyer, String personName) {
        super(PersonType.COURIER, personName);
        this.blocking_To_Courier = blocking_To_Courier;
        this.blocking_To_Dispatcher = blocking_To_Dispatcher;
        this.blocking_To_Buyer = blocking_To_Buyer;
    }


    @Override
    public void run() {
        while (true) {

            try {
                Main.print(super.getPersonName() + " свободен");

                Message_Base messageFromCook = blocking_To_Courier.take();
                Main.print(super.getPersonName() + " Получил блюдо для доставки от " + messageFromCook.getPerson().getPersonName());
                sleep(Main._1500);

                this.drive_To_Buyer();

                Main.print(super.getPersonName() + " Приехал к покупателю");
                sleep(Main._1500);

                Main.print(super.getPersonName() + " Отдает заказ покупателю ");
                sleep(Main._1500);

                Message_Base messageToBuyer = new Message_Base(this, messageFromCook.getMessage());
                blocking_To_Buyer.put(messageToBuyer);
                Main.print(super.getPersonName() + " Заказ отдан ");
                sleep(Main._1500);

                Main.print(super.getPersonName() + " ожидает оплату ");
                sleep(Main._1500);
                Message_Base messageToCourier = blocking_To_Courier.take();
                Main.print(super.getPersonName() + " оплата получена");
                sleep(Main._1500);

                this.drive_To_Dispatcher();
                Main.print(super.getPersonName() + " Приехал к диспетчеру");
                sleep(Main._1500);

                Main.print(super.getPersonName() + " Отдает Деньги диспетчеру ");
                sleep(Main._1500);
                Message_Base messageToDispatcher = new Message_Base(this, messageToCourier.getMessage());
                blocking_To_Dispatcher.put(messageToDispatcher);
                Main.print(super.getPersonName() + " Деньги отданы диспетчеру");
                sleep(Main._1500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void drive_To_Buyer() throws InterruptedException {
        int timeSleep = (int) (Math.random() * 10) + 5;
        Main.print(super.getPersonName() + " Выехал к покупателю ");
        for (int i = 0; i < timeSleep; i++) {
            Main.print(super.getPersonName() + " Едет к покупателю.... ");
            sleep(Main._1000);
        }
    }

    public void drive_To_Dispatcher() throws InterruptedException {
        int timeSleep = (int) (Math.random() * 10) + 5;
        Main.print(super.getPersonName() + " Выехал к Диспетчеру ");
        for (int i = 0; i < timeSleep; i++) {
            Main.print(super.getPersonName() + " Едет к Диспетчеру.... ");
            sleep(Main._1000);
        }
    }
}
