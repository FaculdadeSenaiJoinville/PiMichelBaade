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
		//System.out.println("context: "+context);
		try {

			HttpSession session = ((HttpServletRequest) request).getSession();
			//System.out.println("session: "+session);
			
			
			
			String usuario = null;
			
			int nivel = 0;			
			// Se existe uma Session
			String requestURI = null;
			//System.out.println("ta aqui: "+session.getAttribute("nivel_usuario"));
			if (session != null && session.getAttribute("nivel_usuario") != null) {
					usuario = (String) session.getAttribute("login");
					//System.out.println("usuario servlet :"+usuario);
					nivel = (int) session.getAttribute("nivel_usuario");
					//System.out.println("nivel servlet :"+nivel);
					requestURI = ((HttpServletRequest) request).getRequestURI();
					//System.out.println("URL: "+((HttpServletRequest) request).getRequestURI());
						
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
