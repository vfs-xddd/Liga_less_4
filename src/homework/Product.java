package homework;

public class Product {
    protected String name;
    protected String description;
    protected int weight;

    Product(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    Product(String name, int weight, String description) {
        this(name, weight);
        this.description = description;
    }
}
