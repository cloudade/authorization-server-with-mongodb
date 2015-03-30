package cloudade.server.auth.mongo.oauth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientKeyGenerator;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.oauth.domain.MongoOAuth2ClientToken;
import cloudade.server.auth.mongo.oauth.repositories.MongoOAuth2ClientTokenRepository;

@Component
public class MongoClientTokenServices implements ClientTokenServices {

    private final MongoOAuth2ClientTokenRepository mongoOAuth2ClientTokenRepository;

    private final ClientKeyGenerator clientKeyGenerator;

    @Autowired
    public MongoClientTokenServices(final MongoOAuth2ClientTokenRepository mongoOAuth2ClientTokenRepository,
                                    final ClientKeyGenerator clientKeyGenerator) {
        this.mongoOAuth2ClientTokenRepository = mongoOAuth2ClientTokenRepository;
        this.clientKeyGenerator = clientKeyGenerator;
    }

    @Override
    public OAuth2AccessToken getAccessToken(final OAuth2ProtectedResourceDetails resource,
                                            final Authentication authentication) {
        final MongoOAuth2ClientToken mongoOAuth2ClientToken = mongoOAuth2ClientTokenRepository.findByAuthenticationId(clientKeyGenerator.extractKey(resource, authentication));
        System.out.println("getAccessToken mongoOAuth2ClientToken : "+mongoOAuth2ClientToken.getToken());
        return SerializationUtils.deserialize(mongoOAuth2ClientToken.getToken());
    }

    @Override
    public void saveAccessToken(final OAuth2ProtectedResourceDetails resource,
                                final Authentication authentication,
                                final OAuth2AccessToken accessToken) {
        removeAccessToken(resource, authentication);
        final MongoOAuth2ClientToken mongoOAuth2ClientToken = new MongoOAuth2ClientToken(UUID.randomUUID().toString(),
                accessToken.getValue(),
                SerializationUtils.serialize(accessToken),
                clientKeyGenerator.extractKey(resource, authentication),
                authentication.getName(),
                resource.getClientId());
        
        System.out.println("saveAccessToken token : "+SerializationUtils.serialize(accessToken));
        mongoOAuth2ClientTokenRepository.save(mongoOAuth2ClientToken);
    }

    @Override
    public void removeAccessToken(final OAuth2ProtectedResourceDetails resource,
                                  final Authentication authentication) {
        mongoOAuth2ClientTokenRepository.deleteByAuthenticationId(clientKeyGenerator.extractKey(resource, authentication));
    }
}
