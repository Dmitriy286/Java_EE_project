package entity;

public class Human {
    private int id;
    private String name;
    private int money;

    public Human() {
    }

    public Human(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public Human(String name) {
        this.name = name;
        this.money = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
