public class Ticket {

    private long id;
    private int cost;
    private String data;
    private String cityOut;
    private String cityIn;

    public Ticket () {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCityOut() {
        return cityOut;
    }

    public void setCityOut(String cityOut) {
        this.cityOut = cityOut;
    }

    public String getCityIn() {
        return cityIn;
    }

    public void setCityIn(String cityIn) {
        this.cityIn = cityIn;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                id +
                ' ' + cost +
                ' ' + data + '\'' +
                ' ' + cityOut + '\'' +
                ' ' + cityIn + '\'' +
                '}';
    }

}
