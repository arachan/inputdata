package com.yusuke.fxmlController;

import com.yusuke.Controller.NamelistJpaController;
import com.yusuke.Controller.exceptions.NonexistentEntityException;
import com.yusuke.entities.Namelist;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author yusuke
 */
public class NameListController implements Initializable{

    @FXML
    private TextField IDField;

    @FXML
    private TextField NameField;

    @FXML
    private TableView<Namelist> TableView;

    @FXML
    private TableColumn<Namelist, Integer> IDColumn;

    @FXML
    private TableColumn<Namelist, String> NameColumn;
    
    private ObservableList<Namelist> data;
    
    private NamelistJpaController controller;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           // Create EntityManager        
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("com.yusuke_inputdata_jar_1.0-SNAPSHOTPU");
        // Create Controller
        controller =new NamelistJpaController(emf);
        // Get Namelist Data
        List<Namelist> namelist = (List<Namelist>)controller.findAll();
        
        // Change List to ObservableList to use TableView
        data=FXCollections.observableArrayList(namelist);
        
              
        // Set CellValue 
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        
                
        showNameDetails(null);

        // Set ObservableList into TableView
        this.TableView.setItems(data);

        
        // Listen for selection changes and show the sale details when changed. 
        TableView.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->showNameDetails(newValue));

    }    
    
    @FXML
    private void showNameDetails(Namelist name){
        if(name!=null){
            //Fill the labels with info from the sale object.
            IDField.setText(name.getId().toString());
            NameField.setText(name.getName());
        }else{
            //sale is null,remove all the text.
            IDField.setText("");
            NameField.setText("");
        }
    }
    
     @FXML
    private void handleDeleteName() throws NonexistentEntityException{
        int selectedIndex=TableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0){
            //personTable.getItems().remove(selectedIndex);
            controller.destroy(selectedIndex);
            System.out.println("delete");
            
        }else{
            //Nothing selected.
            Alert alert=new Alert(Alert.AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No  Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewName(){
        Namelist Name=new Namelist();
        
        //boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        //if(okClicked){
            //mainApp.getPersonData().add(tempPerson);
        //}
    }
    
    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     * 
     */
    @FXML
     private void handleEditName() throws Exception{
         Namelist name=TableView.getSelectionModel().getSelectedItem();
         //Data Update
         if(name!=null){
             //boolean okClicked = mainApp.showPersonEditDialog(name);
             //if(okClicked){
             //    showPersonDetails(selectedPerson);
             //}
             System.out.println("update");
             controller.edit(name);
         }else{
             //Data Add(Create)
            if(NameField.getText()!=null){
                name=new Namelist();
                name.setName(NameField.getText());
                System.out.println(name.getName());
                controller.create(name);
            }else{ 
            //Nothing selected.
             Alert alert =new Alert(Alert.AlertType.WARNING);
             //alert.initOwner(mainApp.getPrimaryStage());
             alert.setTitle("No Selection");
             alert.setHeaderText("No Person Selected");
             alert.setContentText("Please select a person in the table.");
             
             alert.showAndWait();
            }
         }
     }
     
     @FXML
     private void handleClear(){
         //各種フィールドをクリア
         IDField.clear();
         NameField.clear();
         //TableViewの選択もクリア
         TableView.getSelectionModel().clearSelection();
     }
}
