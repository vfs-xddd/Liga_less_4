package homework;

import java.util.List;

public abstract class Storage {
    protected int capacity;
    protected int max_capacity = 100;
    protected List<Product> product_list;


    public abstract void add_product(List<String> user_values);

    public abstract void del_product(String name, int weight);

    protected abstract void get_product_list();

    public abstract void show_product_list();

}
