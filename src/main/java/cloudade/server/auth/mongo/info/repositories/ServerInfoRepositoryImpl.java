package cloudade.server.auth.mongo.info.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.user.domain.User;

import com.mongodb.WriteResult;

@Component
public class ServerInfoRepositoryImpl implements ServerInfoRepositoryBase {
	

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServerInfoRepositoryImpl(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
