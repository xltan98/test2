package sg.nus.iss.restaurantbackend.respositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.nus.iss.Utils.RestaurantUtility;
import sg.nus.iss.restaurantbackend.models.Restaurant;

@Repository
public class RestaurantRepository {
    @Autowired
    MongoTemplate template;

    // db.restaurants.distinct([{$unwind:'cusine'}
    //                       ,{$group:{_id:'cusine'}},
    //                       {$sort:{_id:1}}]))

    public List<String> getTypesOfCusine(){
        UnwindOperation unwindCusine=Aggregation.unwind("cuisine");

        GroupOperation groupByCusine=Aggregation.group("cuisine");

        SortOperation sortByCusine=Aggregation.sort(Sort.by(Direction.ASC,"_id"));

        Aggregation pipeline= Aggregation.newAggregation(unwindCusine,groupByCusine,sortByCusine);

        AggregationResults<Document> results =template.aggregate(pipeline,"restaurants",Document.class);

        return results.getMappedResults()
            .stream()
            .map(d -> d.getString("_id"))
            .collect(Collectors.toList());
    }

    public List<String> getDistinctCuisineTypes() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group("cuisine")
        );

        AggregationResults<String> results = template.aggregate(
            aggregation,
            "restaurants",
            String.class
        );

        return results.getMappedResults();
            
}
// db.restaurants.find({
//     "cuisine": { $in: [ "African" ] }
//      })

    public List<Restaurant> getRestaurantsFromType(String cuisine){

        Criteria c = Criteria.where("cuisine").regex(cuisine,"i");

        Query query=Query.query(c);

        List<Document>results=template.find(query,Document.class,"restaurants");

        List<Restaurant> restaurantList= new ArrayList<>();
        for(Document result:results){
            restaurantList.add(RestaurantUtility.toRestaurant(result));

        }

        return restaurantList;
        

    }

    public Restaurant getRestaurantsById(String restaurantId){
        Criteria c= Criteria.where("restaurant_id").regex(restaurantId);
        Query query=Query.query(c);

        List<Document>results=template.find(query,Document.class,"restaurants");

        List<Restaurant> restaurantList= new ArrayList<>();
        for(Document result:results){
            restaurantList.add(RestaurantUtility.toRestaurant(result));

        }

        return restaurantList.get(0);

    }
}
