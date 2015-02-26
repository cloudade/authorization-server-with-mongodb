package cloudade.server.auth.mongo.oauth.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cloudade.server.auth.mongo.oauth.domain.MongoOAuth2AccessToken;

public interface MongoOAuth2AccessTokenRepository extends MongoRepository<MongoOAuth2AccessToken, String>, MongoOAuth2AccessTokenRepositoryBase {

}
