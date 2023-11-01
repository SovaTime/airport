import java.util.Random;

public class Gener {

    private static final Random random = new Random();

    public static Passenger generatePassenger() {
        Passenger passenger = new Passenger();
        passenger.setFullName("Passenger " + random.nextInt(100));
        passenger.setPhone(phoneNumber());
        passenger.setPassport("Passport " + random.nextInt(100));
        passenger.setFly(generateFlight());
        passenger.setPass(generateTicket());
        passenger.setBag(generateBaggage());
        return passenger;
    }

    public static Flight generateFlight() {
        Flight flight = new Flight();
        flight.setId(random.nextLong());
        flight.setPlane(generateAircraft());
        flight.setName(generateTerminal());
        return flight;
    }

    public static Aircraft generateAircraft() {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(random.nextLong());
        aircraft.setName("Aircraft " + random.nextInt(100));
        aircraft.setFullNamePilot("Pilot " + random.nextInt(100));
        return aircraft;
    }

    public static Terminal generateTerminal() {
        Terminal terminal = new Terminal();
        terminal.setId(random.nextLong());
        terminal.setName("Terminal " + random.nextInt(100));
        return terminal;
    }

    public static Ticket generateTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(random.nextLong());
        ticket.setCost(random.nextInt(500));
        ticket.setData(dateGener());
        ticket.setCityOut("City " + random.nextInt(10));
        ticket.setCityIn("City " + random.nextInt(10));
        return ticket;
    }

    public static Baggage generateBaggage() {
        Baggage baggage = new Baggage();
        baggage.setId(random.nextLong());
        baggage.setWeight(random.nextDouble() * 100);
        baggage.setCount(random.nextInt(5));
        return baggage;
    }

    private static String phoneNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("89");
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String dateGener() {
        Random random = new Random();
        int day = random.nextInt(31) + 1; // генерируем день месяца от 1 до 31
        int month = random.nextInt(12) + 1; // генерируем месяц от 1 до 12
        int year = random.nextInt(10) + 2023; // генерируем год от 2023 до 2032
        String randomDate;
        return randomDate = String.format("%02d.%02d.%04d", day, month, year);
    }

}
