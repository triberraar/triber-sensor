package be.tribersoft.sensor.domain.impl.type;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.domain.api.type.TypeFacade;
import be.tribersoft.sensor.domain.api.type.TypeMessage;
import be.tribersoft.sensor.domain.api.type.exception.TypeStillInUseException;

@Named
public class TypeFacadeImpl implements TypeFacade {

	@Inject
	private TypeRepositoryImpl typeRepository;
	@Inject
	private TypeFactory typeFactory;
	@Inject
	private TypeUpdater typeUpdater;
	@Inject
	private SensorRepository sensorRepository;

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
		if (sensorRepository.typeInUse(id)) {
			throw new TypeStillInUseException();
		}
		TypeEntity type = typeRepository.getByIdAndVersion(id, version);
		typeRepository.delete(type);
	}

}
