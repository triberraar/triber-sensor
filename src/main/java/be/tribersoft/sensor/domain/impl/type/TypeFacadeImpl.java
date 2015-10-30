package be.tribersoft.sensor.domain.impl.type;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.type.TypeFacade;
import be.tribersoft.sensor.domain.api.type.TypeMessage;

@Named
public class TypeFacadeImpl implements TypeFacade {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private TypeFactory typeFactory;
	@Inject
	private TypeUpdater typeUpdater;

	@Override
	public void save(TypeMessage typeMessage) {
		typeRepository.save(typeFactory.create(typeMessage));
	}

	@Override
	public void update(String id, Long version, TypeMessage typeMessage) {
		TypeEntity type = typeRepository.getByIdAndVersion(id, version);
		typeUpdater.update(type, typeMessage);
	}

	@Override
	public void delete(String id, Long version) {
		TypeEntity type = typeRepository.getByIdAndVersion(id, version);
		typeRepository.delete(type);
	}

}
