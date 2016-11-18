package com.yusuke.fxmlController;

import com.yusuke.Controller.NamelistJpaController;
import com.yusuke.entities.Namelist;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           // Create EntityManager        
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("com.yusuke_inputdata_jar_1.0-SNAPSHOTPU");
        // Create Controller
        NamelistJpaController controller =new NamelistJpaController(emf);
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
}
