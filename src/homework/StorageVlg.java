package homework;

import java.util.*;

public class StorageVlg extends Storage{
    public boolean to_exit = false;
    protected ArrayList<Product> product_list = new ArrayList<>();

    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("================================================================================\n" +
                "Начало работы с программой склад. \n" +
                "Для вызова списка доступных команда введите help\n" +
                "Для выхода введите exit\n" +
                "================================================================================");
        while (!scanner.hasNext("exit")) {
            ArrayList <String> tokens = new ArrayList<String>(List.of(scanner.nextLine().split(" ")));
            if (!tokens.get(0).matches("add|del|show|find|more|help")) {System.out.println("Введите верную команду!"); continue;}
            if (tokens.size() > 2 && !NumbersCheck.isInt(tokens.get(2))) {System.out.println("необходимо вводить команда название товара и вес через пробел"); continue;}

            switch (tokens.get(0)) {
                case "help":
                    System.out.println("Доступные команды вводятся через пробел. Необязательные параметры в указаны []. Примеры:\n" +
                            "add товар вес [описание] ---- добавить товар на склад\n" +
                            "del товар [вес] ---- удалить товар со склада\n" +
                            "show ---- показать товары и заполняемость склада\n" +
                            "find товар ---- проверить наличие товара на складе\n" +
                            "more вес ---- добавить места на склад\n" +
                            "help ---- справка\n");
                    break;
                case "add":
                    if (tokens.size() < 3) {System.out.println("необходимо вводить команда название товара и вес через пробел"); continue;}
                    if (tokens.size() == 3) {tokens.add("Описание отсутствует");}
                    if (tokens.size() > 4) {for (int i=4; i<tokens.size(); i++) {tokens.set(3, tokens.get(3)+tokens.get(i));}}
                    add_product(tokens);
                    break;
                case "del":
                    int pr_weight = 0;
                    String pr_name = tokens.get(1);
                    if (tokens.size() > 2) {pr_weight = Integer.parseInt(tokens.get(2));}
                    del_product(pr_name, pr_weight);
                    break;
                case "show":
                    show_product_list();
                    break;
                case "find":
                    if (tokens.size()==1){
                        System.out.println("Необходимо указать команду и название товара через пробел");
                        continue;}
                    is_in_storage(tokens.get(1));
                    break;
                case "more":
                    if (tokens.size()==1){
                        System.out.println("Необходимо указать команду и кол-во добавляемого места в склад через пробел");
                        continue;}
                    if (!NumbersCheck.isInt(tokens.get(1))) {
                        System.out.println("Значение места должно быть числом");
                        continue;}
                    max_capacity += Integer.valueOf(tokens.get(1));
                    System.out.println("Поздравляю, размер склада увеличен!");
                    break;


            }

        }
        }

    @Override
    public void add_product(List<String> user_values) {
        String name = user_values.get(1);
        int weight = Integer.valueOf(user_values.get(2));
        String description = user_values.get(3);
        if (capacity + weight > max_capacity) {
            System.out.println("Добавляемое колво товара превышает вместимость склада!\n" +
                    "Возможно добавить только " + (max_capacity - capacity) + " ед., сделать это?\n" +
            "Если Да - введите yes, если нет любой символ");
            if (new Scanner(System.in).hasNext("yes")) { weight = max_capacity - capacity;}
            else {
                System.out.println("Товар не добавлен! Недостаточно вместимости склада.");
                return;
            }
        }
        product_list.add(new Product(name, weight, description));
        System.out.println("Продукт " + name + " добавлен успешно!");
        capacity += weight;
    }

    @Override
    public void del_product(String name, int weight) {
        if (product_list.isEmpty()) {
            System.out.println("На склад не добавлено ни одного товара!");
            return;
        }
        for (int i=0; i<product_list.size(); i++) {
            Product pr = product_list.get(i);
            if (pr.name.equals(name)) {
                if (weight==0 | weight >= pr.weight) {
                    product_list.remove(i);
                    capacity -= pr.weight;
                    System.out.println("Товар " + pr.name + " удален со склада полностью.");
                    return;
                }
                if (pr.weight > weight) {pr.weight-= weight;
                    System.out.println("Кол-во " + pr.name + " уменьшено до " + pr.weight);
                    capacity -= pr.weight;}
                }
        }
    }

    @Override
    protected void get_product_list() {

    }

    @Override
    public void show_product_list() {
        if (product_list.isEmpty()) {
            System.out.println("На склад не добавлено ни одного товара!");
            return;
        }
        System.out.println("Вместимость склада " + max_capacity + " ед.");
        System.out.println("Склад загружен на " + capacity*100/max_capacity + "%");
        System.out.println(to_hashMap(product_list));
    }

    public Boolean is_in_storage(String name){
        if (product_list.isEmpty()) {
            System.out.println("На склад не добавлено ни одного товара!");
            return false;}
        if (to_hashMap(product_list).containsKey(name)) {System.out.println("Товар " + name + " на складе обнаружен.");
        return true;}
        else {System.out.println("Товар " + name + " на складе отсутствует.");
        return false;}
        }



    private Map to_hashMap(ArrayList <Product> pr_list){
        HashMap <String, Integer> storage_map = new HashMap<>();
        for (Product pr: pr_list) {storage_map.put(pr.name, pr.weight);}
        return storage_map;
    }
}
