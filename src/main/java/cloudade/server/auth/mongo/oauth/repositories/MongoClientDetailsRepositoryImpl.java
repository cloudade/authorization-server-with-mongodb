package cloudade.server.auth.mongo.oauth.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.oauth.domain.MongoClientDetails;

import com.mongodb.WriteResult;

@Component
public class MongoClientDetailsRepositoryImpl implements MongoClientDetailsRepositoryBase {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoClientDetailsRepositoryImpl(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean deleteByClientId(String clientId) {
        final Query query = Query.query(Criteria.where("clientId").is(clientId));
        final WriteResult writeResult = mongoTemplate.remove(query, MongoClientDetails.class);
        return writeResult.getN() == 1;
    }

    @Override
    public boolean update(final MongoClientDetails mongoClientDetails) {
        final Query query = Query.query(Criteria.where("clientId").is(mongoClientDetails.getClientId()));

        final Update update = Update.update("scope", mongoClientDetails.getScope())
                .set("accessTokenValiditySeconds", mongoClientDetails.getAccessTokenValiditySeconds())
                .set("refreshTokenValiditySeconds", mongoClientDetails.getRefreshTokenValiditySeconds())
                .set("additionalInformation", mongoClientDetails.getAdditionalInformation())
                .set("resourceIds", mongoClientDetails.getResourceIds())
                .set("authorizedGrantTypes", mongoClientDetails.getAuthorizedGrantTypes())
                .set("authorities", mongoClientDetails.getAuthorities())
                .set("autoApproveScopes", mongoClientDetails.getAutoApproveScopes())
                .set("registeredRedirectUris", mongoClientDetails.getRegisteredRedirectUri());

        final WriteResult writeResult = mongoTemplate.updateFirst(query, update, MongoClientDetails.class);

        return writeResult.getN() == 1;
    }

    @Override
    public boolean updateClientSecret(final String clientId,
                                      final String newSecret) {
        final Query query = Query.query(Criteria.where("clientId").is(clientId));

        final Update update = Update.update("clientSecret", newSecret);

        final WriteResult writeResult = mongoTemplate.updateFirst(query, update, MongoClientDetails.class);

        return writeResult.getN() == 1;
    }

    @Override
    public MongoClientDetails findByClientId(final String clientId) throws IllegalArgumentException {
        final Query query = Query.query(Criteria.where("clientId").is(clientId));
        final MongoClientDetails mongoClientDetails = mongoTemplate.findOne(query, MongoClientDetails.class);
        if (mongoClientDetails == null) {
            throw new IllegalArgumentException("No valid client id");
        }
        return mongoClientDetails;
    }


}
