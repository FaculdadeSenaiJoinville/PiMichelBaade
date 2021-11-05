package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		String context = request.getServletContext().getContextPath();
		try {

			HttpSession session = ((HttpServletRequest) request).getSession();
			String usuario = null;
			// Se existe uma Session
			if (session != null) {
				// Atribua o valor do login de quem se logou a variável usuario
				usuario = (String) session.getAttribute("login");
			}
			// Verificando se usuário é nulo
			if (usuario == null){
				// Se for redireciona para a apresentação da mensagem de erro.

				((HttpServletResponse) response).sendRedirect("http://localhost:8080/projtrilhaseguranca/erro.html");
			} else {

				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// Fechando o bloco do doFilter()
	
	// Executa a destruição do Filtro.
	public void destroy() {
	}

}
