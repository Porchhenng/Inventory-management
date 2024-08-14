package pos.v2.pos_v2;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.security.cert.Extension;
import java.util.*;

import static com.sun.javafx.css.FontFaceImpl.FontFaceSrcType.URL;

public class productController {

    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brand;

    @FXML
    private TableColumn<productData, String> addProducts_col_brand;

    @FXML
    private TableColumn<productData, Double> addProducts_col_price;

    @FXML
    private TableColumn<productData, Integer> addProducts_col_productId;

    @FXML
    private TableColumn<productData, String> addProducts_col_productName;

    @FXML
    private TableColumn<productData, String> addProducts_col_status;

    @FXML
    private TableColumn<productData, String> addProducts_col_type;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private TextField addProducts_search;

    @FXML
    private TextField addProducts_id;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private Button addProducts_resetBtn;

    @FXML
    private ComboBox<?> addProducts_status;

    @FXML
    private TableView<productData> addProducts_tableView;

    @FXML
    private ComboBox<?> addProducts_type;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private AnchorPane addProducts_imageView;

    @FXML
    private Button addProducts_importBtn;
        //===================================================================
        // in dashBoard controller

        //DATABASE TOOLS
        private Connection connect;
        private PreparedStatement prepare;
        private Statement statement;
        private ResultSet result;

        private Image image;
        public void addProductsAdd(){

            String sql = "INSERT INTO product (product_id,type,brand,productName,price, status, image,date)"
                    + "VALUES (?,?,?,?,?,?,?,?)";

            connect = database.connectDb();
            try{
                Alert alert;
                if(addProducts_id.getText().isEmpty()
                        || addProducts_type.getSelectionModel().getSelectedItem() == null
                        || addProducts_brand.getText().isEmpty()
                        || addProducts_status.getSelectionModel().getSelectedItem() == null
                        || addProducts_productName.getText().isEmpty()
                        || addProducts_price.getText().isEmpty()
                        || getData.path == ""){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                }else {
                    // CHECK THE PRODUCT ID IS ALREADY EXIST
                    String checkData = "SELECT product_id FROM product WHERE product_id = '"
                            + addProducts_id.getText()+"'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);
                    if(result.next()){
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Products ID: "+ addProducts_id.getText()+ " was already exist!");
                        alert.showAndWait();
                    }else{

                    }



                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addProducts_id.getText());
                    prepare.setString(2, (String) addProducts_type.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addProducts_brand.getText());
                    prepare.setString(4, addProducts_productName.getText());
                    prepare.setString(5, addProducts_price.getText());
                    prepare.setString(6, (String) addProducts_status.getSelectionModel().getSelectedItem());

                    String uri = getData.path();
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(7, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    addProductsShowListData();
                    // to clear the fields
                    addProductsReset();
                }


            }catch(Exception e){e.printStackTrace()};

        }

