import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javafx.stage.FileChooser;

import java.io.File;
import java.security.cert.Extension;


import static com.sun.javafx.css.FontFaceImpl.FontFaceSrcType.URL;

public class dashboardController {

    
    @FXML
    private TextField addProduct_search;

    @FXML
    private Button addProduct_Btn;

    @FXML
    private TextField addProduct_Id;

    @FXML
    private Button addProduct_addBtn;

    @FXML
    private TextField addProduct_brand;

    @FXML
    private TableColumn<productData, String> addProduct_col_brand;

    @FXML
    private TableColumn<productData, Double> addProduct_col_price;

    @FXML
    private TableColumn<productData, Integer> addProduct_col_productId;

    @FXML
    private TableColumn<productData, String> addProduct_col_productName;

    @FXML
    private TableColumn<productData, String> addProduct_col_status;

    @FXML
    private TableColumn<productData, String> addProduct_col_type;

    @FXML
    private Button addProduct_deleteBtn;

    @FXML
    private AnchorPane addProduct_form;

    @FXML
    private ImageView addProduct_imageView;

    @FXML
    private Button addProduct_importBtn;

    @FXML
    private TextField addProduct_price;

    @FXML
    private TextField addProduct_productName;

    @FXML
    private Button addProduct_resetBtn;

    @FXML
    private ComboBox<?> addProduct_status;

    @FXML
    private TableView<productData> addProduct_tableView;

    @FXML
    private ComboBox<?> addProduct_type;

    @FXML
    private Button addProduct_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button home_Btn;

    @FXML
    private Label home_availableProduct;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AreaChart<?, ?> home_incomeChart;

    @FXML
    private Label home_numberOrder;

    @FXML
    private BarChart<?, ?> home_ordersChart;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button orders_Btn;

    @FXML
    private TableView<customerData> orders_TableView;

    @FXML
    private Button orders_addBtn;

    @FXML
    private TextField orders_amount;

    @FXML
    private Label orders_balance;

    @FXML
    private ComboBox<?> orders_brand;
    @FXML
    private TableColumn<productData, Integer> orders_col_productId;

    @FXML
    private TableColumn<customerData, String> orders_col_brand;

    @FXML
    private TableColumn<customerData, Double> orders_col_price;

    @FXML
    private TableColumn<customerData, String> orders_col_productName;

    @FXML
    private TableColumn<customerData, Integer> orders_col_quantity;

    @FXML
    private TableColumn<customerData, String> orders_col_type;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private Button orders_payemntBtn;

    @FXML
    private ComboBox<?> orders_productName;

    @FXML
    private ComboBox<?> orders_productType;

    @FXML
    private Spinner<Integer> orders_quantity;

    @FXML
    private Button orders_receiptBtn;

    @FXML
    private Button orders_resetBtn;

    @FXML
    private Label orders_total;

    @FXML
    private Label username;

    @FXML
    void addProduct_brand(ActionEvent event) {

    }

    @FXML
    void addProduct_price(ActionEvent event) {

    }

    @FXML
    void addProduct_productId(ActionEvent event) {

    }

    @FXML
    void addProduct_productName(ActionEvent event) {

    }

    @FXML
    void addProduct_productType(ActionEvent event) {

    }

    @FXML
    void addProduct_search(ActionEvent event) {

    }

