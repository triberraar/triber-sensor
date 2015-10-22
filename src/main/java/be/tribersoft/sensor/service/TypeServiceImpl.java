package be.tribersoft.sensor.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.Type;
import be.tribersoft.sensor.domain.api.TypeFacade;
import be.tribersoft.sensor.domain.api.TypePatch;
import be.tribersoft.sensor.domain.api.TypeUpdate;
import be.tribersoft.sensor.service.api.TypeService;

@Named
@Transactional
public class TypeServiceImpl implements TypeService {

	@Inject
	private TypeFacade typeFacade;

	@Override
	public void save(Type type) {
		typeFacade.save(type);
	}

	@Override
	public void update(String uuid, Long version, TypeUpdate typeUpdate) {
		typeFacade.update(uuid, version, typeUpdate);
	}

	@Override
	public void patch(String uuid, Long version, TypePatch typePatch) {
		typeFacade.patch(uuid, version, typePatch);
	}

	@Override
	public void delete(String uuid, Long version) {
		typeFacade.delete(uuid, version);
	}

}
