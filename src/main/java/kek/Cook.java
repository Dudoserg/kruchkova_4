package kek;

import Message.Message_Base;
import Message.Message_Fridge;
import Start.Main;

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
            final Message_Base messageBaseFromDispatcher = blocking_Dispatcher_Cook.take();
            Main.print(super.getPersonName() + " Получил сообщение: " + messageBaseFromDispatcher.getMessage() + " от " + messageBaseFromDispatcher.getPerson().getPersonName());

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
                    blocking_To_Fridge.put(pizza);

                    break;
                }
                case "бургер" : {
                    Message_Fridge burger = new Message_Fridge();
                    burger.getCountProducts().put(Products.Cutlet, 1);
                    burger.getCountProducts().put(Products.Bun, 1);
                    burger.getCountProducts().put(Products.Tomatoes, 50);

                    Main.print(super.getPersonName() + " обращается к холодильнику за ингридиентами " );
                    blocking_To_Fridge.put(burger);

                    break;
                }
                default:{

                    Main.print(super.getPersonName() + " Такого блюда НЕ СУЩЕСТВУЕТ!! ОШИБКА");

                }

            }
            // Ждем ответа холодильника
            Main.print(super.getPersonName() + " Ждет ответа от холодильника");
            final Message_Fridge messageFromFridge = blocking_Fridge_Cook.take();
            Main.print(super.getPersonName() + " Ответ от холодильника пришел");

            if(messageFromFridge.getStatus() == 1){
                // Все окей готовим
                Main.print(super.getPersonName() + " Ответ положительный, продукты есть, начинаем готовить...");
                String result = coocking(messageFromFridge.getCountProducts(), messageBaseFromDispatcher.getMessage());
                // Шлем сообщение курьеру
                Main.print(super.getPersonName() + " Шлем сообщение курьеру" );
                Message_Base messageBaseToCourier = new Message_Base(this, "Пицца");
                blocking_To_Courier.put(messageBaseToCourier);

            }else {
                // не хватает продукта
                Main.print(super.getPersonName() + " Ответ отрицательный, не хватает продукта...");
                // Шлем сообщение диспетчеру
                Message_Base messageBaseToDispatcher = new Message_Base(this, "Не хватает ингридиентов");
                Main.print(super.getPersonName() + " Шлет сообщение диспетчеру: " + messageBaseToDispatcher.getMessage() );
                blocking_To_Dispatcher.put(messageBaseToDispatcher);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }

    private String coocking(HashMap<Products, Integer> Products, String product) throws InterruptedException {
        Main.print(super.getPersonName() + " Готовит блюдо в течении 10 сек... " );
        sleep(1000 * 10);
        Main.print(super.getPersonName() + " Блюдо готово " );
        return product;
    }
}
