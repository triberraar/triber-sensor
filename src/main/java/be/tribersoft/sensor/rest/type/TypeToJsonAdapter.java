package be.tribersoft.sensor.rest.type;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.type.Type;

@Relation(collectionRelation = "types", value = "type")
public class TypeToJsonAdapter {

	private Type type;

	public TypeToJsonAdapter(Type type) {
		this.type = type;
	}

	@JsonProperty
	public String getId() {
		return type.getId();
	}

	@JsonProperty
	public Long getVersion() {
		return type.getVersion();
	}

	@JsonProperty
	public String getName() {
		return type.getName();
	}

}
