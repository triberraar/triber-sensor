package be.tribersoft.sensor.domain.impl.sensor;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;

@Entity(name = "sensor")
public class SensorEntity implements Sensor {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(nullable = false, length = 256)
	private String name;

	@Column(length = 4098)
	private String description;

	@ManyToOne(optional = false)
	private TypeEntity type;

	@ManyToOne(optional = false)
	private UnitEntity unit;

	SensorEntity() {
	}

	public SensorEntity(String name, TypeEntity type, UnitEntity unit) {
		this.name = name;
		this.type = type;
		this.unit = unit;
		this.creationDate = DateFactory.generate();
	}

	public void setDescription(Optional<String> description) {
		if (description.isPresent()) {
			this.description = description.get();
		} else {
			this.description = null;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TypeEntity getType() {
		return type;
	}

	@Override
	public UnitEntity getUnit() {
		return unit;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
