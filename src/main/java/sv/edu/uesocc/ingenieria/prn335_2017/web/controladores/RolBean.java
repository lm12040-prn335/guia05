/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.RolFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Rol;

/**
 *
 * @author ed
 */
@Named(value = "rolBean")
@ViewScoped
public class RolBean implements Serializable {

    public RolBean() {
    }

    @EJB
    private RolFacadeLocal rl;
    private LazyDataModel<Rol> modelo;
    private boolean btnadd = true;
    private Rol registro;
    Rol nuevo = new Rol();
    List<Rol> lista = new ArrayList<>();
    
    

    @PostConstruct
    public void inicio() {
            
            try {
                this.modelo = new LazyDataModel<Rol>() {
                    @Override
                    public Object getRowKey(Rol object){
                        if(object != null){
                            return object.getIdRol();
                        }
                        return null;
                    }
                    @Override
                    public Rol getRowData(String rowKey){
                        if(rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null){
                            try {
                                Integer buscado = new Integer(rowKey);
                                for(Rol reg : (List<Rol>)getWrappedData()){
                                    if(reg.getIdRol().compareTo((buscado))==0){
                                        return reg;
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Error "+ e);
                            }
                        }
                        return null;
                    }
                    @Override
                    public List<Rol> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
                        
                        List<Rol> salida= new ArrayList();
                        try {
                            if(rl != null){
                                this.setRowCount(rl.count());
                                salida=rl.findRange(first, pageSize);
                                
                            }
                        } catch (Exception e) {
                            System.out.println("Error "+e);
                        }
                            return salida;
                        
                    }
};
            } catch (Exception e) {
                System.out.println("Error "+e);
            }
            
        }
    
        
        
    public LazyDataModel<Rol> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Rol> modelo) {
        this.modelo = modelo;
    }

    public RolFacadeLocal getRol() {
        return rl;
    }

    public void setRol(RolFacadeLocal rl) {
        this.rl = rl;
    }

    public Rol getRegistro() {
        return registro;
    }

    public void setRegistro(Rol registro) {
        this.registro = registro;
    }

    public boolean isBtnadd() {
        return btnadd;
    }

    public void setBtnadd(boolean btnadd) {
        this.btnadd = btnadd;
    }

    public void showMessage(String Mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(Mensaje));
    }
    public void llenar(){
        if(lista != null){
            this.lista=rl.findAll();
            this.lista=Collections.EMPTY_LIST;
        }
    }
    public void crear(){
        try{
            rl.create(nuevo);
            llenar();
            showMessage("Registro realizado correctamente");
            nuevo= new Rol();
        }catch(Exception e){
             System.out.println("Error: " + e);
                showMessage("Error a la hora de ingresar los datos.");
        }
         }
     
    private Object getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
        public void filtrar(){
            rl.findByFiltro();
            inicio();
        }
    
    

}
