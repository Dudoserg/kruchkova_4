package Message;


import kek.Products;
import lombok.*;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message_Fridge {
    @Getter
    @Setter
    private HashMap<Products, Integer> countProducts;

    @Getter
    @Setter
    private Integer status;

    {
        countProducts = new HashMap<>();
    }
}
