package br.com.pizzaria.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class FiltroAdministrativo implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest   = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        boolean logado = (session != null)
                && (session.getAttribute("usuarioLogado") != null);

        if (logado) {
            chain.doFilter(request, response);
        } else {
            // Redireciona para o login se não estiver autenticado
            httpResponse.sendRedirect(
                httpRequest.getContextPath() + "/login.xhtml");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
