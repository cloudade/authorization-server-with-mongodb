package cloudade.server.auth.mongo.user.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.user.domain.User;

import com.mongodb.WriteResult;

@Component
public class UserRepositoryImpl implements UserRepositoryBase {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean changePassword(final String oldPassword,
                               final String newPassword,
                               final String username) {
        final Query searchUserQuery = new Query(where("username").is(username).andOperator(where("password").is(oldPassword)));
        final WriteResult result = mongoTemplate.updateFirst(searchUserQuery, update("password", newPassword), User.class);
        return result.getN() == 1? true : false;
    }
}
