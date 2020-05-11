package products;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public final class DB {
    private static final DB INSTANCE = new DB();

    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    private DB() {

            try {
                conn = DriverManager.getConnection(URL);
            } catch (SQLException ex) {
                System.out.println("Valami baj van a csatlakozás létrehozásakor.");
                System.out.println("" + ex);
            }

            if (conn != null) {
                try {
                    createStatement = conn.createStatement();
                } catch (SQLException ex) {
                    System.out.println("Valami baj van van a createStatament létrehozásakor.");
                    System.out.println("" + ex);
                }
            }

            try {
                dbmd = conn.getMetaData();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
                System.out.println("" + ex);
            }

            try {
                ResultSet rs = dbmd.getTables(null, "APP", "PRODUCTS", null);
                if (!rs.next()) {
                    createStatement.execute("create table products(megnevezes varchar(20), vonalkod varchar(20) not null primary key, ar varchar(10), darab varchar(10))");
                }
            } catch (SQLException ex) {
                System.out.println("Valami baj van az adattáblák létrehozásakor.");
                System.out.println("" + ex);
            }
        }


        public ArrayList<Product> getAllProduct() {
            String sql = "select * from products";
            ArrayList<Product> products = null;
            try {
                ResultSet rs = createStatement.executeQuery(sql);
                products = new ArrayList<>();

                while (rs.next()) {
                    Product actualProduct = new Product(rs.getString("megnevezes"), rs.getString("vonalkod"), rs.getString("ar"), rs.getString("darab"));
                    products.add(actualProduct);
                }
            } catch (SQLException ex) {
                System.out.println("Valami baj van a termékek kiolvasásakor");
                System.out.println("" + ex);
            }
            return products;
        }
        public Product selectProduct(Product product) {

            try {
                String sql = "select megnevezes, vonalkod, ar, darab from products where vonalkod = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, product.getVonalkod());
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                Product actProduct = new Product(rs.getString("megnevezes"), rs.getString("vonalkod"), rs.getString("ar"), rs.getString("darab"));
                return actProduct;}
                return null;
            } catch (SQLException ex) {
                System.out.println("Valami baj van a termék kiolvasásakor");
                System.out.println("" + ex);
                return null;
            }
        }

        public void addProduct(Product product) {
            try {
                String sql = "insert into products (megnevezes, vonalkod, ar, darab) values (?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, product.getMegnevezes());
                preparedStatement.setString(2, product.getVonalkod());
                preparedStatement.setString(3, product.getAr());
                preparedStatement.setString(4, product.getDarab());
                preparedStatement.execute();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a termék hozzáadásakor");
                System.out.println("" + ex);
            }
        }

        public void updateProduct(Product product) {
            try {
                String sql = "update products set megnevezes = ?, ar = ? , darab = ? where vonalkod = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, product.getMegnevezes());
                preparedStatement.setString(2, product.getAr());
                preparedStatement.setString(3, product.getDarab());
                preparedStatement.setString(4, product.getVonalkod());
                preparedStatement.execute();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a termék változtatásakor");
                System.out.println("" + ex);
            }
        }

        public void removeProduct(Product product) {
            try {
                String sql = "delete from products where vonalkod = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, product.getVonalkod());
                preparedStatement.execute();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a termék törlésekor");
                System.out.println("" + ex);
            }
        }

        public static DB getInstance() {
            return INSTANCE;
        }
    }