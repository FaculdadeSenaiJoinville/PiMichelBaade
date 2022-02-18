package Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletFilter implements Filter {

	protected FilterConfig filterConfig;
	
	public void init(FilterConfig filterConfig) throws ServletException {
	       }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		
		String context = request.getServletContext().getContextPath();
		try {

			HttpSession session = ((HttpServletRequest) request).getSession();
			
			
			
			String usuario = null;
			
			int nivel = 0;			
			// Se existe uma Session
			String requestURI = null;
			if (session != null && session.getAttribute("nivel_usuario") != null) {
					usuario = (String) session.getAttribute("login");
					nivel = (int) session.getAttribute("nivel_usuario");
					requestURI = ((HttpServletRequest) request).getRequestURI();
						
			}
			
			if (usuario == null && nivel == 0){

				((HttpServletResponse) response).sendRedirect("http://localhost:8080/ProjetoIndividual/index.html");
			} else {
				
				if (requestURI.startsWith("/ProjetoIndividual/pages/admin/") && nivel != 1) {
					((HttpServletResponse) response).sendRedirect("http://localhost:8080/ProjetoIndividual/index.html");
				}else if (requestURI.startsWith("/ProjetoIndividual/pages/caixa/") && nivel != 2) {
					((HttpServletResponse) response).sendRedirect("http://localhost:8080/ProjetoIndividual/index.html");
				}else if (requestURI.startsWith("/ProjetoIndividual/pages/estoquista/") && nivel != 3) {
					((HttpServletResponse) response).sendRedirect("http://localhost:8080/ProjetoIndividual/index.html");
				}
				
				filterChain.doFilter(request, response);  
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendRedirect("http://localhost:8080/ProjetoIndividual/index.html");
		}

	}
	
	// Executa a destruição do Filtro.
	public void destroy() {

	}

}
