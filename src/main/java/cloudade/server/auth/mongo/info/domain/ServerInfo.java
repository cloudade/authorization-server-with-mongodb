package cloudade.server.auth.mongo.info.domain;

import java.util.Objects;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ServerInfo {

	@Id
	private String id;
	private String pid;
	private String version;
	private LocalDate lastStartedAt;
	private LocalDate firstStartedAt;

	public ServerInfo(final String id, final String pid, final String version,
			final LocalDate lastStartedAt, final LocalDate firstStartedAt) {
		this.id = id;
		this.pid = pid;
		this.version = version;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
		return Objects.hash(id, pid, version, lastStartedAt, firstStartedAt);
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
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.pid, other.pid)
				&& Objects.equals(this.version, other.version)
				&& Objects.equals(this.lastStartedAt, other.lastStartedAt)
				&& Objects.equals(this.firstStartedAt, other.firstStartedAt);
	}

	@Override
	public String toString() {
		return "ServerInfo{" + "id='" + id + '\'' + ", pid='" + pid + '\''
				+ ", version='" + version + '\'' + ", lastStartedAt='"
				+ lastStartedAt + '\'' + ", firstStartedAt=" + firstStartedAt
				+ '}';
	}
}
