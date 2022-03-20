package ru.digitalleague;

import org.junit.*;
import ru.digitalleague.storage_example.Storage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class MyTests {
    //default storage config
    private static final int STORAGE_SIZE = 3;
    private static final int ONE_ITEM_AMOUNT = 10;
    private static final int appleAmount = 3;
    private static final int milkAmount = ONE_ITEM_AMOUNT;
    private static final String errMsgMask = "==> ? \n==============================================================\n";
    private static final List<String> possibleNames= new ArrayList<>(Arrays.asList(
            "apple", "milk", "banana", "012345", "Smth"
    ));

    @Before
    public void setUp(){
        System.out.println("=============================================================");
        Storage.addObject("apple", appleAmount);      //do not change product's names! they re in tests as default
        Storage.addObject("milk", milkAmount);        //=maxValue for Item
    }

    @After
    public void reinitStorage(){
        //del already created items in @BeforeEach
        possibleNames.forEach(Storage::removeObject);
        Storage.showAllStorage();
    }


    //========================================================================================
    //*******   addObject()   **********
    @Test
    public void addNew() {
        String product = "banana";
        int value = 10;
        Storage.addObject(product, value);
        Assert.assertTrue("True? Storage.isInStock(product)&Storage.getProductAmount(product)",
                Storage.isInStock(product)&Storage.getProductAmount(product)==value);
        Storage.removeObject(product);      //clean for other tests
    }

    @Test
    public void addNewOnlyNumericName() {
        String product = "012345";
        int value = 1;
        Storage.addObject(product, value);
        Assert.assertFalse("U can add to a storage product with only numeric name like 012345",
                Storage.isInStock(product));
        Storage.removeObject(product);      //clean for other tests
    }

    @Test
    public void addNewNegativeValue() {
        String product = "banana";
        int value = -10;
        Storage.addObject(product, value);
        Assert.assertFalse("U can add negative product value like -10!",
                Storage.isInStock(product));
        Storage.removeObject(product);      //clean for other tests
    }

    @Test
    public void addCurent() {
        String product = "apple";
        int value = 2;
        Storage.addObject(product, value);
        Assert.assertEquals(errMsgMask.replace("?", "Storage not correct value when add Current one"),
                appleAmount+value, Storage.getProductAmount(product));
    }

    @Test
    public void addCurentWithUpperCase() {
        String product = "ApPle";
        int value = 2;
        Storage.addObject(product, value);
        int productAmount = Storage.getProductAmount(product);
        Storage.removeObject(product);
        Assert.assertEquals(errMsgMask.replace("?", "Seems like <APPLE> has to be the same as <apple>, but it is NOT [look for apple]"),
                appleAmount+value, Storage.getProductAmount("apple"));
        Assert.assertEquals(errMsgMask.replace("?", "Seems like <APPLE> has to be the same as <apple>, but it is NOT [look for ApPle]"),
                appleAmount+value, productAmount);
    }

    @Test
    public void addCurentNegativeValue() {
        String product = "milk";
        int value = -5;
        Storage.addObject(product, value);
        Assert.assertEquals(errMsgMask.replace("?", "Product value don't decrease with negative value"),
                milkAmount+value, Storage.getProductAmount(product));
    }

    @Test
    public void addCurentNegativeValueMoreThenExist() {
        String product = "apple";
        int value = -5;
        Storage.addObject(product, value);
        Assert.assertFalse(errMsgMask.replace("?", "Product still exists after adding negative value more then its value. Products with negative value exist on storage!"),
                Storage.isInStock(product));
    }

    @Test
    public void addCurentNegativeLikeExistValue() {
        String product = "apple";
        int value = -appleAmount;
        Storage.addObject(product, value);
        Assert.assertFalse(errMsgMask.replace("?", "Product still exists after adding negative value same as it was. Products with 0 value exist on storage!"),
                Storage.isInStock(product));
    }

    @Test
    public void addCurentIfMax() {
        String product = "milk";
        int value = 5;
        Storage.addObject(product, value);
        Assert.assertEquals(errMsgMask.replace("?", "seems posible to add more then max value!"),
                ONE_ITEM_AMOUNT, Storage.getProductAmount(product));
    }

    @Test
    public void addNewNulValue() {
        String product = "banana";
        int value = 0;
        Storage.addObject(product,value);
        Assert.assertFalse(errMsgMask.replace("?", "U can add product with 0 value!"),
                Storage.isInStock(product));
        Storage.removeObject(product);  //clean it
    }

    @Test
    public void addNewVerylongProductName() {
        StringBuilder product = new StringBuilder("a");
        for (int i=0; i<1000; i++) product.append("a");
        int value = 1;
        Storage.addObject(product.toString(),value);
        Boolean isInStock = Storage.isInStock(product.toString());
        Storage.removeObject(product.toString());
        assertFalse(errMsgMask.replace("?", "U can add product with unreal long name till Java no memory error!"),
                isInStock);
    }
    //=========================================================================================
    //=========================================================================================
    //*******   removeObject()   **********
    @Test
    public void removeExist() {
        String product = "milk";
        Storage.removeObject(product);
        Assert.assertFalse(errMsgMask.replace("?", "Product is not removed!"),
                Storage.isInStock(product));
    }

    @Test
    public void removeNotExist() {
        String product = "Smth";
        Storage.removeObject(product);
        Assert.assertFalse(errMsgMask.replace("?", "Product can appear when it is removed.."),
                Storage.isInStock(product));
    }

    //=========================================================================================
    //=========================================================================================
    //*******   showAllStorage()   **********
    @Test
    public void showProductsNoFail() {
        //u can't see all products on a storage
        Storage.showAllStorage();
    }
    //=========================================================================================
    //=========================================================================================
    //*******   findObject()   **********
    @Test
    public void findObjectIfExist() {
        // Exist products cant be find
        String product = "milk";
        Storage.findObject(product);
    }

    @Test
    public void findObjectIfNotExist() {
        // Problems with check not exist products
        String product = "Smth";
        Storage.findObject(product);
    }
    //=========================================================================================
    //=========================================================================================
    //*******   isInStock()   **********
    @Test
    public void isInStockIfItIs() {
        String product = "milk";
        Assert.assertTrue(errMsgMask.replace("?", "Exist products cant be checked"),
                Storage.isInStock(product));
    }

    @Test
    public void isInStockIfItIsNot() {
        String product = "Smth";
        Assert.assertFalse(errMsgMask.replace("?", "Not exist products cant be checked"),
                Storage.isInStock(product));
    }
    //=========================================================================================
    //=========================================================================================
    //*******   checkFreePlaces()   **********
    @Test
    public void checkFreePlacesIfProducts() {
        //u can't check free places on a storage
        Storage.checkFreePlaces();
    }

    @Test
    public void checkFreePlacesIfEmptyStorage() {
        //u can't check free places on a storage
        Storage.checkFreePlaces();
    }
    //=========================================================================================
    //=========================================================================================
    //*******   checkFreePlaces()   **********
    @Test
    public void getFreePlacesIfProducts() {
        //u can't get free places on a storage
        Storage.getFreePlaces();
    }

    @Test
    public void getFreePlacesIfEmptyStorage() {
        //u can't get free places on a storage if no products
        reinitStorage();
        Storage.getFreePlaces();
    }
    //=========================================================================================
    //=========================================================================================
    //*******   getProductAmount()   **********
    @Test
    public void getProductAmountIfExist() {
        String product = "milk";
        Assert.assertEquals(errMsgMask.replace("?", "can't get amount of existed product"),
                milkAmount, Storage.getProductAmount(product));
    }

    @Test
    public void getProductAmountIfNotExist() {
        String product = "Smth";
        Assert.assertEquals(errMsgMask.replace("?", "can't get amount of existed product"),
                0, Storage.getProductAmount(product));
    }
    //=========================================================================================

}
