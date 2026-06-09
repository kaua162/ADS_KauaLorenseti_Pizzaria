package br.com.pizzaria.controller;

import br.com.pizzaria.entity.EntregaEntity;
import br.com.pizzaria.entity.PedidoEntity;
import br.com.pizzaria.enumeration.StatusEntregaEnum;
import br.com.pizzaria.facade.EntregaFacade;
import br.com.pizzaria.facade.PedidoFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Named(value = "entregaController")
@SessionScoped
public class EntregaController implements Serializable {

    @EJB
    private EntregaFacade ejbFacade;

    @EJB
    private PedidoFacade pedidoFacade;

    private EntregaEntity entrega = new EntregaEntity();
    private EntregaEntity selected;

    public EntregaEntity getEntrega() { return entrega; }
    public void setEntrega(EntregaEntity entrega) { this.entrega = entrega; }

    public EntregaEntity getSelected() { return selected; }
    public void setSelected(EntregaEntity selected) { this.selected = selected; }

    public List<EntregaEntity> getEntregaList() {
        return ejbFacade.buscarTodos();
    }

    public List<PedidoEntity> getPedidoList() {
        return pedidoFacade.buscarTodos();
    }

    public List<StatusEntregaEnum> getStatusList() {
        return Arrays.asList(StatusEntregaEnum.values());
    }

    public EntregaEntity prepareAdicionar() {
        entrega = new EntregaEntity();
        entrega.setDatahoraEnvio(new Timestamp(System.currentTimeMillis()));
        return entrega;
    }

    public void adicionarEntrega() {
        persist(PersistAction.CREATE, "Entrega registrada com sucesso!");
    }

    public void editarEntrega() {
        // Se o status mudou para ENTREGUE, registra a data/hora
        if (selected != null && StatusEntregaEnum.ENTREGUE.equals(selected.getStatus())
                && selected.getDatahoraEntrega() == null) {
            selected.setDatahoraEntrega(new Timestamp(System.currentTimeMillis()));
        }
        persist(PersistAction.UPDATE, "Entrega alterada com sucesso!");
    }

    public void deletarEntrega() {
        persist(PersistAction.DELETE, "Entrega excluída com sucesso!");
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
                        ejbFacade.createReturn(entrega);
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
