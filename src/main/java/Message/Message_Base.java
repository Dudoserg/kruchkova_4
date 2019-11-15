package Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import kek.Person;

@Data
@AllArgsConstructor
public class Message_Base {

    enum Type{
        Order_Is_Ready; // Заказ успешно выполнен

    }

    private Person person;
    private String message;



    @Override
    public String toString() {
        return "Message_Base.Message_Base{" +
                "message='" + message + '\'' +
                '}';
    }
}
