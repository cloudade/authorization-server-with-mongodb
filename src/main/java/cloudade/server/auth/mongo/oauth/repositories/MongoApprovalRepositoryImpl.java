package cloudade.server.auth.mongo.oauth.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.oauth.domain.MongoApproval;

import com.mongodb.WriteResult;

@Component
public class MongoApprovalRepositoryImpl implements MongoApprovalRepositoryBase {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public MongoApprovalRepositoryImpl(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public boolean updateOrCreate(final Collection<MongoApproval> mongoApprovals) {
		boolean result = true;
		for (MongoApproval mongoApproval : mongoApprovals) {
			final Update update = Update
					.update("expiresAt", mongoApproval.getExpiresAt())
					.addToSet("status", mongoApproval.getStatus())
					.addToSet("lastModifiedAt",
							mongoApproval.getLastUpdatedAt());

			final WriteResult writeResult = mongoTemplate.upsert(
					byUserIdAndClientIdAndScope(mongoApproval), update,
					MongoApproval.class);

			if (writeResult.getN() != 1) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean updateExpiresAt(final LocalDate expiresAt,
			final MongoApproval mongoApproval) {
		final Update update = Update.update("expiresAt", expiresAt);

		final WriteResult writeResult = mongoTemplate.updateFirst(
				byUserIdAndClientIdAndScope(mongoApproval), update,
				MongoApproval.class);

		return writeResult.getN() == 1;
	}

	@Override
	public boolean deleteByUserIdAndClientIdAndScope(
			final MongoApproval mongoApproval) {
		final WriteResult writeResult = mongoTemplate
				.remove(byUserIdAndClientIdAndScope(mongoApproval),
						MongoApproval.class);

		return writeResult.getN() == 1;
	}

	@Override
	public List<MongoApproval> findByUserIdAndClientId(final String userId,
			final String clientId) {
		final Query query = Query.query(where("userId").is(userId).andOperator(
				where("clientId").is(clientId)));
		return mongoTemplate.find(query, MongoApproval.class);
	}

	private Query byUserIdAndClientIdAndScope(final MongoApproval mongoApproval) {
		return Query.query(where("userId").is(mongoApproval.getUserId())
				.andOperator(
						where("clientId").is(mongoApproval.getClientId())
								.andOperator(
										where("scope").is(
												mongoApproval.getScope()))));
	}
}
