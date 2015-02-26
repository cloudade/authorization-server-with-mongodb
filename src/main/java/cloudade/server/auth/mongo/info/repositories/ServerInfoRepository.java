package cloudade.server.auth.mongo.info.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cloudade.server.auth.mongo.info.domain.ServerInfo;
import cloudade.server.auth.mongo.user.domain.User;

public interface ServerInfoRepository extends MongoRepository<ServerInfo, String>, ServerInfoRepositoryBase {
}
