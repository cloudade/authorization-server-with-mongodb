package cloudade.server.auth.mongo.oauth.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cloudade.server.auth.mongo.oauth.domain.MongoApproval;

public interface MongoApprovalRepository extends MongoRepository<MongoApproval, String>, MongoApprovalRepositoryBase {
}
