package Models;

public class People {

    private String name;
    private Integer age;
    private String sex;

    //создали приватные переменные

    public  People (String name, Integer age, String sex ){
        this.name = name;
        this.age = age;
        this.sex = sex;

    }

    public People() {

    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }


}
