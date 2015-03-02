package cloudade.server.auth.mongo.info.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.info.domain.ServerInfo;

@Component
public class ServerInfoRepositoryImpl implements ServerInfoRepositoryBase {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public ServerInfoRepositoryImpl(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public ServerInfo findByUniqueKeys(String host, String port) {
		final Query query = Query.query(Criteria.where("host").is(host)
				.andOperator(Criteria.where("port").is(port)));
		return mongoTemplate.findOne(query, ServerInfo.class);
	}

	@Override
	public List<ServerInfo> findByAppName(String appName) {
		final Query query = Query.query(Criteria.where("appName").is(appName));
		return mongoTemplate.find(query, ServerInfo.class);
	}
	
}
