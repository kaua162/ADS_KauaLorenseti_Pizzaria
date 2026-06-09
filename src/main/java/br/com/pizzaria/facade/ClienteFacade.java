package br.com.pizzaria.facade;

import br.com.pizzaria.entity.ClienteEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ClienteFacade extends AbstractFacade<ClienteEntity> {

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(ClienteEntity.class);
    }

    public List<ClienteEntity> buscarTodos() {
        List<ClienteEntity> lista = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT c FROM ClienteEntity c ORDER BY c.nome");
            if (!query.getResultList().isEmpty()) {
                lista = (List<ClienteEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar clientes: " + e);
        }
        return lista;
    }
}
