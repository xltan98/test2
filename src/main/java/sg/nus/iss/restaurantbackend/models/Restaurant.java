package sg.nus.iss.restaurantbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {

    String building;
    Address address;
    String borough;
    String cuisine;
    String name;
    String restaurantId;


    
}
