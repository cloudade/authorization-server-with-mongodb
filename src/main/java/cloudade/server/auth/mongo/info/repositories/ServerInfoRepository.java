package cloudade.server.auth.mongo.info.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cloudade.server.auth.mongo.info.domain.ServerInfo;

public interface ServerInfoRepository extends MongoRepository<ServerInfo, String>, ServerInfoRepositoryBase {
}
