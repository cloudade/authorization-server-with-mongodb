package cloudade.server.auth.mongo.info.domain;

import java.util.Objects;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ServerInfo {

	@Id
	private String id;
	private String host;
	private String port;
	private String pid;
	private String appName;
	private String springBootVersion;
	private String appVersion;
	private LocalDate lastStartedAt;
	private LocalDate firstStartedAt;

	public ServerInfo(final String id, final String host, final String port,
			final String pid, final String appName, final String springBootVersion,
			final String appVersion, final LocalDate lastStartedAt,
			final LocalDate firstStartedAt) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.pid = pid;
		this.appName = appName;
		this.springBootVersion = springBootVersion;
		this.appVersion = appVersion;
		this.lastStartedAt = lastStartedAt;
		this.firstStartedAt = firstStartedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getHost() {
		return host;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSpringBootVersion() {
		return springBootVersion;
	}

	public void setSpringBootVersion(String springBootVersion) {
		this.springBootVersion = springBootVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public LocalDate getLastStartedAt() {
		return lastStartedAt;
	}

	public void setLastStartedAt(LocalDate lastStartedAt) {
		this.lastStartedAt = lastStartedAt;
	}

	public LocalDate getFirstStartedAt() {
		return firstStartedAt;
	}

	public void setFirstStartedAt(LocalDate firstStartedAt) {
		this.firstStartedAt = firstStartedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pid, host, port, appName, springBootVersion, appVersion, lastStartedAt, firstStartedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ServerInfo other = (ServerInfo) obj;
		return Objects.equals(this.host, other.host)
				&& Objects.equals(this.port, other.port)
				&& Objects.equals(this.pid, other.pid)
				&& Objects.equals(this.appName, other.appName)
				&& Objects.equals(this.springBootVersion, other.springBootVersion)
				&& Objects.equals(this.appVersion, other.appVersion)
				&& Objects.equals(this.lastStartedAt, other.lastStartedAt)
				&& Objects.equals(this.firstStartedAt, other.firstStartedAt);
	}

	@Override
	public String toString() {
		return "ServerInfo{" + "id='" + id + '\'' + ", pid='" + pid + '\'' + ", appName='" + appName + '\''
				+ ", host='" + host + '\''+ ", port='" + port + '\'' + ", springBootVersion='" +springBootVersion + '\''
				+ ", appVersion='" + appVersion + '\'' + ", lastStartedAt='"
				+ lastStartedAt + '\'' + ", firstStartedAt=" + firstStartedAt
				+ '}';
	}
}
