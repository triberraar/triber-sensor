package be.tribersoft.sensor.domain.impl.unit;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.unit.Unit;

@Entity(name = "unit")
public class UnitEntity implements Unit {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(nullable = false, length = 256)
	private String name;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(length = 128)
	private String symbol;

	UnitEntity() {
	}

	public UnitEntity(String name) {
		this.name = name;
		this.creationDate = DateFactory.generate();
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

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public Optional<String> getSymbol() {
		return Optional.ofNullable(symbol);
	}

	public void setSymbol(Optional<String> symbol) {
		if (symbol.isPresent()) {
			this.symbol = symbol.get();
		} else {
			this.symbol = null;
		}
	}

}
