package br.com.pizzaria.controller;

import br.com.pizzaria.entity.UsuarioEntity;
import br.com.pizzaria.facade.UsuarioFacade;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private UsuarioFacade ejbFacade;

    private String email;
    private String senha;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    /**
     * Valida as credenciais consultando o banco de dados.
     * Se válido, salva o usuário na sessão e redireciona para a área admin.
     */
    public String validarLogin() {
        UsuarioEntity usuario = ejbFacade.buscarPorEmailSenha(email, senha);
        if (usuario != null && usuario.getId() != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext
                    .getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            return "/admin/cliente.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Usuário ou senha inválidos!", "Verifique suas credenciais."));
            return null;
        }
    }

    /**
     * Invalida a sessão e redireciona para o login.
     */
    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext
                .getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/login.xhtml?faces-redirect=true";
    }
}
