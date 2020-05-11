import org.apache.derby.client.am.SqlException;
import org.junit.Test;
import products.DB;
import products.Product;

import java.util.ArrayList;

public class testDB {
    @Test
    public void testSingletone() throws Exception{
        DB db1 = DB.getInstance();
        DB db2 = DB.getInstance();
        System.out.println("A két létrehozott adatbázis megegyezik?");
        System.out.println(db1.equals(db2));
    }
    @Test
    public void testSelectProduct() throws SqlException, Exception{
        DB db = DB.getInstance();
        Product product = new Product("Tej", "123050", "500", "50");
        db.addProduct(product);
        Product act = db.selectProduct(product);
        System.out.println(act);
    }
    @Test
    public void testGetAllProduct() throws SqlException, Exception{
        DB db = DB.getInstance();
        Product product = new Product("Tej", "12350", "500", "50");
        Product product1 = new Product("Vaj", "123501", "300", "10");
        db.addProduct(product);
        db.addProduct(product1);
        ArrayList<Product> products= db.getAllProduct();
        for (int i = 0; i < products.size();i++){
            System.out.println(products.get(i));
        }
    }
    @Test
    public void testRemoveProduct() throws SqlException, Exception{
        DB db = DB.getInstance();
        Product product = new Product("Tej", "12350", "500", "50");
        db.addProduct(product);
        System.out.println(db.selectProduct(product));
        db.removeProduct(product);
        System.out.println("Törlés után: " + db.selectProduct(product));
    }
    @Test
    public void testUpdateProduct() throws SqlException, Exception{
        DB db = DB.getInstance();
        Product product = new Product("Tej", "12350", "500", "50");
        db.addProduct(product);
        System.out.println(db.selectProduct(product));
        Product productchange = new Product("Vaj", "12350", "300", "10");
        db.updateProduct(productchange);
        System.out.println("Update után: " + db.selectProduct(product));
    }
}
