package be.tribersoft.sensor.domain.impl.type;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.type.TypeCreate;
import be.tribersoft.sensor.domain.api.type.TypeFacade;
import be.tribersoft.sensor.domain.api.type.TypePatch;
import be.tribersoft.sensor.domain.api.type.TypeUpdate;
import be.tribersoft.sensor.domain.api.type.TypeUpdater;

@Named
public class TypeFacadeImpl implements TypeFacade {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private TypeFactory typeFactory;
	@Inject
	private TypeUpdater typeUpdater;

	@Override
	public void save(TypeCreate typeCreate) {
		typeRepository.save(typeFactory.create(typeCreate));
	}

	@Override
	public void update(String id, Long version, TypeUpdate typeUpdate) {
		TypeEntity type = typeRepository.getByIdAndVersion(id, version);
		typeUpdater.update(type, typeUpdate);
	}

	@Override
	public void patch(String id, Long version, TypePatch typePatch) {
		TypeEntity type = typeRepository.getByIdAndVersion(id, version);
		typeUpdater.patch(type, typePatch);
	}

	@Override
	public void delete(String id, Long version) {
		TypeEntity type = typeRepository.getByIdAndVersion(id, version);
		typeRepository.delete(type);
	}

}
