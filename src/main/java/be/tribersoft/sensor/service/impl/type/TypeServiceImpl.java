package be.tribersoft.sensor.service.impl.type;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.type.TypeFacade;
import be.tribersoft.sensor.domain.api.type.TypeMessage;
import be.tribersoft.sensor.service.api.type.TypeService;

@Named
@Transactional
public class TypeServiceImpl implements TypeService {

	@Inject
	private TypeFacade typeFacade;

	@Override
	public void save(TypeMessage typeCreate) {
		typeFacade.save(typeCreate);
	}

	@Override
	public void update(String id, Long version, TypeMessage typeMessage) {
		typeFacade.update(id, version, typeMessage);
	}

	@Override
	public void delete(String id, Long version) {
		typeFacade.delete(id, version);
	}

}
