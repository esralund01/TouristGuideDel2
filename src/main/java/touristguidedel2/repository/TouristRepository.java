package touristguidedel2.repository;

import touristguidedel2.model.TouristAttraction;

import java.util.*;

public class TouristRepository {

    private final List<TouristAttraction> touristAttractions;
    private final SortedSet<String> cities;
    private final SortedSet<String> tags;

    public TouristRepository() {

        touristAttractions = new ArrayList<>(List.of(
            new TouristAttraction("SMK","Museum for Kunst","København",List.of("Museum", "Kunst")),
            new TouristAttraction("Odense Zoo","Europas bedste zoo","Odense", List.of("Børnevenlig")),
            new TouristAttraction("Dyrehaven","Naturpark med skovområder","Kongens Lyngby",List.of("Natur", "Gratis")),
            new TouristAttraction("Tivoli","Forlystelsespark i København centrum","København",List.of("Børnevenlig"))
        ));

        cities = new TreeSet<>(List.of("Albertslund", "Kongens Lyngby", "København", "Odense"));
        tags = new TreeSet<>(List.of("Museum", "Kunst", "Børnevenlig", "Natur", "Gratis"));
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

    public SortedSet<String> getCities() {
        return cities;
    }

    public SortedSet<String> getTags() {
        return tags;
    }

    public void addTouristAttraction(TouristAttraction touristAttraction) {
        if (touristAttraction.getName().isBlank()) {
            throw new IllegalStateException("Tourist Attraction name cannot be blank.");
        } else {
            touristAttractions.add(touristAttraction);
        }
    }

    public void updateTouristAttraction(TouristAttraction touristAttraction) {
        for (TouristAttraction ta : touristAttractions) {
            if (ta.getName().equals(touristAttraction.getName())) {
                touristAttractions.remove(ta);
                touristAttractions.add(touristAttraction);
            }
        }
    }

    public void deleteAttraction(String name) {
        touristAttractions.removeIf(touristAttraction -> touristAttraction.getName().equals(name));
    }
}