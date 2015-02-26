package cloudade.server.auth.mongo.oauth.repositories;

import cloudade.server.auth.mongo.oauth.domain.MongoOAuth2RefreshToken;

public interface MongoOAuth2RefreshTokenRepositoryBase {
    MongoOAuth2RefreshToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);
}
