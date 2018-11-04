package sailorsmarketplace;

import com.company.sailorsmarketplace.dao.EventRepository;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.google.inject.Singleton;
import javax.inject.Inject;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Singleton
public class EventTestData {
    private final EventRepository eventRepo;

    @Inject
    public EventTestData(final EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public Event createTestEvent(final User user) {
        return eventRepo.save(new Event(
                user,
                randomAlphabetic(8),
                randomAlphanumeric(25))
                                      .addUser(user));
    }

    public void removeTestEvent(Event event) {
        eventRepo.delete(event);
    }
}