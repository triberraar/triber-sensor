package be.tribersoft.sensor.service.impl.type;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.type.TypeCreate;
import be.tribersoft.sensor.domain.api.type.TypeFacade;
import be.tribersoft.sensor.domain.api.type.TypePatch;
import be.tribersoft.sensor.domain.api.type.TypeUpdate;
import be.tribersoft.sensor.service.api.type.TypeService;

@Named
@Transactional
public class TypeServiceImpl implements TypeService {

	@Inject
	private TypeFacade typeFacade;

	@Override
	public void save(TypeCreate typeCreate) {
		typeFacade.save(typeCreate);
	}

	@Override
	public void update(String id, Long version, TypeUpdate typeUpdate) {
		typeFacade.update(id, version, typeUpdate);
	}

	@Override
	public void patch(String id, Long version, TypePatch typePatch) {
		typeFacade.patch(id, version, typePatch);
	}

	@Override
	public void delete(String id, Long version) {
		typeFacade.delete(id, version);
	}

}
