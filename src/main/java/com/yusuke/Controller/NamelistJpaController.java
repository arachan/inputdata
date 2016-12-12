/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yusuke.Controller;

import com.yusuke.Controller.exceptions.NonexistentEntityException;
import com.yusuke.entities.Namelist;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author yusuke
 */
public class NamelistJpaController implements Serializable {

    public NamelistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Namelist namelist) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(namelist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Namelist namelist) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            namelist = em.merge(namelist);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = namelist.getId();
                if (findNamelist(id) == null) {
                    throw new NonexistentEntityException("The namelist with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Namelist namelist;
            try {
                namelist = em.getReference(Namelist.class, id);
                namelist.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The namelist with id " + id + " no longer exists.", enfe);
            }
            em.remove(namelist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Namelist> findNamelistEntities() {
        return findNamelistEntities(true, -1, -1);
    }

    public List<Namelist> findNamelistEntities(int maxResults, int firstResult) {
        return findNamelistEntities(false, maxResults, firstResult);
    }

    private List<Namelist> findNamelistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Namelist.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Namelist findNamelist(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Namelist.class, id);
        } finally {
            em.close();
        }
    }

    public int getNamelistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Namelist> rt = cq.from(Namelist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Namelist> findAll(){
        EntityManager em=getEntityManager();
        TypedQuery<Namelist> q=em.createNamedQuery("Namelist.findAll", Namelist.class);  
        return q.getResultList();
    }
    
    public List<Namelist> findByName(String name){
        EntityManager em=getEntityManager();
        TypedQuery<Namelist> q=em.createNamedQuery("Namelist.findByName",Namelist.class);
        q.setParameter("name", name);        
        return q.getResultList();
    }
    
}
