public class Flight {

    private long id;
    private Aircraft plane;
    private Terminal name;

    public Flight () {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Aircraft getPlane() {
        return plane;
    }

    public void setPlane(Aircraft plane) {
        this.plane = plane;
    }

    public Terminal getName() {
        return name;
    }

    public void setName(Terminal name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Flight{" +
                id +
                ' ' + plane.toString() +
                ' ' + name.toString() +
                '}';
    }

}
