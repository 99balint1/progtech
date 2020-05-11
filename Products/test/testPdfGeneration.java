import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.Test;
import products.PdfGeneration;
import products.Product;


public class testPdfGeneration {
    @Test
    public void testPdfGeneration() throws Exception{
        PdfGeneration pdf = new PdfGeneration();
        ObservableList<Product> products = FXCollections.observableArrayList();
        Product product1 = new Product("Elso", "12345", "300","10");
        Product product2 = new Product("Masodik", "12312", "400","15");
        products.add(product1);
        products.add(product2);
        pdf.pdfGeneration("teszt", products);
    }
}
