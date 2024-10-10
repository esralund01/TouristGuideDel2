package touristguidedel2.repository;

import touristguidedel2.model.TouristAttraction;

import java.sql.*;
import java.util.*;

public class TouristRepository {

    private String url; // TODO: Skal instantieres

    public List<TouristAttraction> getAllAttractions() {

        List<TouristAttraction> touristAttractions = new ArrayList<>();
        String sql = """
            SELECT
                touristAttractions.attractionName as attractionName,
                touristAttractions.attractionDescription as attractionDescription,
                cities.cityName as cityName,
            FROM touristAttractions JOIN cities ON touristAttractions.city = cities.cityId""";

        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String name = resultSet.getString("attractionName");
                String description = resultSet.getString("attractionDescription");
                String cityName = resultSet.getString("cityName");

                touristAttractions.add(new TouristAttraction(name, description, cityName, new ArrayList<>()));
            }

        } catch (SQLException ignored) {}

        return touristAttractions;
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
        touristAttractions.add(touristAttraction);
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

    public boolean touristAttractionExists(String name) {
        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}