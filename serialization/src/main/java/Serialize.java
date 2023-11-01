import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Serialize {

    private static final int COUNT = 1000000;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void serial(Object object, String fileName) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(fileName), object);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            passengers.add(Gener.generatePassenger());
        }

        serial(passengers, "airport.json");
    }
}
