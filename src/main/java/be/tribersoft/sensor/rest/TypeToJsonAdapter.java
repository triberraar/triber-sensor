package be.tribersoft.sensor.rest;

import org.springframework.hateoas.core.Relation;

import be.tribersoft.sensor.domain.api.Type;

@Relation(collectionRelation = "types", value = "type")
public class TypeToJsonAdapter {

	private Type type;

	public TypeToJsonAdapter(Type type) {
		this.type = type;
	}

	public String getId() {
		return type.getId();
	}

	public Long getVersion() {
		return type.getVersion();
	}

	public String getName() {
		return type.getName();
	}

}
