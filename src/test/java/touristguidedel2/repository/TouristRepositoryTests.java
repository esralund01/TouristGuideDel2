package touristguidedel2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import touristguidedel2.model.TouristAttraction;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("h2")
public class TouristRepositoryTests {

    @Autowired
    TouristRepository repository;

    @Test
    void getCities() {
        SortedSet<String> expected = new TreeSet<>(List.of("Albertslund", "Kongens Lyngby", "København", "Odense"));
        assertEquals(expected, repository.getCities());
    }

    @Test
    void getTags() {
        SortedSet<String> expected = new TreeSet<>(List.of("Børnevenlig", "Gratis", "Kunst", "Museum", "Natur"));
        assertEquals(expected, repository.getTags());
    }

    @Test
    void crud() {
        assertTrue(repository.addTouristAttraction(new TouristAttraction("Test", "TestD", "Odense", List.of("Natur", "Kunst"))));
        //assertTrue(repository.touristAttractionExists("Test"));
        //assertEquals("TestD", repository.getAttractionByName("Test").getDescription());
        //assertEquals("TestD", repository.getAllAttractions().getFirst().getDescription());
        repository.deleteAttraction("Not in here");
        repository.deleteAttraction("Test");
        //assertTrue(repository.touristAttractionExists("Test"));
        // and the update method
    }
}