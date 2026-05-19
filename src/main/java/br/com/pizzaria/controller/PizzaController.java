package br.com.pizzaria.controller;

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
import java.util.List;

@Named
@SessionScoped
public class PizzaController implements Serializable {

    private PizzaEntity pizza = new PizzaEntity();
    private List<PizzaEntity> pizzaList = new ArrayList<>();

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        carregarLista();
    }

    public void carregarLista() {
        pizzaList = em.createQuery("SELECT p FROM PizzaEntity p", PizzaEntity.class).getResultList();
    }

    public void adicionarPizza() {
        pizza.setId(gerarId());
        em.persist(pizza);
        pizza = new PizzaEntity();
        carregarLista();
        exibirMensagem("Pizza cadastrada com sucesso!");
    }

    public void editarPizza() {
        em.merge(pizza);
        carregarLista();
        exibirMensagem("Pizza atualizada!");
    }

    public void excluirPizza() {
        PizzaEntity p = em.find(PizzaEntity.class, pizza.getId());
        em.remove(p);
        carregarLista();
        exibirMensagem("Pizza excluída!");
    }

    public void selecionarPizza(PizzaEntity p) {
        this.pizza = p;
    }

    public void novaPizza() {
        this.pizza = new PizzaEntity();
    }

    private int gerarId() {
        List<PizzaEntity> lista = em.createQuery(
            "SELECT p FROM PizzaEntity p ORDER BY p.id DESC", PizzaEntity.class)
            .setMaxResults(1).getResultList();
        return lista.isEmpty() ? 1 : lista.get(0).getId() + 1;
    }

    private void exibirMensagem(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public PizzaEntity getPizza() { return pizza; }
    public void setPizza(PizzaEntity pizza) { this.pizza = pizza; }
    public List<PizzaEntity> getPizzaList() { return pizzaList; }
    public void setPizzaList(List<PizzaEntity> pizzaList) { this.pizzaList = pizzaList; }
}