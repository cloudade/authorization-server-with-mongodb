package cloudade.server.auth.mongo.info.repositories;

import java.util.List;

import cloudade.server.auth.mongo.info.domain.ServerInfo;

public interface ServerInfoRepositoryBase {

	ServerInfo findByUniqueKeys(String host, String port);
	List<ServerInfo> findByAppName(String appName);

}
