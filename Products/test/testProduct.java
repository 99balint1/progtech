import org.junit.Test;
import products.Product;

public class testProduct {
    @Test
    public void TestEmptyConstructor() throws Exception{
        Product product = new Product();
        System.out.println(product);
    }
    @Test
    public void TestConstructor() throws Exception{
        Product product = new Product("tej", "123459", "500", "10");
        System.out.println(product);
    }
    @Test
    public void TestGetters() throws Exception{
        Product product = new Product("vaj", "12345", "400", "6");
        System.out.println(product.getMegnevezes());
        System.out.println(product.getVonalkod());
        System.out.println(product.getAr());
        System.out.println(product.getDarab());
    }
    @Test
    public void TestSetters() throws Exception{
        Product product = new Product("vaj", "12345", "400", "6");
        product.setMegnevezes("Tej");
        product.setVonalkod("11111");
        product.setAr("1000");
        product.setDarab("0");
        System.out.println(product.getMegnevezes());
        System.out.println(product.getVonalkod());
        System.out.println(product.getAr());
        System.out.println(product.getDarab());
    }
}
