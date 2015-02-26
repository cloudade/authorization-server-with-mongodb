package cloudade.server.auth.mongo.user.repositories;

public interface UserRepositoryBase {

    boolean changePassword(String oldPassword, String newPassword, String username);

}
