package sg.nus.iss.restaurantbackend.respositories;

import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import sg.nus.iss.restaurantbackend.models.Comment;

@Repository
public class CommentRepository {
    @Autowired
    MongoTemplate template;

    public String postComment(Comment comment){
       
        comment.setCommentId( UUID.randomUUID().toString().substring(0,8));
        Document d= new Document()
        .append("name",comment.getName())
        .append("rating",comment.getRating())
        .append("restaurantId",comment.getRestaurantId())
        .append("text",comment.getText())
        .append("commentId",comment.getCommentId());

        template.insert(d,"comments");

        return comment.getCommentId();

    }

    
}
