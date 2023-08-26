package sg.nus.iss.restaurantbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String name;
	private int rating;
	private String restaurantId;
	private String text;
    private String commentId;

    
}
