package edu.mta.groupa.planner;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.repository.AccommodationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccommodationTest {

    @Autowired
    private AccommodationRepository accommodationRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void whenFindByTitle_thenReturnAccommodation() {
        // given
    	Accommodation accom = new Accommodation();
    	accom.setTitle("Test title");
    	accom.setCheckIn(new Date());
    	accom.setCheckOut(new Date());
        entityManager.persist(accom);
        entityManager.flush();
     
        // when
        Accommodation found = accommodationRepository.findByTitle(accom.getTitle()).get(0);
     
        // then
        Assert.assertEquals(found.getTitle(), accom.getTitle());
    }
}
