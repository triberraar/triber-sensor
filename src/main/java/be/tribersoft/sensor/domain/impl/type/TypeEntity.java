package be.tribersoft.sensor.domain.impl.type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.sensor.domain.api.type.Type;

@Entity
public class TypeEntity implements Type {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(nullable = false)
	private String name;

	TypeEntity() {
	}

	public TypeEntity(String name) {
		this.name = name;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

}