        public void addProductsUpdate(){
            String uri = getData.path;
            uri = uri.replace("\\","\\\\");

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            String sql = "UPDATE product SET type = '"+ addProducts_type.getSelectionModel().getSelectedItem()
                    + "', brand = '"+addProducts_brand.getText()
                    + "', productName = '"+addProducts_productName.getText()
                    + "', price = '"+ addProducts_price.getText()
                    + "', status = '"+ addProducts_status.getSelectionModel().getSelectedItem()
                    + "', image = '"+uri+"',date = '" + sqlDate+ "' WHERE product_id = '"
                    + addProducts_id.getText() + "'" ;
            connect = database.connectDb();
            try{
                Alert alert;
                if(addProducts_id.getText().isEmpty()
                        || addProducts_type.getSelectionModel().getSelectedItem() == null
                        || addProducts_brand.getText().isEmpty()
                        || addProducts_status.getSelectionModel().getSelectedItem() == null
                        || addProducts_productName.getText().isEmpty()
                        || addProducts_price.getText().isEmpty()
                        || getData.path == "") {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                }else{
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to UPDATE Product ID: "+ addProducts_id.getText()+ "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)){
                        statement = connect.createStatement();
                        statement.executeUpdate(sql);

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();


                        addProductsShowListData();
                        addProductsReset();
                    }
                }
            }catch(Exception e){e.printStackTrace();}

        }

    public void addProductsDelete(){
        String sql = "DELETE FROM product WHERE product_id = '"+ addProducts_id.getText()+"'";
        connect = database.connectDb();

        try{
            Alert alert;
            if(addProducts_id.getText().isEmpty()
                    || addProducts_type.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || getData.path == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product ID: "+ addProducts_id.getText()+ "?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();


                    addProductsShowListData();
                    addProductsReset();
                }
            }
        }catch(Exception e){e.printStackTrace();}










        }


        public void addProductsReset(){
            addProducts_id.setText("");
            addProducts_type.getSelectionModel().clearSelection();
            addProducts_brand.setText("");
            addProducts_col_productId.setText("");
            addProducts_price.setText("");
            addProducts_imageView.setShape(null);
            addProducts_status.getSelectionModel().clearSelection();

            getData.path = "";
        }

        public void addProductsImportImage(){
            FileChooser open = new FileChooser();
            open.setTitle("Open Image File");
            open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg","*png"));

            File file = open.showOpenDialog(main_form.getScene().getWindow());

            if(file != null){
                getData.path = file.getAbsolutePath();
                image = new Image(file.toURI().toString(),120,142,false,true);
                addProducts_imageView.setImage(image);
            }

        }

        public void addProductsSearch(){
            FilteredList<productData> filter = new FilteredList<>(addProductList, e -> true);

            InvalidationListener Observable;
            addProducts_search.textProperty().addListener((Observable, oldValue, newValue)->{
                filter.setPredicate(predicateProductData ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String searchKey = newValue.toLowerCase();
                    if(predicateProductData.getProductId().toString().contains(searchKey)){
                        return true;
                    }else if(predicateProductData.getType().toLowerCase().contains(searchKey)){
                        return true;
                    }else if(predicateProductData.getBrand().toLowerCase().contains(searchKey)){
                        return true;
                    }else if(predicateProductData.getProductName().toLowerCase().contains(searchKey)){
                        return true;
                    }else if(predicateProductData.getPrice().toString().contains(searchKey)){
                        return true;
                    }else if(predicateProductData.getStatus().toLowerCase().contains(searchKey)){
                        return true;
                    }else return false;
                });
            });

            SortedList<productData> sortList = new SortedList<>(filter);
            sortList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
            addProducts_tableView.setItems(sortList);
        }




        public Observable<productData> addProductsListData() {
            ObservableList<productData> productList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM product";
            connect = database.connectDb();
            try{
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery;
                productData prodD;
                while(result.next()){
                    prodD = new productData(result.getInt("product_id"), result.getString("type"), result.getString("brand"),
                            result.getString("type"), result.getString("brand"),
                            result.getString("productName"), result.getString("price"), result.getString("status"));
                    productList.add(prodD);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return productList;
        }

        private ObservableList<productData> addProductList;
        public void addProductsShowListData(){
            addProductList = (ObservableList<productData>) addProductsListData();

            addProducts_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            addProducts_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

            addProducts_tableView.setItems(addProductList);
        }

        public void addProductsSelect(){
            productData prodD = addProducts_tableView.getSelectionModel().getSelectedItem();
            int num = addProducts_tableView.getSelectionModel().getSelectedIndex();
            if((num - 1)<-1){return;}

            addProducts_id.setText(String.valueOf(prodD.getProductId()));
            addProducts_brand.setText(prodD.getBrand());
            addProducts_productName.setText(prodD.getProductName());
            addProducts_price.setText((String.valueOf(prodD.getPrice())));
            String uri = "file:" +prodD.getImage();

            image = new Image(uri, 120,142,false, true);

            addProducts_imageView.setImage(image);
            getData.path = prodD.getImage();

        }

        private String[] listType= {"Snacks", "Drinks", "Dessert", "Personal Products", "Others"};
        public void addProductsListType(){
            List<String> listT = new ArrayList<>();

            for(String data: listType){
                listT.add(data);
            }
            Observable listData = FXCollections.observableArrayList(listT);
            addProducts_type.setItems(listData);
        }

        private String[] listStatus = {"Available", "Not Available"};
        public void addProductsListStatus(){
            List<String> listS = new ArrayList<>();

            for(String data: listStatus){
                listS.add(data);
            }
            ObservableList listData = FXCollections.observableArrayList(listS);
            addProducts_status.setItems(listData);


        }



        public void switchForm(ActionEvent event){

            if(event.getSource() == home_btn){
                home_form.setVisible(true);
                addProducts_form.setVisible(false);
                orders_form.setVisible(false);

                home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #23825e, #6d6e30);");
                addProducts_btn.setStyle("-fx-background-color: transparent");
                orders_btn.setStyle("-fx-background-color: transparent");
            }
            else if(event.getSource() == addProducts_btn){
                home_form.setVisible(false);
                addProducts_form.setVisible(true);
                orders_form.setVisible(false);

                addProducts_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #23825e, #6d6e30);");
                home_btn.setStyle("-fx-background-color: transparent");
                orders_btn.setStyle("-fx-background-color: transparent");

                addProductsShowListData();
                addProductsListStatus();
                addProductsListType();
                addProductsSearch();
            }
            else if(event.getSource() == orders_btn){
                home_form.setVisible(false);
                addProducts_form.setVisible(false);
                orders_form.setVisible(true);

                orders_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #23825e, #6d6e30);");
                addProducts_form.setStyle("-fx-background-color: transparent");
                home_btn.setStyle("-fx-background-color: transparent");
            }

            @Override
                    public void initialize(URL location, ResourceBundle resources){
               // to do data on table view
                addProductsShowListData();
                addProductsListStatus();
                addProductsListType();
            }


        }

}





