package cloudade.server.auth.mongo.oauth.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cloudade.server.auth.mongo.oauth.domain.MongoClientDetails;

public interface MongoClientDetailsRepository extends MongoRepository<MongoClientDetails, String>, MongoClientDetailsRepositoryBase {
}
