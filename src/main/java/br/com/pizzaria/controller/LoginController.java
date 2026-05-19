package br.com.pizzaria.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    private String usuario;
    private String senha;

    public String login() {
        if ("admin".equals(usuario) && "admin".equals(senha)) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            return "/pages/pizza.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválidos!", null));
            return null;
        }
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}