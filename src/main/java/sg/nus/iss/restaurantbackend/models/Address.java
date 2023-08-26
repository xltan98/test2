package sg.nus.iss.restaurantbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

   String building;
   Coordinate coord;
   String street;
   String zipcode;

    
}
