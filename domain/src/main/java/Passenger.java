public class Passenger {

    private String fullName;
    private String phone;
    private String passport;
    private Flight fly;
    private Ticket pass;
    private Baggage bag;

    public Passenger (){

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Flight getFly() {
        return fly;
    }

    public void setFly(Flight fly) {
        this.fly = fly;
    }

    public Ticket getPass() {
        return pass;
    }

    public void setPass(Ticket pass) {
        this.pass = pass;
    }

    public Baggage getBag() {
        return bag;
    }

    public void setBag(Baggage bag) {
        this.bag = bag;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                fullName + '\'' +
                ' ' + phone +
                ' ' + passport + '\'' +
                ' ' + fly.toString() +
                ' ' + pass.toString() +
                ' ' + bag.toString() +
                '}';
    }

}