    @FXML
    void addProduct_status(ActionEvent event) {

    }
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;
    public void addProductsAdd(){

        String sql = "INSERT INTO product (product_id,type,brand,productName,price, status, image,date)"
                + "VALUES (?,?,?,?,?,?,?,?)";

        connect = database.connectionDb();
        try{
            Alert alert;
            if(addProduct_col_productId.getText().isEmpty()
                    || addProduct_type.getSelectionModel().getSelectedItem() == null
                    || addProduct_col_brand.getText().isEmpty()
                    || addProduct_status.getSelectionModel().getSelectedItem() == null
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_price.getText().isEmpty()
                    || getData.path == null || getData.path =="")
                {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                // CHECK THE PRODUCT ID IS ALREADY EXIST
                String checkData = "SELECT product_id FROM product WHERE product_id = '"
                        + addProduct_Id.getText()+"'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Products ID: "+ addProduct_Id.getText()+ " was already exist!");
                    alert.showAndWait();
                }else{

                }



                prepare = connect.prepareStatement(sql);
                prepare.setString(1, addProduct_Id.getText());
                prepare.setString(2, (String) addProduct_type.getSelectionModel().getSelectedItem());
                prepare.setString(3, addProduct_brand.getText());
                prepare.setString(4, addProduct_productName.getText());
                prepare.setString(5, addProduct_price.getText());
                prepare.setString(6, (String) addProduct_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                uri = uri.replace("\\","\\\\");
                prepare.setString(7, uri);

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(8, String.valueOf(sqlDate));

                prepare.executeUpdate();
                addProductsShowListData();
                // to clear the fields
                addProductsReset();
            }


        }catch(Exception e){e.printStackTrace();}

    }

    public void addProductsUpdate(){
        String uri = getData.path;
        uri = uri.replace("\\","\\\\");

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "UPDATE product SET type = '"+ addProduct_type.getSelectionModel().getSelectedItem()
                + "', brand = '"+addProduct_brand.getText()
                + "', productName = '"+addProduct_productName.getText()
                + "', price = '"+ addProduct_price.getText()
                + "', status = '"+ addProduct_status.getSelectionModel().getSelectedItem()
                + "', image = '"+uri+"',date = '" + sqlDate+ "' WHERE product_id = '"
                + addProduct_Id.getText() + "'" ;
        connect = database.connectionDb();
        try{
            Alert alert;
            if(addProduct_Id.getText().isEmpty()
                    || addProduct_type.getSelectionModel().getSelectedItem() == null
                    || addProduct_brand.getText().isEmpty()
                    || addProduct_status.getSelectionModel().getSelectedItem() == null
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_price.getText().isEmpty()
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
                alert.setContentText("Are you sure you want to UPDATE Product ID: "+ addProduct_Id.getText()+ "?");
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
        String sql = "DELETE FROM product WHERE product_id = '"+ addProduct_Id.getText()+"'";
        connect = database.connectionDb();

        try{
            Alert alert;
            if(addProduct_Id.getText().isEmpty()
                    || addProduct_type.getSelectionModel().getSelectedItem() == null
                    || addProduct_brand.getText().isEmpty()
                    || addProduct_status.getSelectionModel().getSelectedItem() == null
                    || addProduct_productName.getText().isEmpty()
                    || addProduct_price.getText().isEmpty()
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
                alert.setContentText("Are you sure you want to DELETE Product ID: "+ addProduct_Id.getText()+ "?");
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
    public ObservableList <productData> addProductsListData() {
            ObservableList<productData> productList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM product";
            connect = database.connectionDb();
            try{
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();
                productData prodD;
                while(result.next()){
                    prodD = new productData(result.getInt("product_id"),
                    result.getString("type"),
                    result.getString("brand"),
                    result.getString("productName"),
                    result.getDouble("price"),               
                    result.getString("status"),
                    result.getString("image"),
                    result.getDate("date"));
                    productList.add(prodD);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return productList;
        }

        public void addProductsReset(){
            addProduct_Id.setText("");
            addProduct_type.getSelectionModel().clearSelection();
            addProduct_brand.setText("");
            
            addProduct_price.setText("");
            addProduct_imageView.setImage(null);
            addProduct_status.getSelectionModel().clearSelection();

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
                addProduct_imageView.setImage(image);
            }

        }
        public void addProductsSearch(){
            FilteredList<productData> filter = new FilteredList<>(addProductList, e -> true);

            
            addProduct_search.textProperty().addListener((Observable, oldValue, newValue)->{
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
            sortList.comparatorProperty().bind(addProduct_tableView.comparatorProperty());
            addProduct_tableView.setItems(sortList);
        }


        private ObservableList<productData> addProductList;
        public void addProductsShowListData(){
            addProductList = (ObservableList<productData>) addProductsListData();

            addProduct_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            addProduct_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            addProduct_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
            addProduct_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            addProduct_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            addProduct_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

            addProduct_tableView.setItems(addProductList);
        }
        public void addProductsSelect(){
            productData prodD = addProduct_tableView.getSelectionModel().getSelectedItem();
            int num = addProduct_tableView.getSelectionModel().getSelectedIndex();
            if((num - 1)<-1){return;}

            addProduct_Id.setText(String.valueOf(prodD.getProductId()));
            addProduct_brand.setText(prodD.getBrand());
            addProduct_productName.setText(prodD.getProductName());
            addProduct_price.setText((String.valueOf(prodD.getPrice())));
            String uri = "file:" +prodD.getImage();

            image = new Image(uri, 120,142,false, true);

            addProduct_imageView.setImage(image);
            getData.path = prodD.getImage();

        }

    public void close(){
        System.exit(0);
    }
    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    private String[] listType= {"Snacks", "Drinks", "Dessert", "Personal Products", "Others"};
        public void addProductsListType(){
            List<String> listT = new ArrayList<>();

            for(String data: listType){
                listT.add(data);
            }
            ObservableList listData = FXCollections.observableArrayList(listT);
            addProduct_type.setItems(listData);
        }

        private String[] listStatus = {"Available", "Not Available"};
        public void addProductsListStatus(){
            List<String> listS = new ArrayList<>();

            for(String data: listStatus){
                listS.add(data);
            }
            ObservableList listData = FXCollections.observableArrayList(listS);
            addProduct_status.setItems(listData);


        }
     

    public void switchForm(ActionEvent event){
        if (event.getSource() == home_Btn) {
            home_form.setVisible(true);
            addProduct_form.setVisible(false);
            orders_form.setVisible(false);

            home_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #25a473, #89892b);");
            addProduct_Btn.setStyle("-fx-background-color:transparent");
            orders_Btn.setStyle("-fx-background-color:transparent");

            homeAvailableProduct();
            homeDisplayTotalOrders();
            homeTotalIncome();
            homeIncomeChart();
            homeOrdersChart();
            
        } else if(event.getSource() == addProduct_Btn){
            addProduct_form.setVisible(true);
            home_form.setVisible(false);
            orders_form.setVisible(false);

            home_Btn.setStyle("-fx-background-color:transparent");
            addProduct_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #25a473, #89892b);");
            orders_Btn.setStyle("-fx-background-color:transparent");

            addProductsShowListData();
            addProductsListStatus();
            addProductsListType();
            addProductsSearch();

        }else if(event.getSource() == orders_Btn){
            addProduct_form.setVisible(false);
            home_form.setVisible(false);
            orders_form.setVisible(true);

            home_Btn.setStyle("-fx-background-color:transparent");
            addProduct_Btn.setStyle("-fx-background-color:transparent");
            orders_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #25a473, #89892b);");

            ordersListType();
            ordersShowListData();
            ordersListBrand();
            ordersListProductName();
            ordersSpinner();
            

        }

    }
    public void logOut(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to proceed?");
            alert.showAndWait();
            Optional<ButtonType>option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                
            
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
              
             Stage stage = new Stage();
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
             logout.getScene().getWindow().hide();
        }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<customerData> observableListData (){
        customerId();
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerid+"'";

         connect = database.connectionDb();
        try {
            customerData customerD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                customerD = new customerData(result.getInt("customer_id")
                        , result.getString("type")
                        , result.getString("brand")
                        , result.getString("productName")
                        , result.getInt("quantity")
                        , result.getDouble("price")
                        , result.getDate("date"));

                listData.add(customerD);
            }
        }catch (Exception e){e.printStackTrace();}

        return listData;
    }

 
    private ObservableList<customerData> ordersList;
    public void ordersShowListData(){
        ordersList = ordersListData();


        orders_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        orders_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orders_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orders_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orders_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orders_TableView.setItems(ordersList);
        ordersDisplayTotal();
    }

    private String[] orderListType= {"Snacks", "Drinks", "Dessert", "Gadget", "Personal Product"};
    public void ordersListType(){
        List<String>listT = new ArrayList<>();
        for (String data: orderListType){
            listT.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listT);
        orders_productType.setItems(listData);

        ordersListBrand();
    }
    public void ordersListBrand(){
        
        String sql = "SELECT brand FROM product WHERE type = '"
                + orders_productType.getSelectionModel().getSelectedItem()
                +"' and status = 'Available' GROUP BY brand";
        connect = database.connectionDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                listData.add(result.getString("Brand"));
            }
            orders_brand.setItems(listData);
            ordersListProductName();
            
        }catch (Exception e){e.printStackTrace();}

    }
    public void ordersListProductName(){
    
        String sql = "SELECT productName FROM product WHERE brand = '"
                + orders_brand.getSelectionModel().getSelectedItem()+"'";
        connect = database.connectionDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()){
                listData.add(result.getString("productName"));
            }
            orders_productName.setItems(listData);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private SpinnerValueFactory<Integer> spinner;

    public void ordersSpinner(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,0);
        orders_quantity.setValueFactory(spinner);

    }
    private int qty;
    public void ordersShowSpinnerValue(){

         qty = orders_quantity.getValue();


    }
    public void ordersSelect(){
            customerData prodD = orders_TableView.getSelectionModel().getSelectedItem();
            int num = orders_TableView.getSelectionModel().getSelectedIndex();
            if((num - 1)<-1){return;}

            /*orders_col_type.setText(String.valueOf(prodD.getType()));
            orders_col_brand.setText(prodD.getBrand());
            orders_col_productName.setText(prodD.getProductName());
            orders_col_price.setText((String.valueOf(prodD.getPrice())));
            orders_col_quantity.setText(String.valueOf(prodD.getQuantity())); */
            

        }
    public ObservableList<customerData> ordersListData() {

        customerId();
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customer_id = '"+ customerid+"'";
        connect=database.connectionDb();

        try {
            customerData customerD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerD = new customerData(result.getInt("customer_id"),
                result.getString("type"),
                result.getString("brand"),
                result.getString("productName"),
                result.getInt("quantity"),
                result.getDouble("price"),
                result.getDate("date")); 
                listData.add(customerD);
                
            }

        }catch(Exception e){e.printStackTrace();}
        return listData;
    }


    private int customerid;
    public void customerId(){

        String customerId = "SELECT * FROM customer";

        connect = database.connectionDb();

        try {
            prepare  = connect.prepareStatement(customerId);
            result = prepare.executeQuery();

            int checkId = 0;

            while (result.next()){
                // GET LAST CUSTOMER ID
                checkId = result.getInt("customer_id");
            }

            String checkData = "SELECT * FROM customer_receipt";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            while (result.next()) {
                checkId = result.getInt("customer_id");
            }

            if(customerid == 0){
                customerid+=1;
            }else if (checkId == customerid){
                customerid += 1;
            }


        }catch (Exception e){e.fillInStackTrace();}
    }
    private String[] ordersListType = {"Snack", "Drinks", "Dessert", "Gadgets", "Personal Product", "Others"};

    

    private void orderListType() {

        List<String> list = new ArrayList<>();
        for(String data: ordersListType){
            list.add(data);

        }

        ObservableList listData = FXCollections.observableArrayList(list);
        orders_productType.setItems(listData);
        ordersListBrand();

    }
    public void ordersAdd(){
        customerId();
        String sql = "INSERT INTO customer (customer_id, type, brand, productName, quantity, price, date)"+
                "VALUES(?,?,?,?,?,?,?)";
        connect = database.connectionDb();

        try {
            String checkData = "SELECT * FROM product WHERE productName = '" +orders_productName.getSelectionModel().getSelectedItem()+"'";

        double priceData = 0;
        statement = connect.createStatement();
        result = statement.executeQuery(checkData);


        if (result.next()){
             priceData = result.getDouble("price");

        }
        double totalPData = (priceData*qty);


        Alert alert;

        if(orders_productType.getSelectionModel().getSelectedItem() == null
                || (String)orders_brand.getSelectionModel().getSelectedItem()  == null
                || (String)orders_productName.getSelectionModel().getSelectedItem() == null
                || totalPData == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose first");
            alert.showAndWait();
            
        } else {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(customerid));
            prepare.setString(2, (String)orders_productType.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String)orders_brand.getSelectionModel().getSelectedItem());
            prepare.setString(4, (String)orders_productName.getSelectionModel().getSelectedItem());
            prepare.setString(5, String.valueOf(qty));
             totalPData = (priceData*qty);
            prepare.setString(6, String.valueOf(totalPData));
            Date date = new Date();
            java.sql.Date sqlData = new java.sql.Date(date.getTime());
            prepare.setString(7, String.valueOf(sqlData));

            prepare.executeUpdate();

            ordersDisplayTotal();
            ordersShowListData();
            
            
        }
        

        }catch (Exception e){e.printStackTrace();}
        
        


    }
    private double amountP;
    private  double balanceP;
    public void ordersReceipt(){

        customerId();
        String sql = "DELETE FROM customer WHERE customer_id = '" + customerid + "'";
        connect =database.connectionDb();
        


        try {
            if (!orders_TableView.getItems().isEmpty()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to reset?");
                Optional<ButtonType> option = alert.showAndWait();
                

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                     
                    
                }
                
            }


        }catch (Exception e){e.printStackTrace();}


    }

    public void ordersReset(){
        customerId();

        String sql = "DELETE FROM customer WHERE customer_id = '"+customerid+"'";

        connect = database.connectionDb();

        try {
            if(!orders_TableView.getItems().isEmpty()){

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to reset?");
                Optional<ButtonType> option = alert.showAndWait();


                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                        ordersShowListData();
                        totalP = 0;
                        amountP = 0;
                        balanceP = 0;

                       

                        //AWESOME!, NOW LET WORK FOR THE DASHBOARD :)

                        orders_balance.setText("$0.0");
                        orders_total.setText("$0.0");
                        orders_amount.setText("");
                }



//                statement = connect.createStatemnt();
//                alert.executeUpdate(sql);


            }
        }catch (Exception e){e.printStackTrace();}
    }

    

    public  void ordersAmount(){
        Alert alert;
        amountP = Double.parseDouble(orders_amount.getText());
        if (!orders_amount.getText().isEmpty()) {
            
        
        if (totalP > 0 ) {
            if (amountP >= totalP) {
                balanceP = (amountP - totalP);

                orders_balance.setText("$" +String.valueOf(balanceP));
                
            } else { 
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("invalid :3");
                alert.showAndWait();

                orders_amount.setText("");


            }
            
        } else {
            alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("invalid :3");
                alert.showAndWait();
        }
    } else{
        alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("invalid :3");
                alert.showAndWait();

    }

        
    }

    public void ordersPay(){
        customerId();
        String sql = "INSERT INTO customer_receipt (customer_id, total,amount,balance, date)" + "VALUES(?,?,?,?,?)";
                
        connect = database.connectionDb();

        try{
            Alert alert;
            if (totalP > 0 || orders_amount.getText().isEmpty() || amountP == 0) {

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("CONFIRMATION MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)){
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, String.valueOf(customerid));
                        prepare.setString(2, String.valueOf(totalP));
                        prepare.setString(3, String.valueOf(amountP));
                        prepare.setString(4, String.valueOf(balanceP));
//                        prepare.setString(1, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date aqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(5, String.valueOf(aqlDate));
                        
                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful");
                        alert.showAndWait();

                        ordersShowListData();

                        totalP = 0;
                        amountP = 0;
                        balanceP = 0;

                       

                        //AWESOME!, NOW LET WORK FOR THE DASHBOARD :)

                        orders_balance.setText("$0.0");
                        orders_total.setText("$0.0");
                        orders_amount.setText("");

                    }else return;
            }else {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid :3");
                    alert.showAndWait();


            }



        }catch (Exception e){e.printStackTrace();}
    }



    private double totalP;
    public void ordersDisplayTotal(){

        String sql = "SELECT SUM(price) FROM customer WHERE customer_id= '"+customerid+"'";

        connect = database.connectionDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                totalP = result.getDouble("SUM(price)");
            }
            orders_total.setText("$"+String.valueOf(totalP));

        }catch (Exception e){e.printStackTrace();}

    }
     public void homeDisplayTotalOrders(){

        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT COUNT(id) FROM customer WHERE date = '"+sqlDate+"'";
        connect = database.connectionDb();
        int countOrders = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                countOrders = result.getInt("COUNT(id)");
            }

            home_numberOrder.setText(String.valueOf(countOrders));

        }catch (Exception e){e.printStackTrace();}

    }


    public void  homeTotalIncome(){
        String sql = "SELECT SUM(total) FROM customer_receipt";
        connect = database.connectionDb();

        double totalIncome = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result =  prepare.executeQuery();

            while (result.next()){

                totalIncome = result.getDouble("SUM(total)");

            }

            home_totalIncome.setText("$"+String.valueOf(totalIncome));


        }catch (Exception e){e.printStackTrace();}

    }

    public void homeAvailableProduct(){
        String sql = "SELECT COUNT(id) FROM product WHERE status ='Available'";

        connect =database.connectionDb();
        int countAP = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countAP = result.getInt("COUNT(id)");
            }
            home_availableProduct.setText(String.valueOf(countAP));
        }catch(Exception e){e.printStackTrace();}
    }

    public void homeIncomeChart(){
        home_incomeChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 6";

        try {
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

            }
            home_incomeChart.getData().add(chart);


        }catch (Exception e){e.printStackTrace();}

    }


    public void homeOrdersChart(){
        home_ordersChart.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM customer GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

        connect = database.connectionDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
               chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

            }


            home_incomeChart.getData().add(chart);


        }catch (Exception e){e.printStackTrace();}

    }

    public void displayUsername(){

    }


    


    @Override
    public void initialize(URL location, ResourceBundle resources){
        
       homeAvailableProduct();
       homeDisplayTotalOrders();
       homeTotalIncome();
       homeIncomeChart();
       homeOrdersChart();


        addProductsShowListData();
        addProductsListStatus();
        addProductsListType();

        
        ordersListType();
        ordersListBrand();
        ordersListProductName();
        ordersSpinner();
        ordersShowListData();
        ordersDisplayTotal();

    }

}

