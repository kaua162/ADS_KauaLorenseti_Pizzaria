package br.com.pizzaria.controller;

import br.com.pizzaria.entity.EntregaEntity;
import br.com.pizzaria.entity.PedidoEntity;
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
public class EntregaController implements Serializable {

    private EntregaEntity entrega = new EntregaEntity();
    private List<EntregaEntity> entregaList = new ArrayList<>();
    private List<PedidoEntity> pedidoList = new ArrayList<>();

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        carregarLista();
        carregarPedidos();
    }

    public void carregarLista() {
        entregaList = em.createQuery("SELECT e FROM EntregaEntity e", EntregaEntity.class).getResultList();
    }

    public void carregarPedidos() {
        pedidoList = em.createQuery("SELECT p FROM PedidoEntity p", PedidoEntity.class).getResultList();
    }

    public void adicionarEntrega() {
        entrega.setId(gerarId());
        entrega.setDatahorareg(new Date());
        em.persist(entrega);
        entrega = new EntregaEntity();
        carregarLista();
        exibirMensagem("Entrega cadastrada com sucesso!");
    }

    public void editarEntrega() {
        em.merge(entrega);
        carregarLista();
        exibirMensagem("Entrega atualizada!");
    }

    public void excluirEntrega() {
        EntregaEntity e = em.find(EntregaEntity.class, entrega.getId());
        em.remove(e);
        carregarLista();
        exibirMensagem("Entrega excluída!");
    }

    public void selecionarEntrega(EntregaEntity e) {
        this.entrega = e;
    }

    public void novaEntrega() {
        this.entrega = new EntregaEntity();
    }

    private int gerarId() {
        List<EntregaEntity> lista = em.createQuery(
            "SELECT e FROM EntregaEntity e ORDER BY e.id DESC", EntregaEntity.class)
            .setMaxResults(1).getResultList();
        return lista.isEmpty() ? 1 : lista.get(0).getId() + 1;
    }

    private void exibirMensagem(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public EntregaEntity getEntrega() { return entrega; }
    public void setEntrega(EntregaEntity entrega) { this.entrega = entrega; }
    public List<EntregaEntity> getEntregaList() { return entregaList; }
    public void setEntregaList(List<EntregaEntity> entregaList) { this.entregaList = entregaList; }
    public List<PedidoEntity> getPedidoList() { return pedidoList; }
    public void setPedidoList(List<PedidoEntity> pedidoList) { this.pedidoList = pedidoList; }
}