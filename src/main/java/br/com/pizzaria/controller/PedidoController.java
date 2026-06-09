package br.com.pizzaria.controller;

import br.com.pizzaria.entity.ClienteEntity;
import br.com.pizzaria.entity.PedidoEntity;
import br.com.pizzaria.entity.PizzaEntity;
import br.com.pizzaria.enumeration.StatusPedidoEnum;
import br.com.pizzaria.facade.ClienteFacade;
import br.com.pizzaria.facade.PedidoFacade;
import br.com.pizzaria.facade.PizzaFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Named(value = "pedidoController")
@SessionScoped
public class PedidoController implements Serializable {

    @EJB
    private PedidoFacade ejbFacade;

    @EJB
    private ClienteFacade clienteFacade;

    @EJB
    private PizzaFacade pizzaFacade;

    private PedidoEntity pedido = new PedidoEntity();
    private PedidoEntity selected;

    public PedidoEntity getPedido() { return pedido; }
    public void setPedido(PedidoEntity pedido) { this.pedido = pedido; }

    public PedidoEntity getSelected() { return selected; }
    public void setSelected(PedidoEntity selected) { this.selected = selected; }

    public List<PedidoEntity> getPedidoList() {
        return ejbFacade.buscarTodos();
    }

    public List<ClienteEntity> getClienteList() {
        return clienteFacade.buscarTodos();
    }

    public List<PizzaEntity> getPizzaList() {
        return pizzaFacade.buscarDisponiveis();
    }

    public List<StatusPedidoEnum> getStatusList() {
        return Arrays.asList(StatusPedidoEnum.values());
    }

    public PedidoEntity prepareAdicionar() {
        pedido = new PedidoEntity();
        pedido.setQuantidade(1);
        return pedido;
    }

    /**
     * Calcula o valor total automaticamente ao salvar o pedido.
     */
    public void adicionarPedido() {
        pedido.setDatahoraPedido(new Timestamp(System.currentTimeMillis()));
        if (pedido.getPizza() != null && pedido.getQuantidade() != null) {
            BigDecimal total = pedido.getPizza().getPreco()
                    .multiply(BigDecimal.valueOf(pedido.getQuantidade()));
            pedido.setValorTotal(total);
        }
        persist(PersistAction.CREATE, "Pedido registrado com sucesso!");
    }

    public void editarPedido() {
        // Recalcula valor total ao editar
        if (selected != null && selected.getPizza() != null && selected.getQuantidade() != null) {
            BigDecimal total = selected.getPizza().getPreco()
                    .multiply(BigDecimal.valueOf(selected.getQuantidade()));
            selected.setValorTotal(total);
        }
        persist(PersistAction.UPDATE, "Pedido alterado com sucesso!");
    }

    public void deletarPedido() {
        persist(PersistAction.DELETE, "Pedido excluído com sucesso!");
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
                        ejbFacade.createReturn(pedido);
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
