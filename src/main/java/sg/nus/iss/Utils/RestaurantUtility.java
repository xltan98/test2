package sg.nus.iss.Utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.iss.restaurantbackend.models.Address;
import sg.nus.iss.restaurantbackend.models.Comment;
import sg.nus.iss.restaurantbackend.models.Coordinate;
import sg.nus.iss.restaurantbackend.models.Restaurant;

@Component
public class RestaurantUtility {

    public static Restaurant toRestaurant(Document doc){
        Restaurant res =new Restaurant();

        res.setName(doc.getString("name"));
       Document addressdoc=doc.get("address",Document.class);
       List<Double> coorddoc = addressdoc.getList("coord",Double.class);
       Coordinate coord= new Coordinate();
       coord.setLatitude(coorddoc.get(0));
       coord.setLongtitude(coorddoc.get(1));
       Address address = new Address();
       address.setBuilding(addressdoc.getString("building"));
       address.setCoord(coord);
       address.setStreet(addressdoc.getString("street"));
       address.setZipcode(addressdoc.getString("zipcode"));
       
       res.setAddress(address);
       res.setBorough(doc.getString("borough"));
       res.setCuisine(doc.getString("cuisine"));
       res.setRestaurantId(doc.getString("restaurant_id"));

       return res;

    }

    // public static Comment toComment(String payload){
    //     JsonReader reader=Json.createReader(new StringReader(payload));
    //     JsonObject jsonPayload=reader.readObject();
    //     JsonObject jcomment=jsonPayload.getJsonObject("comment");

    //     Comment comment = new Comment();
    //     comment.setName(jcomment.getString("name"));
    //     comment.setRating(jcomment.getInt("rating")); 
    //     comment.setRestaurantId(jcomment.getString("restaurantId"));
    //     comment.setText(jcomment.getString("text"));
    //    // comment.setCommentId(jcomment.getString("commentId"));


    //     return comment;
    // }

    public static Comment toComment(String payload){
        JsonObject jcomment = Json.createReader(new StringReader(payload)).readObject();
    
        Comment comment = new Comment();
        comment.setName(jcomment.getString("name"));
        comment.setRating(jcomment.getInt("rating")); 
        comment.setRestaurantId(jcomment.getString("restaurantId"));
        comment.setText(jcomment.getString("text"));
        // No need to set commentId since it's not present in the payload
    
        return comment;
    }
    
}
