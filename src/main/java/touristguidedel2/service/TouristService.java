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

    public void addTouristAttraction(TouristAttraction touristAttraction) {
        touristRepository.addTouristAttraction(touristAttraction);
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

    public void updateTouristAttraction(TouristAttraction touristAttraction) {
        touristRepository.updateTouristAttraction(touristAttraction);
    }

    public void deleteAttraction(String name) {
        touristRepository.deleteAttraction(name);
    }

    public boolean touristAttractionExists(String name) {
        return touristRepository.touristAttractionExists(name);
    }

}