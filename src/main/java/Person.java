import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person extends Thread {

    private PersonType personType;
    private String personName;


    public String getPersonName() {
        return personName + "(" + super.getId() + ")";
    }

}
