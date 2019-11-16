package Start;


import Message.Message_Base;
import Message.Message_Fridge;
import Persons.*;
import kek.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
    }

    private static final Exchanger<Message_Base> EXCHANGER = new Exchanger<>();

    public static final Integer _1500 = 150 * 5;
    public static final Integer _1000 = 100 * 5;

    public static Semaphore semaphorePrint = new Semaphore(1, true);

    public static void print(String str) {
        try {
            LocalTime localTime = LocalTime.now();
            String currentTime = "[ " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond();
            if (localTime.getHour() < 10)
                currentTime += " ";
            if (localTime.getMinute() < 10)
                currentTime += " ";
            if (localTime.getSecond() < 10)
                currentTime += " ";
            currentTime += " ]";
            semaphorePrint.acquire();
            System.out.println(currentTime + "  " + str);
            semaphorePrint.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Main() {

        BlockingQueue<Message_Base> blocking_To_Buyer = new ArrayBlockingQueue<Message_Base>(1, true);
        BlockingQueue<Message_Base> blocking_To_Dispatcher = new ArrayBlockingQueue<Message_Base>(1, true);

        BlockingQueue<Message_Base> blocking_Dispatcher_Cook = new ArrayBlockingQueue<Message_Base>(1, true);
        BlockingQueue<Message_Fridge> blocking_Fridge_Cook = new ArrayBlockingQueue<Message_Fridge>(1, true);

        BlockingQueue<Message_Base> blocking_To_Courier = new ArrayBlockingQueue<Message_Base>(1, true);
        BlockingQueue<Message_Fridge> blocking_To_Fridge = new ArrayBlockingQueue<Message_Fridge>(1, true);

        Person dispatcher = new Dispatcher(blocking_To_Dispatcher, blocking_To_Buyer, blocking_Dispatcher_Cook, "dispatcher");
        Person buyer_1 = new Buyer(blocking_To_Buyer, blocking_To_Dispatcher, blocking_To_Courier, "buyer_1");
        Person cook = new Cook(blocking_Dispatcher_Cook, blocking_Fridge_Cook, blocking_To_Dispatcher, blocking_To_Fridge, blocking_To_Courier, "cook");
        Person fridge = new Fridge(blocking_To_Fridge, blocking_Fridge_Cook, "fridge");
        Person courier = new Courier(blocking_To_Courier, blocking_To_Dispatcher, blocking_To_Buyer, "courier");

//        ((Dispatcher) dispatcher).setByuer((Buyer) buyer_1);

        // Список задач
        List<Runnable> list_Runnable = new ArrayList<>();
        list_Runnable.add(dispatcher);
        list_Runnable.add(buyer_1);
        list_Runnable.add(cook);
        list_Runnable.add(fridge);
        list_Runnable.add(courier);

        // Список потоков
        List<Thread> list_Thread = list_Runnable.stream().map(runnable -> new Thread(runnable)).collect(Collectors.toList());

        // Запускаем потоки
        for (int i = 0; i < list_Thread.size(); i++) {
            list_Thread.get(i).start();
        }
        System.out.println(Products.Mushrooms.toString());
        System.out.println("end of MAIN");

    }
}
