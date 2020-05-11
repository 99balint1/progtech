
package products;

import javafx.beans.property.SimpleStringProperty;


public class Product {
    
    private final SimpleStringProperty megnevezes;
    private final SimpleStringProperty vonalkod;
    private final SimpleStringProperty ar;
    private final SimpleStringProperty darab;
    
    public Product() {
        this.megnevezes = new SimpleStringProperty("");
        this.vonalkod = new SimpleStringProperty("");
        this.ar = new SimpleStringProperty("");
        this.darab = new SimpleStringProperty("");
    }
 
    public Product(String megnevezes, String vonalkod, String ar, String darab) {
        this.vonalkod = new SimpleStringProperty(vonalkod);
        this.megnevezes = new SimpleStringProperty(megnevezes);
        this.ar = new SimpleStringProperty(ar);
        this.darab = new SimpleStringProperty(darab);
    }

    public String getMegnevezes() {
        return megnevezes.get();
    }
    public void setMegnevezes(String Megnevezes) {
        megnevezes.set(Megnevezes);
    }
        
    public String getVonalkod() {
        return vonalkod.get();
    }
    public void setVonalkod(String Vonalkod) {
        vonalkod.set(Vonalkod);
    }
    
    public String getAr() {
        return ar.get();
    }
    public void setAr(String Ar) {
        ar.set(Ar);
    }
    public String getDarab(){return darab.get();}
    public void setDarab(String Darab){
        darab.set(Darab);
    }
    @Override
    public String toString(){
        return String.format("Megnevezes: " + megnevezes.get()+ " Vonalkód: " + vonalkod.get()+" Ár: " + ar.get()+ " Darabszám: "+darab.get());
    }

    
}
