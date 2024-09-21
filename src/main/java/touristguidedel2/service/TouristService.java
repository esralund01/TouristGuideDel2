package touristguidedel2.service;

import touristguidedel2.model.TouristAttraction;
import touristguidedel2.repository.TouristRepository;

import java.util.List;
import java.util.SortedSet;

public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService() {
        touristRepository = new TouristRepository();
    }

    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.getAllAttractions();
    }

    public TouristAttraction getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    public SortedSet<String> getCities() {
        return touristRepository.getCities();
    }

    public SortedSet<String> getTags() {
        return touristRepository.getTags();
    }
}