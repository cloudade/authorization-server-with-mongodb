package cloudade.server.auth.configuration;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.info.ServerInfoService;
import cloudade.server.auth.mongo.info.domain.ServerInfo;
import cloudade.server.auth.mongo.user.MongoUserDetailsManager;
import cloudade.server.auth.mongo.user.domain.User;

@Component
public class InitPostConstruct {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private ApplicationContext context;
	@Autowired private MongoUserDetailsManager mongoUserDetailsManager;
	@Autowired private ServerInfoService serverInfoService;


	@PostConstruct
	public void init() throws UnknownHostException{

		// debug for bean definition names
		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		logger.debug("[Bean Definitions Names]");
		for (String beanName : beanNames) {
			logger.debug("	" + beanName);
		}

		List<ServerInfo> servers = serverInfoService.getServerInfoByAppName();
		if(servers == null || servers.size() == 0){

			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
			authList.add(new SimpleGrantedAuthority("ROLE_OAUTH_ADMIN"));

			User user = new User(null, null, "admin", "admin", authList, true, true, true, true);
			mongoUserDetailsManager.createUser(user);

			serverInfoService.saveServerInfo(null);

		}else{

			for (ServerInfo serverInfo : servers) {
				logger.info("	> " + serverInfo);
			}

			serverInfoService.saveServerInfo();

		}

	}
}
