package br.com.pizzaria.controller;

import br.com.pizzaria.entity.ClienteEntity;
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
public class ClienteController implements Serializable {

    private ClienteEntity cliente = new ClienteEntity();
    private List<ClienteEntity> clienteList = new ArrayList<>();

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        carregarLista();
    }

    public void carregarLista() {
        clienteList = em.createQuery("SELECT c FROM ClienteEntity c", ClienteEntity.class).getResultList();
    }

    public void adicionarCliente() {
        cliente.setId(gerarId());
        cliente.setDatahorareg(new Date());
        em.persist(cliente);
        cliente = new ClienteEntity();
        carregarLista();
        exibirMensagem("Cliente cadastrado com sucesso!");
    }

    public void editarCliente() {
        em.merge(cliente);
        carregarLista();
        exibirMensagem("Cliente atualizado!");
    }

    public void excluirCliente() {
        ClienteEntity c = em.find(ClienteEntity.class, cliente.getId());
        em.remove(c);
        carregarLista();
        exibirMensagem("Cliente excluído!");
    }

    public void selecionarCliente(ClienteEntity c) {
        this.cliente = c;
    }

    public void novoCliente() {
        this.cliente = new ClienteEntity();
    }

    private int gerarId() {
        List<ClienteEntity> lista = em.createQuery(
            "SELECT c FROM ClienteEntity c ORDER BY c.id DESC", ClienteEntity.class)
            .setMaxResults(1).getResultList();
        return lista.isEmpty() ? 1 : lista.get(0).getId() + 1;
    }

    private void exibirMensagem(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity cliente) { this.cliente = cliente; }
    public List<ClienteEntity> getClienteList() { return clienteList; }
    public void setClienteList(List<ClienteEntity> clienteList) { this.clienteList = clienteList; }
}