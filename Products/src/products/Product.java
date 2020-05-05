
package products;

import javafx.beans.property.SimpleStringProperty;


public class Product {
    
    private final SimpleStringProperty megnevezes;
    private final SimpleStringProperty vonalkod;
    private final SimpleStringProperty ar;
    private final SimpleStringProperty id;
    
    public Product() {
        this.megnevezes = new SimpleStringProperty("");
        this.vonalkod = new SimpleStringProperty("");
        this.ar = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }
 
    public Product(String vonalkod, String megnevezes, String ar) {
        this.vonalkod = new SimpleStringProperty(vonalkod);
        this.megnevezes = new SimpleStringProperty(megnevezes);
        this.ar = new SimpleStringProperty(ar);
        this.id = new SimpleStringProperty("");
    }
    
    public Product(Integer id, String vonalkod, String megnevezes, String ar) {
        this.vonalkod = new SimpleStringProperty(vonalkod);
        this.megnevezes = new SimpleStringProperty(megnevezes);
        this.ar = new SimpleStringProperty(ar);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }
    
    public String getFirstName() {
        return megnevezes.get();
    }
    public void setFirstName(String fName) {
        megnevezes.set(fName);
    }
        
    public String getVonalkod() {
        return vonalkod.get();
    }
    public void setVonalkod(String fName) {
        vonalkod.set(fName);
    }
    
    public String getAr() {
        return ar.get();
    }
    public void setAr(String fName) {
        ar.set(fName);
    }

    public String getId(){
        return id.get();
    }
    
    public void setId(String fId){
        id.set(fId);
    }
    
}
