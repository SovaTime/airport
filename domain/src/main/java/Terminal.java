public class Terminal {

    private long id;
    private String name;

    public Terminal () {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                id +
                ' ' + name + '\'' +
                '}';
    }

}
