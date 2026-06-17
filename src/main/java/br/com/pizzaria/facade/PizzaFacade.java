package br.com.pizzaria.facade;

import br.com.pizzaria.entity.PizzaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PizzaFacade extends AbstractFacade<PizzaEntity> {

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PizzaFacade() {
        super(PizzaEntity.class);
    }

    public List<PizzaEntity> buscarTodos() {
        List<PizzaEntity> lista = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT p FROM PizzaEntity p ORDER BY p.nome");
            if (!query.getResultList().isEmpty()) {
                lista = (List<PizzaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar pizzas: " + e);
        }
        return lista;
    }

    public List<PizzaEntity> buscarDisponiveis() {
        List<PizzaEntity> lista = new ArrayList<>();
        try {
            Query query = em.createQuery(
                "SELECT p FROM PizzaEntity p WHERE p.disponivel = true ORDER BY p.nome");
            if (!query.getResultList().isEmpty()) {
                lista = (List<PizzaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar pizzas disponíveis: " + e);
        }
        return lista;
    }
}
