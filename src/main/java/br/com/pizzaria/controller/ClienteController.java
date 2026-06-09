package br.com.pizzaria.controller;

import br.com.pizzaria.entity.ClienteEntity;
import br.com.pizzaria.facade.ClienteFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named(value = "clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    @EJB
    private ClienteFacade ejbFacade;

    private ClienteEntity cliente = new ClienteEntity();
    private ClienteEntity selected;

    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity cliente) { this.cliente = cliente; }

    public ClienteEntity getSelected() { return selected; }
    public void setSelected(ClienteEntity selected) { this.selected = selected; }

    public List<ClienteEntity> getClienteList() {
        return ejbFacade.buscarTodos();
    }

    public ClienteEntity prepareAdicionar() {
        cliente = new ClienteEntity();
        return cliente;
    }

    public void adicionarCliente() {
        persist(PersistAction.CREATE, "Cliente cadastrado com sucesso!");
    }

    public void editarCliente() {
        persist(PersistAction.UPDATE, "Cliente alterado com sucesso!");
    }

    public void deletarCliente() {
        persist(PersistAction.DELETE, "Cliente excluído com sucesso!");
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
                        ejbFacade.createReturn(cliente);
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
