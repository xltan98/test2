package sg.nus.iss.restaurantbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import sg.nus.iss.Utils.RestaurantUtility;
import sg.nus.iss.restaurantbackend.models.Restaurant;
import sg.nus.iss.restaurantbackend.respositories.CommentRepository;
import sg.nus.iss.restaurantbackend.respositories.RestaurantRepository;

@RestController
@CrossOrigin
public class RestaurantController {
    @Autowired
    RestaurantRepository rRepo;

    @Autowired
    CommentRepository cRepo;

    @GetMapping(path="/cuisines")
    public ResponseEntity<String> getTypesOfCusine(){
        List<String>cusineList=rRepo.getTypesOfCusine();
        System.out.println(cusineList);

        JsonArrayBuilder arrayBuilder= Json.createArrayBuilder();

        for(String cusine:cusineList){

            arrayBuilder.add(Json.createObjectBuilder()
            .add("cusineType",cusine));

        }

        return ResponseEntity.ok(arrayBuilder.build().toString());
    }

    @GetMapping(path="/cuisines/{cuisine}")
    public ResponseEntity<String> getRestaurantFromTypeOfCuisine(@PathVariable String cuisine){

        List<Restaurant>restaurantList=rRepo.getRestaurantsFromType(cuisine);
        JsonArrayBuilder ab = Json.createArrayBuilder();
        for(Restaurant r :restaurantList){
            JsonObjectBuilder restaurantBuilder = Json.createObjectBuilder();
            restaurantBuilder.add("name", r.getName());
            restaurantBuilder.add("borough",r.getBorough());
            restaurantBuilder.add("cuisine",r.getCuisine());
            restaurantBuilder.add("restaurantId",r.getRestaurantId());
            restaurantBuilder.add("coordlong",r.getAddress().getCoord().getLongtitude());
            restaurantBuilder.add("coordlat",r.getAddress().getCoord().getLatitude());

            

            ab.add(restaurantBuilder);

            
        }
        
        

        return ResponseEntity.ok(ab.build().toString());
    }

    @GetMapping(path="/restaurant/{restaurantId}")
    public ResponseEntity<String> getRestaurant(@PathVariable String restaurantId){
        Restaurant r=rRepo.getRestaurantsById(restaurantId);
        //String commentId=cRepo.postComment(RestaurantUtility.toComment(payload));
          JsonObjectBuilder restaurantBuilder = Json.createObjectBuilder();
            restaurantBuilder.add("name", r.getName());
            restaurantBuilder.add("borough",r.getBorough());
            restaurantBuilder.add("cuisine",r.getCuisine());
            restaurantBuilder.add("restaurantId",r.getRestaurantId());
            restaurantBuilder.add("coordlong",r.getAddress().getCoord().getLongtitude());
            restaurantBuilder.add("coordlat",r.getAddress().getCoord().getLatitude());
        //return ResponseEntity.ok(Json.createObjectBuilder().add("commentId",commentId).build().toString());
        return ResponseEntity.ok(restaurantBuilder.build().toString());
    }

    @PostMapping(path="/comment")
    public ResponseEntity<String>postComment(@RequestBody String payload){
        String commentId=cRepo.postComment(RestaurantUtility.toComment(payload));
            

        return ResponseEntity.ok(Json.createObjectBuilder()
        .add("commentId", commentId)
        .build()
        .toString());

    }
    
}
