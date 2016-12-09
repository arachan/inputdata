/**
 * Database Access Objectを創設
 * 接続は1つ
 * Controllerも1つなのでDAOにまとめた方が無難だと考えた。
 * 
 **/
package com.yusuke.dao;

import com.yusuke.Controller.NamelistJpaController;
import com.yusuke.Controller.exceptions.NonexistentEntityException;
import com.yusuke.entities.Namelist;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author yusuke
 */
public class NameListDao {
        private final EntityManagerFactory emf;
        private final NamelistJpaController controller;
    
        //Constractor
        public NameListDao(){
            emf=Persistence.createEntityManagerFactory("com.yusuke_inputdata_jar_1.0-SNAPSHOTPU");
            // Create Controller
            controller =new NamelistJpaController(emf);
        }       
        
        public void remove(int Namelistid) throws NonexistentEntityException{
            controller.destroy(Namelistid);
        }
        
        public void edit(Namelist namelist) throws Exception{
            controller.edit(namelist);
        }
        
        public void add(Namelist namelist){
            controller.create(namelist);
        }
        
        public List<Namelist> all(){
            return controller.findNamelistEntities();
        }
}
