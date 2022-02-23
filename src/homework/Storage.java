package homework;

import java.util.List;

public abstract class Storage {
    protected int capacity;
    protected List<Product> product_list;

    public abstract void add_product();

    public abstract void del_product();

    protected abstract void get_product_list();

    public abstract void show_product_list();

}
