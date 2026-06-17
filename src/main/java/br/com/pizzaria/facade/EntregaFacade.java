package br.com.pizzaria.facade;

import br.com.pizzaria.entity.EntregaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EntregaFacade extends AbstractFacade<EntregaEntity> {

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntregaFacade() {
        super(EntregaEntity.class);
    }

    public List<EntregaEntity> buscarTodos() {
        List<EntregaEntity> lista = new ArrayList<>();
        try {
            Query query = em.createQuery(
                "SELECT e FROM EntregaEntity e ORDER BY e.id DESC");
            if (!query.getResultList().isEmpty()) {
                lista = (List<EntregaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar entregas: " + e);
        }
        return lista;
    }
}
