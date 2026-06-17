package br.com.pizzaria.controller;

import br.com.pizzaria.entity.PizzaEntity;
import br.com.pizzaria.enumeration.TamanhoPizzaEnum;
import br.com.pizzaria.facade.PizzaFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named(value = "pizzaController")
@SessionScoped
public class PizzaController implements Serializable {

    @EJB
    private PizzaFacade ejbFacade;

    private PizzaEntity pizza = new PizzaEntity();
    private PizzaEntity selected;

    public PizzaEntity getPizza() { return pizza; }
    public void setPizza(PizzaEntity pizza) { this.pizza = pizza; }

    public PizzaEntity getSelected() { return selected; }
    public void setSelected(PizzaEntity selected) { this.selected = selected; }

    public List<PizzaEntity> getPizzaList() {
        return ejbFacade.buscarTodos();
    }

    public List<TamanhoPizzaEnum> getTamanhos() {
        return Arrays.asList(TamanhoPizzaEnum.values());
    }

    public PizzaEntity prepareAdicionar() {
        pizza = new PizzaEntity();
        return pizza;
    }

    public void adicionarPizza() {
        persist(PersistAction.CREATE, "Pizza cadastrada com sucesso!");
    }

    public void editarPizza() {
        persist(PersistAction.UPDATE, "Pizza alterada com sucesso!");
    }

    public void deletarPizza() {
        persist(PersistAction.DELETE, "Pizza excluída com sucesso!");
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE, DELETE, UPDATE
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(pizza);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) msg = cause.getLocalizedMessage();
            if (msg != null && msg.length() > 0) addErrorMessage(msg);
            else addErrorMessage(ex.getLocalizedMessage());
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }
}
