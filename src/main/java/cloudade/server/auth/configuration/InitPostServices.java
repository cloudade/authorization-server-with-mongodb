package cloudade.server.auth.configuration;

import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.user.MongoUserDetailsManager;
import cloudade.server.auth.mongo.user.domain.User;

@Component
public class InitPostServices {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private ApplicationContext context;
	@Autowired private MongoUserDetailsManager mongoUserDetailsManager;
	
	@Value("${server.port}")
	private int port;
	
	@PostConstruct
	public void init() throws UnknownHostException{
		
		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		logger.debug("[Bean Definitions Names]");
		for (String beanName : beanNames) {
			logger.debug("	" + beanName);
		}
		
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new SimpleGrantedAuthority("ROLE_OAUTH_ADMIN"));

		User user = new User(null, null, "admin", "admin", authList, true, true, true, true);
		//mongoUserDetailsManager.createUser(user);

		logger.info(" > created admin user !");
		
		System.out.println("getHostName : " + java.net.InetAddress.getLocalHost().getHostName());
		System.out.println("an : " + context.getApplicationName() );
		System.out.println("dn : " + context.getDisplayName());
		System.out.println("ih : " + context.getId());
		System.out.println("p : " + port);
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println("jvmName : " + jvmName.split("@")[0]);
		
		String version = Banner.class.getPackage().getImplementationVersion();
		version = (version == null ? "" : " (v" + version + ")");
		

		System.out.println("version : " + version);
		System.out.println("version : " + Banner.class.getPackage().getImplementationVersion());
		System.out.println("version : " + getClass().getPackage().getImplementationTitle());
		
		
		
		
		
		
	}
}
