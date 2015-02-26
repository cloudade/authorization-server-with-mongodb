package cloudade.server.auth.mongo.info;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cloudade.server.auth.mongo.info.domain.ServerInfo;
import cloudade.server.auth.mongo.info.repositories.ServerInfoRepository;

@Component
public class ServerInfoService {

	private ServerInfoRepository serverInfoRepository;

	@Autowired
	private ApplicationContext context;

	@Autowired
	public ServerInfoService(final ServerInfoRepository serverInfoRepository) {
		this.serverInfoRepository = serverInfoRepository;
	}

	@Value("${server.port}")
	private int port;

	public ServerInfo saveServerInfo() throws UnknownHostException {

		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		String host = InetAddress.getLocalHost().getHostName();
		String springBootVersion = Banner.class.getPackage().getImplementationVersion();
		String appVersion = getClass().getPackage().getImplementationTitle();
		
		ServerInfo beforeServerInfo = getServerInfo();
		if (beforeServerInfo != null) {
			beforeServerInfo.setSpringBootVersion(springBootVersion);
			beforeServerInfo.setAppVersion(appVersion);
			beforeServerInfo.setPid(jvmName.split("@")[0]);
			
			serverInfoRepository.save(beforeServerInfo);
			return beforeServerInfo;
		} else {
			ServerInfo serverInfo = new ServerInfo(null, host, port + "",
					jvmName.split("@")[0], springBootVersion, appVersion, null, null);
			serverInfoRepository.save(serverInfo);
			return serverInfo;
		}

	}

	public ServerInfo getServerInfo() throws UnknownHostException {

		return serverInfoRepository.findByUniqueKeys(InetAddress.getLocalHost()
				.getHostName(), "" + port);

	}

}
