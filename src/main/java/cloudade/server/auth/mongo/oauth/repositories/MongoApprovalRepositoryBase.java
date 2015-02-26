package cloudade.server.auth.mongo.oauth.repositories;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import cloudade.server.auth.mongo.oauth.domain.MongoApproval;

public interface MongoApprovalRepositoryBase {
    boolean updateOrCreate(Collection<MongoApproval> mongoApprovals);

    boolean updateExpiresAt(LocalDate now, MongoApproval mongoApproval);

    boolean deleteByUserIdAndClientIdAndScope(MongoApproval mongoApproval);

    List<MongoApproval> findByUserIdAndClientId(String userId, String clientId);
}
