package touristguidedel2.repository;

import org.springframework.stereotype.Repository;
import touristguidedel2.model.TouristAttraction;

import java.sql.*;
import java.util.*;

@Repository
public class TouristRepository {

    // TODO: delete old attribute
    private final List<TouristAttraction> touristAttractions = new ArrayList<>();

    // TODO: replace with application properties
    private String database = System.getenv("DB_URL");
    private String username = System.getenv("DB_USER");
    private String password = System.getenv("DB_PASSWORD");

    public TouristRepository() {
    }

    public List<TouristAttraction> getAllAttractions() {

        List<TouristAttraction> touristAttractions = new ArrayList<>();

        String sql = """
                SELECT
                	attractions.attractionName as name,
                    attractions.attractionDescription as description,
                    cities.cityName as city,
                    tags.tagName as tag
                FROM attractions_tags, attractions, tags, cities
                WHERE attractions_tags.attractionId = attractions.attractionId
                	AND attractions_tags.tagId = tags.tagId
                    AND attractions.city = cities.cityId;""";

        try (Connection connection = DriverManager.getConnection(database, username, password)) {

            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            TouristAttraction previous = new TouristAttraction("", "", "", new ArrayList<>());

            while (resultSet.next()) {

                String name = resultSet.getString("name");

                if (name.equals(previous.getName())) {

                    String tag = resultSet.getString("tag");
                    previous.getTags().add(tag);

                } else {
                    String description = resultSet.getString("description");
                    String city = resultSet.getString("city");
                    String tag = resultSet.getString("tag");

                    List<String> tags = new ArrayList<>();
                    tags.add(tag);
                    previous = new TouristAttraction(name, description, city, tags);
                    touristAttractions.add(previous);
                }
            }

        } catch (SQLException ignored) {}

        return touristAttractions;
    }

    public TouristAttraction getAttractionByName(String name) {

        String sql = """
                SELECT
                	attractions.attractionName as name,
                    attractions.attractionDescription as description,
                    cities.cityName as city,
                    tags.tagName as tag
                FROM attractions_tags, attractions, tags, cities
                WHERE attractions_tags.attractionId = attractions.attractionId
                	AND attractions_tags.tagId = tags.tagId
                    AND attractions.city = cities.cityId
                    AND attractions.attractionName = ?;""";

        try (Connection connection = DriverManager.getConnection(database, username, password)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            // First row
            if (!resultSet.next()) {
                return null;
            }
            String dbName = resultSet.getString("name");
            String description = resultSet.getString("description");
            String city = resultSet.getString("city");
            String firstTag = resultSet.getString("tag");
            List<String> tags = new ArrayList<>();
            tags.add(firstTag);

            // Other rows
            while (resultSet.next()) {
                String nextTag = resultSet.getString("tag");
                tags.add(nextTag);
            }

            return new TouristAttraction(dbName, description, city, tags);

        } catch (SQLException ignored) {
            return null;
        }
    }

    public SortedSet<String> getCities() {

        SortedSet<String> cities = new TreeSet<>();

        String sql = """
                SELECT cityName
                FROM cities""";

        try (Connection connection = DriverManager.getConnection(database, username, password)) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                cities.add(resultSet.getString("cityName"));
            }
        } catch (SQLException ignored) {}

        return cities;
    }

    public SortedSet<String> getTags() {

        SortedSet<String> tags = new TreeSet<>();

        String sql = """
                SELECT tagName
                FROM tags""";

        try (Connection connection = DriverManager.getConnection(database, username, password)) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                tags.add(resultSet.getString("tagName"));
            }
        } catch (SQLException ignored) {}

        return tags;
    }

    public boolean addTouristAttraction(TouristAttraction touristAttraction) {

        String name = touristAttraction.getName();
        String description = touristAttraction.getDescription();
        String cityName = touristAttraction.getCity();
        List<String> tags = touristAttraction.getTags();

        String getCityId = """
                SELECT cityId
                FROM cities
                WHERE cityName = ?;""";

        String insertAttraction = """
                INSERT INTO attractions (attractionName, attractionDescription, city)
                VALUES (?, ?, ?);""";

        String getTagId = """
                SELECT tagId
                FROM tags
                WHERE tagName = ?;""";

        String insertTag = """
                INSERT INTO attractions_tags (attractionId, tagId)
                VALUES (?, ?);""";

        try (Connection connection = DriverManager.getConnection(database, username, password)) {

            // Getting the city ID
            PreparedStatement preparedStatement1 = connection.prepareStatement(getCityId);
            preparedStatement1.setString(1, cityName);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int cityId = resultSet1.getInt("cityId");

            // Inserting the attraction
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertAttraction, Statement.RETURN_GENERATED_KEYS);
            preparedStatement2.setString(1, name);
            preparedStatement2.setString(2, description);
            preparedStatement2.setInt(3, cityId);
            int attractionsInserted = preparedStatement2.executeUpdate();

            // Tags
            int tagsInserted = 0;

            if (attractionsInserted > 0) {

                ResultSet generatedKeys = preparedStatement2.getGeneratedKeys();
                generatedKeys.next();
                int attractionId = generatedKeys.getInt(1);

                for (String tag : tags) {

                    // Getting the tag ID
                    PreparedStatement preparedStatement3 = connection.prepareStatement(getTagId);
                    preparedStatement3.setString(1, tag);
                    ResultSet resultSet3 = preparedStatement3.executeQuery();
                    resultSet3.next();
                    int tagId = resultSet3.getInt("tagId");

                    // Inserting attraction-tag connection
                    PreparedStatement preparedStatement4 = connection.prepareStatement(insertTag);
                    preparedStatement4.setInt(1, attractionId);
                    preparedStatement4.setInt(2, tagId);
                    tagsInserted += preparedStatement4.executeUpdate();
                }
            }
            return attractionsInserted == 1 && tagsInserted == tags.size();
        } catch (SQLException ignored) {
            return false;
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

    public Boolean touristAttractionExists(String name) {

        String sql = """
            SELECT attractionName
            FROM attractions
            WHERE attractionName = ?""";

        try (Connection connection = DriverManager.getConnection(database, username, password)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException ignored) {
            return null;
        }
    }
}