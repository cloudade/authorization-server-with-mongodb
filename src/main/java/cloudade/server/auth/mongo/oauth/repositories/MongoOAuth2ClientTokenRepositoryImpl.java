package cloudade.server.auth.mongo.oauth.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.oauth.domain.MongoOAuth2ClientToken;

import com.mongodb.WriteResult;

@Component
public class MongoOAuth2ClientTokenRepositoryImpl implements MongoOAuth2ClientTokenRepositoryBase {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoOAuth2ClientTokenRepositoryImpl(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean deleteByAuthenticationId(final String authenticationId) {
        final Query query = Query.query(Criteria.where("authenticationId").is(authenticationId));
        final WriteResult writeResult = mongoTemplate.remove(query, MongoOAuth2ClientToken.class);
        return writeResult.getN() == 1;
    }

    @Override
    public MongoOAuth2ClientToken findByAuthenticationId(final String authenticationId) {
        final Query query = Query.query(Criteria.where("authenticationId").is(authenticationId));
        return mongoTemplate.findOne(query, MongoOAuth2ClientToken.class);
    }
}
