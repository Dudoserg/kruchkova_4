import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Exchanger;

//@Data
public class Dispatcher extends Person {

    private Exchanger<Message> EXCHANGER;


    @Getter
    @Setter
    private String message;


    public Dispatcher(Exchanger<Message> exchanger, String name) {
        super(PersonType.DISPATCHER, name);
        this.EXCHANGER = exchanger;
    }


    @Override
    public void run() {
        while (true) {
            try {

                Message receivedMessage = EXCHANGER.exchange(null);

                System.out.println(LocalTime.now().toString() + " " + super.getPersonName() + " получил следующее сообщение: '" +
                        receivedMessage.getMessage() + "' От " + receivedMessage.getPerson().getPersonName());

                switch (receivedMessage.getPerson().getPersonType()){
                    case BUYER:{
                        EXCHANGER.
                        break;
                    }
                    case COURIER:{
                        break;
                    }
                    case COOK:{
                        break;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
