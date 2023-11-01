import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Deserialize {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> deserializeList(TypeReference<List<T>> typeReference, String filename) throws IOException {
        return objectMapper.readValue(new File(filename), typeReference);
    }

    public static void main(String[] args) throws IOException {
        List<Passenger> passengers = deserializeList(new TypeReference<List<Passenger>>() {}, "airport.json");
        passengers.forEach(System.out::println);
    }
}
