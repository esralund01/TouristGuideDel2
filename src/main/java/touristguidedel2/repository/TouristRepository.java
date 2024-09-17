package touristguidedel2.repository;

import touristguidedel2.model.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

public class TouristRepository {

    private final List<TouristAttraction> touristAttractions;

    public TouristRepository() {

        touristAttractions = new ArrayList<>(List.of(
            new TouristAttraction("SMK","Museum for Kunst"),
            new TouristAttraction("Odense Zoo","Europas bedste zoo"),
            new TouristAttraction("Dyrehaven","Naturpark med skovområder"),
            new TouristAttraction("Tivoli","Forlystelsespark i København centrum")
        ));
    }

    public List<TouristAttraction> getAllAttractions() {
        return List.copyOf(touristAttractions);
    }

    public TouristAttraction getAttractionByName(String name) {
        for (TouristAttraction touristAttraction : touristAttractions){
            if (touristAttraction.getName().equals(name)){
                return touristAttraction;
            }
        }
        return null;
    }
}