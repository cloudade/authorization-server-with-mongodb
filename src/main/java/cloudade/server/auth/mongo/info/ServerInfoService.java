package cloudade.server.auth.mongo.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.joda.time.LocalDate;
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

	@Value("${spring.application.name}")
	private String appName;

	public ServerInfo saveServerInfo() throws IOException{
		return saveServerInfo(getServerInfo());
	}

	public ServerInfo saveServerInfo(ServerInfo serverInfo) throws IOException{

		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		String host = getHostname();

		String springBootVersion = Banner.class.getPackage().getImplementationVersion();
		String appVersion = getClass().getPackage().getImplementationVersion();

		if (serverInfo != null) {
			serverInfo.setSpringBootVersion(springBootVersion);
			serverInfo.setAppVersion(appVersion);
			serverInfo.setPid(jvmName.split("@")[0]);
			serverInfo.setLastStartedAt( new LocalDate() );

			serverInfoRepository.save(serverInfo);
			return serverInfo;
		} else {
			serverInfo = new ServerInfo(null, host, port + "",
					jvmName.split("@")[0], appName, springBootVersion, appVersion, new LocalDate(), new LocalDate());
			serverInfoRepository.save(serverInfo);
			return serverInfo;
		}

	}

	public ServerInfo getServerInfo() throws IOException {
		return serverInfoRepository.findByUniqueKeys(getHostname(), "" + port);

	}

	public List<ServerInfo> getServerInfoByAppName() {
		return serverInfoRepository.findByAppName(appName);
	}

	private String getHostname() throws IOException {
		String host;
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {

			Process p  = Runtime.getRuntime().exec("hostname");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuffer sBuf = new StringBuffer();
			while((line = br.readLine()) != null){
				sBuf.append(line);
				System.out.println(sBuf.toString());
			}
			host = sBuf.toString();

		}

		return host;
	}

}
