public class Aircraft {

    private long id;
    private String name;
    private String fullNamePilot;

    public Aircraft () {

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

    public String getFullNamePilot() {
        return fullNamePilot;
    }

    public void setFullNamePilot(String fullNamePilot) {
        this.fullNamePilot = fullNamePilot;
    }

    @Override
    public String toString() {
        return "Aircraft{ " +
                id +
                ' ' + name + '\'' +
                ' ' + fullNamePilot + '\'' +
                '}';
    }

}
