package cloudade.server.auth.mongo.oauth.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import cloudade.server.auth.mongo.oauth.domain.MongoOAuth2RefreshToken;

public interface MongoOAuth2RefreshTokenRepository extends MongoRepository<MongoOAuth2RefreshToken, String>, MongoOAuth2RefreshTokenRepositoryBase {
}
