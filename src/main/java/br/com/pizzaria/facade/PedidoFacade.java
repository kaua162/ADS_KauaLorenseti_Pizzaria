package br.com.pizzaria.facade;

import br.com.pizzaria.entity.PedidoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PedidoFacade extends AbstractFacade<PedidoEntity> {

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoFacade() {
        super(PedidoEntity.class);
    }

    public List<PedidoEntity> buscarTodos() {
        List<PedidoEntity> lista = new ArrayList<>();
        try {
            Query query = em.createQuery(
                "SELECT p FROM PedidoEntity p ORDER BY p.datahoraPedido DESC");
            if (!query.getResultList().isEmpty()) {
                lista = (List<PedidoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar pedidos: " + e);
        }
        return lista;
    }
}
