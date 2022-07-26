package entity;

public class Child {
    private int id;
    private String name;
    private int age;
    private Wife wife;

    public Child() {
    }

    public Child(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Child(int id, String name, int age, Wife wife) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wife = wife;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", wife=" + wife +
                '}';
    }
}
