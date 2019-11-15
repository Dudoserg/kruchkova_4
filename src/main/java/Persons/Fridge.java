package Persons;

import Message.Message_Fridge;
import Start.Main;
import kek.Person;
import kek.PersonType;
import kek.Products;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Fridge extends Person implements Runnable {

    BlockingQueue<Message_Fridge> blocking_To_Fridge;
    BlockingQueue<Message_Fridge> blocking_To_Cook;

    HashMap<Products, Integer> countProducts;


    public Fridge(BlockingQueue<Message_Fridge> blocking_To_Fridge, BlockingQueue<Message_Fridge> blocking_To_Cook, String personName) {
        super(PersonType.COOK, personName);
        this.blocking_To_Fridge = blocking_To_Fridge;
        this.blocking_To_Cook = blocking_To_Cook;
        countProducts = new HashMap<Products, Integer>();

        countProducts.put(Products.Dough, 1000);
        countProducts.put(Products.Sausage, 1000);
        countProducts.put(Products.Cheese, 1000);
        countProducts.put(Products.Tomatoes, 1000);
        countProducts.put(Products.Mushrooms, 1000);
        countProducts.put(Products.Cutlet, 1000);
        countProducts.put(Products.Bun, 1000);
    }

    @Override
    public void run() {
        try {
            Main.print(super.getPersonName() + " ждет какое либо сообщение...");
            sleep(1500);
            Message_Fridge takedMessage = blocking_To_Fridge.take();
            Main.print(super.getPersonName() + " Получил сообщение от повара ");
            sleep(1500);

            Message_Fridge messageToCook = new Message_Fridge();
            boolean flag_enough_products = true;
            Main.print(super.getPersonName() + " Начинаем проверку присутствия продуктов...");
            sleep(1500);
            for(Map.Entry<Products, Integer> current : takedMessage.getCountProducts().entrySet()){
                Products needProduct = current.getKey();
                Integer needCount = current.getValue();
                // Если в холодильнике достаточно текущего продукта
                if( countProducts.get(needProduct) >= needCount){
                    Main.print(super.getPersonName() + needProduct.toString() + " в количестве " + needCount + " присутствует");
                }
                else{
                    // Увы такого не хватает
                    Main.print(super.getPersonName() + needProduct.toString() + " в количестве " + needCount + " ОТСУТСТВУЕТ");
                    flag_enough_products = false;
                }
                sleep(1000 * 1);
            }

            Main.print(super.getPersonName() + " Проверка закончена ");
            sleep(1500);

            if(flag_enough_products){
                // Всех продуктов хватает
                for(Map.Entry<Products, Integer> current : takedMessage.getCountProducts().entrySet()){
                    Products needProduct = current.getKey();
                    Integer needCount = current.getValue();
                    // Если в холодильнике достаточно текущего продукта
                    if( countProducts.get(needProduct) >= needCount){
                        // Уменьшаем количество в холодильнике
                        flag_enough_products = false;
                        countProducts.put(needProduct, countProducts.get(needProduct)- needCount);
                        messageToCook.getCountProducts().put(needProduct, needCount);
                    }
                }
                messageToCook.setStatus(1);
                Main.print(super.getPersonName() + " Необходимые продукты в наличии, отправляем их повару ");
                sleep(1500);
            }
            else {
                // Какого то продукта не хватает
                messageToCook.setStatus(0);
                Main.print(super.getPersonName() + " Нет одного из необходимых продуктов, ничего не отправляем повару ");
                sleep(1500);
            }
            blocking_To_Cook.put(messageToCook);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
