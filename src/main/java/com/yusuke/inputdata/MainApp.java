package com.yusuke.inputdata;

import com.yusuke.Controller.NamelistJpaController;
import com.yusuke.entities.Namelist;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/NameList.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Namelist");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        /*
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("com.yusuke_inputdata_jar_1.0-SNAPSHOTPU");
        // Create Controller
        NamelistJpaController controller =new NamelistJpaController(emf);
        // Get Namelist Data
        //List<Namelist> namelist = (List<Namelist>)controller.findAll();
        List<Namelist> namelist = (List<Namelist>)controller.findNamelistEntities();
        //Namelist namelist = controller.findNamelist(1);
        //System.out.println(namelist.getName());
        namelist.forEach((name) -> {
            System.out.println(name.getName());
        });
        */
        
    }

}
