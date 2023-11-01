public class Baggage {

    private long id;
    private double weight;
    private int count;

    public Baggage () {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Baggage{" +
                id +
                ' ' + weight +
                ' ' + count +
                '}';
    }

}
