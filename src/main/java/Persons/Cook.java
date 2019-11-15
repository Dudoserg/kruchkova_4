package Persons;

import Message.Message_Base;
import Message.Message_Fridge;
import Start.Main;
import kek.Person;
import kek.PersonType;
import kek.Products;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Cook extends Person implements Runnable {

    BlockingQueue<Message_Base> blocking_To_Dispatcher;

    BlockingQueue<Message_Base> blocking_Dispatcher_Cook;
    BlockingQueue<Message_Fridge> blocking_Fridge_Cook;

    BlockingQueue<Message_Fridge> blocking_To_Fridge;

    BlockingQueue<Message_Base> blocking_To_Courier;

    public Cook(BlockingQueue<Message_Base> blocking_Dispatcher_Cook, BlockingQueue<Message_Fridge> blocking_Fridge_Cook,
                BlockingQueue<Message_Base> blocking_To_Dispatcher, BlockingQueue<Message_Fridge> blocking_To_Fridge,
                BlockingQueue<Message_Base> blocking_To_Courier, String personName) {
        super(PersonType.COOK, personName);
        this.blocking_To_Dispatcher = blocking_To_Dispatcher;

        this.blocking_Dispatcher_Cook = blocking_Dispatcher_Cook;
        this.blocking_Fridge_Cook = blocking_Fridge_Cook;

        this.blocking_To_Fridge = blocking_To_Fridge;

        this.blocking_To_Courier = blocking_To_Courier;
    }

    @Override
    public void run() {
        //while (true){
        try {

            Main.print(super.getPersonName() + " ждет какое либо сообщение...");
            sleep(1500);
            final Message_Base messageBaseFromDispatcher = blocking_Dispatcher_Cook.take();
            Main.print(super.getPersonName() + " Получил сообщение: " + messageBaseFromDispatcher.getMessage() + " от " + messageBaseFromDispatcher.getPerson().getPersonName());
            sleep(1500);

            Main.print(super.getPersonName() + " Вспоминает рецепт...");
            sleep(2500);


            // Todo переделать на класс блюд с рецептами
            switch (messageBaseFromDispatcher.getMessage()){
                case "пицца" : {
                    Message_Fridge pizza = new Message_Fridge();
                    pizza.getCountProducts().put(Products.Dough, 350);
                    pizza.getCountProducts().put(Products.Sausage, 150);
                    pizza.getCountProducts().put(Products.Tomatoes, 150);
                    pizza.getCountProducts().put(Products.Cheese, 100);

                    Main.print(super.getPersonName() + " обращается к холодильнику за ингридиентами " );
                    sleep(1500);
                    blocking_To_Fridge.put(pizza);

                    break;
                }
                case "бургер" : {
                    Message_Fridge burger = new Message_Fridge();
                    burger.getCountProducts().put(Products.Cutlet, 1);
                    burger.getCountProducts().put(Products.Bun, 1);
                    burger.getCountProducts().put(Products.Tomatoes, 50);

                    Main.print(super.getPersonName() + " обращается к холодильнику за ингридиентами " );
                    sleep(1500);
                    blocking_To_Fridge.put(burger);

                    break;
                }
                default:{

                    Main.print(super.getPersonName() + " Такого блюда НЕ СУЩЕСТВУЕТ!! ОШИБКА");
                    sleep(1500);
                }

            }
            // Ждем ответа холодильника
            Main.print(super.getPersonName() + " Ждет ответа от холодильника");
            sleep(1500);
            final Message_Fridge messageFromFridge = blocking_Fridge_Cook.take();
            Main.print(super.getPersonName() + " Ответ от холодильника пришел");
            sleep(1500);

            if(messageFromFridge.getStatus() == 1){
                // Все окей готовим
                Main.print(super.getPersonName() + " Ответ положительный, продукты есть, начинаем готовить...");
                sleep(1500);
                String result = coocking(messageFromFridge.getCountProducts(), messageBaseFromDispatcher.getMessage());
                // Шлем сообщение курьеру
                Main.print(super.getPersonName() + " Шлем сообщение курьеру" );
                sleep(1500);
                Message_Base messageBaseToCourier = new Message_Base(this, new String(messageBaseFromDispatcher.getMessage() + " готовая").toUpperCase());
                blocking_To_Courier.put(messageBaseToCourier);

            }else {
                // не хватает продукта
                Main.print(super.getPersonName() + " Ответ отрицательный, не хватает продукта...");
                // Шлем сообщение диспетчеру
                Message_Base messageBaseToDispatcher = new Message_Base(this, "Не хватает ингридиентов");
                Main.print(super.getPersonName() + " Шлет сообщение диспетчеру: " + messageBaseToDispatcher.getMessage() );
                sleep(1500);
                blocking_To_Dispatcher.put(messageBaseToDispatcher);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }

    private String coocking(HashMap<Products, Integer> Products, String product) throws InterruptedException {
        int timeSleep = (int) (Math.random() * 10) + 5;
        for(int i = 0 ; i < timeSleep; i++){
            Main.print(super.getPersonName() + " Готовит блюдо.... " );
            sleep(1000);
        }
        Main.print(super.getPersonName() + " Блюдо готово " );
        sleep(1500);
        return product;
    }
}
