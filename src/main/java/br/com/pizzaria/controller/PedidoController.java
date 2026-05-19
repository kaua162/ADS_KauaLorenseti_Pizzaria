package br.com.pizzaria.controller;

import br.com.pizzaria.entity.ClienteEntity;
import br.com.pizzaria.entity.PedidoEntity;
import br.com.pizzaria.entity.PizzaEntity;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class PedidoController implements Serializable {

    private PedidoEntity pedido = new PedidoEntity();
    private List<PedidoEntity> pedidoList = new ArrayList<>();
    private List<ClienteEntity> clienteList = new ArrayList<>();
    private List<PizzaEntity> pizzaList = new ArrayList<>();

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        carregarLista();
        carregarClientes();
        carregarPizzas();
    }

    public void carregarLista() {
        pedidoList = em.createQuery("SELECT p FROM PedidoEntity p", PedidoEntity.class).getResultList();
    }

    public void carregarClientes() {
        clienteList = em.createQuery("SELECT c FROM ClienteEntity c", ClienteEntity.class).getResultList();
    }

    public void carregarPizzas() {
        pizzaList = em.createQuery("SELECT p FROM PizzaEntity p", PizzaEntity.class).getResultList();
    }

    public void adicionarPedido() {
        pedido.setId(gerarId());
        pedido.setDatahorareg(new Date());
        pedido.setTotal(pedido.getPizza().getPreco() * pedido.getQuantidade());
        em.persist(pedido);
        pedido = new PedidoEntity();
        carregarLista();
        exibirMensagem("Pedido cadastrado com sucesso!");
    }

    public void editarPedido() {
        pedido.setTotal(pedido.getPizza().getPreco() * pedido.getQuantidade());
        em.merge(pedido);
        carregarLista();
        exibirMensagem("Pedido atualizado!");
    }

    public void excluirPedido() {
        PedidoEntity p = em.find(PedidoEntity.class, pedido.getId());
        em.remove(p);
        carregarLista();
        exibirMensagem("Pedido excluído!");
    }

    public void selecionarPedido(PedidoEntity p) {
        this.pedido = p;
    }

    public void novoPedido() {
        this.pedido = new PedidoEntity();
    }

    private int gerarId() {
        List<PedidoEntity> lista = em.createQuery(
            "SELECT p FROM PedidoEntity p ORDER BY p.id DESC", PedidoEntity.class)
            .setMaxResults(1).getResultList();
        return lista.isEmpty() ? 1 : lista.get(0).getId() + 1;
    }

    private void exibirMensagem(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public PedidoEntity getPedido() { return pedido; }
    public void setPedido(PedidoEntity pedido) { this.pedido = pedido; }
    public List<PedidoEntity> getPedidoList() { return pedidoList; }
    public void setPedidoList(List<PedidoEntity> pedidoList) { this.pedidoList = pedidoList; }
    public List<ClienteEntity> getClienteList() { return clienteList; }
    public void setClienteList(List<ClienteEntity> clienteList) { this.clienteList = clienteList; }
    public List<PizzaEntity> getPizzaList() { return pizzaList; }
    public void setPizzaList(List<PizzaEntity> pizzaList) { this.pizzaList = pizzaList; }
}