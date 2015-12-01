package be.tribersoft.sensor.domain.api.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EventModeTest {

	@Test
	public void CREATEDHAsCreatedAsMessage() {
		assertThat(EventMode.CREATED.getMessage()).isEqualTo("event.created");
	}

	@Test
	public void UPDATEDHAsUpdatedAsMessage() {
		assertThat(EventMode.UPDATED.getMessage()).isEqualTo("event.updated");
	}

	@Test
	public void DeletedHAsDeletedAsMessage() {
		assertThat(EventMode.DELETED.getMessage()).isEqualTo("event.deleted");
	}
}
