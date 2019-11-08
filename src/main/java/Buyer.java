import java.util.concurrent.Exchanger;

public class Buyer extends Person {

    private Exchanger<Message> EXCHANGER;



    public Buyer(Exchanger<Message> EXCHANGER, String name) {
        super(PersonType.BUYER, name);
        this.EXCHANGER = EXCHANGER;

    }


    @Override
    public void run() {
        while (true) {
            try {


                sleep((int) (Math.random() * 1000 * 10));
                this.EXCHANGER.exchange(new Message(this, "pizza"));


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
