package be.tribersoft.sensor.domain.impl.reading;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.event.EventSubject;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.impl.event.EventPersister;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;

@Entity(name = "reading")
@EntityListeners(value = EventPersister.class)
public class ReadingEntity implements Reading {
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

	@Column(nullable = false)
	private BigDecimal value;

	@ManyToOne(optional = false)
	private SensorEntity sensor;

	ReadingEntity() {
	}

	public ReadingEntity(BigDecimal value, SensorEntity sensor) {
		this.creationDate = DateFactory.generate();
		this.value = value;
		this.sensor = sensor;
	}

	Date getCreationDate() {
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
	public BigDecimal getValue() {
		return value;
	}

	@Override
	public SensorEntity getSensor() {
		return sensor;
	}

	@Override
	public EventSubject getEventSubject() {
		return EventSubject.READING;
	}

}
