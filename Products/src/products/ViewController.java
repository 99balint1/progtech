package products;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ViewController implements Initializable {

    @FXML
    TableView table;
    @FXML
    TextField inputMegnevezes;
    @FXML
    TextField inputVonalkod;
    @FXML
    TextField inputAr;
    @FXML
    TextField inputDarab;
    @FXML
    Button addNewProductButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane productPane;
    @FXML
    Pane exportPane;
    @FXML
    SplitPane mainSplit;
    @FXML
    AnchorPane anchor;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;

    DB db = DB.getInstance();

    private final String MENU_PRODUCTS = "Menü";
    private final String MENU_LIST = "Termékek";
    private final String MENU_EXPORT = "Leltár készítése";
    private final String MENU_EXIT = "Kilépés";

    private final ObservableList<Product> data = FXCollections.observableArrayList();
    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void addProduct(ActionEvent event) {
        String ar = inputAr.getText();
        String vonalkod = inputVonalkod.getText();
        if (ar.length() < 10 && tryParseInt(ar) && Integer.parseInt(ar) >0 && tryParseInt(vonalkod)) {
            Product newProduct = new Product(inputMegnevezes.getText(), vonalkod, ar, inputDarab.getText());
            db.addProduct(newProduct);
            data.add(newProduct);

            inputMegnevezes.clear();
            inputVonalkod.clear();
            inputAr.clear();
            inputDarab.clear();
        }else{
            alert("A termék ára nem lehet negatív\n\nvagy\n\na termék vonalkódja nem csak számokat tartalmaz.");
        }
    }

    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, data);
        }else{
            alert("Adj meg egy fájl nevet!");
        }
    }

    public void setTableData() {
        TableColumn megnevezesCol = new TableColumn("Megnevezés");
        megnevezesCol.setMinWidth(130);
        megnevezesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        megnevezesCol.setCellValueFactory(new PropertyValueFactory<Product, String>("megnevezes"));

        megnevezesCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> t) {
                        Product actualProduct = (Product) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualProduct.setMegnevezes(t.getNewValue());
                        db.updateProduct(actualProduct);
                    }
                }
        );

        TableColumn vonalkodCol = new TableColumn("Vonalkód");
        vonalkodCol.setMinWidth(130);
        vonalkodCol.setCellFactory(TextFieldTableCell.forTableColumn());
        vonalkodCol.setCellValueFactory(new PropertyValueFactory<Product, String>("vonalkod"));

        vonalkodCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> t) {
                        Product actualProduct = (Product) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualProduct.setVonalkod(t.getNewValue());
                        db.updateProduct(actualProduct);
                    }
                }
        );

        TableColumn arCol = new TableColumn("Ár(Ft)");
        arCol.setMinWidth(100);
        arCol.setCellValueFactory(new PropertyValueFactory<Product, String>("ar"));
        arCol.setCellFactory(TextFieldTableCell.forTableColumn());

        arCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> t) {
                        Product actualProduct = (Product) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualProduct.setAr(t.getNewValue());
                        db.updateProduct(actualProduct);
                    }
                }
        );
        TableColumn darabCol = new TableColumn("Darabszám");
        darabCol.setMinWidth(100);
        darabCol.setCellFactory(TextFieldTableCell.forTableColumn());
        darabCol.setCellValueFactory(new PropertyValueFactory<Product, String>("darab"));

        darabCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Product, String> t) {
                        Product actualProduct = (Product) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualProduct.setDarab(t.getNewValue());
                        db.updateProduct(actualProduct);
                    }
                }
        );

        TableColumn removeCol = new TableColumn( "Törlés" );
        arCol.setMinWidth(100);

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory =
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Product, String> param )
                    {
                        final TableCell<Product, String> cell = new TableCell<Product, String>()
                        {
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                    {
                                        Product product = getTableView().getItems().get( getIndex() );
                                        data.remove(product);
                                        db.removeProduct(product);
                                    } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );

        table.getColumns().addAll(megnevezesCol, vonalkodCol, arCol, darabCol, removeCol);

        data.addAll(db.getAllProduct());

        table.setItems(data);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_PRODUCTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        nodeItemA.setExpanded(true);

        Node productNode = new ImageView(new Image(getClass().getResourceAsStream("/product.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, productNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_PRODUCTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            productPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            productPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });

    }

    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);

        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });

        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
    }

}
