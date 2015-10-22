package be.tribersoft.sensor.domain;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.Type;
import be.tribersoft.sensor.domain.api.TypeFacade;
import be.tribersoft.sensor.domain.api.TypePatch;
import be.tribersoft.sensor.domain.api.TypeUpdate;
import be.tribersoft.sensor.domain.api.TypeUpdater;

@Named
public class TypeFacadeImpl implements TypeFacade {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private TypeFactory typeFactory;
	@Inject
	private TypeUpdater typeUpdater;

	@Override
	public void save(Type type) {
		typeRepository.save(typeFactory.create(type));
	}

	@Override
	public void update(String uuid, Long version, TypeUpdate typeUpdate) {
		TypeEntity type = typeRepository.getByUuidAndVersion(uuid, version);
		typeUpdater.update(type, typeUpdate);
	}

	@Override
	public void patch(String uuid, Long version, TypePatch typePatch) {
		TypeEntity type = typeRepository.getByUuidAndVersion(uuid, version);
		typeUpdater.patch(type, typePatch);
	}

	@Override
	public void delete(String uuid, Long version) {
		TypeEntity type = typeRepository.getByUuidAndVersion(uuid, version);
		typeRepository.delete(type);
	}

}
