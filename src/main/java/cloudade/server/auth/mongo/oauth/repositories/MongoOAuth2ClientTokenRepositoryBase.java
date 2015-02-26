package cloudade.server.auth.mongo.oauth.repositories;

import cloudade.server.auth.mongo.oauth.domain.MongoOAuth2ClientToken;

public interface MongoOAuth2ClientTokenRepositoryBase {
    boolean deleteByAuthenticationId(String authenticationId);

    MongoOAuth2ClientToken findByAuthenticationId(String authenticationId);
}
