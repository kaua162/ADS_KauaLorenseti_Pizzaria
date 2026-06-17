package br.com.pizzaria.facade;

import br.com.pizzaria.entity.UsuarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UsuarioFacade extends AbstractFacade<UsuarioEntity> {

    @PersistenceContext(unitName = "PizzariaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(UsuarioEntity.class);
    }

    /**
     * Busca um usuário por email e senha para autenticação.
     */
    public UsuarioEntity buscarPorEmailSenha(String email, String senha) {
        try {
            Query query = em.createQuery(
                "SELECT u FROM UsuarioEntity u WHERE u.email = :email AND u.senha = :senha");
            query.setParameter("email", email);
            query.setParameter("senha", senha);
            if (!query.getResultList().isEmpty()) {
                return (UsuarioEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e);
        }
        return null;
    }
}
