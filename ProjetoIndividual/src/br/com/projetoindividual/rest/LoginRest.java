package br.com.projetoindividual.rest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("login")
public class LoginRest extends UtilRest {
	
	@GET
	@Path("/buscarPorLogin")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorLogin(@Context HttpServletRequest req) {

		try {
			HttpSession sessao = req.getSession();
			
			return this.buildResponse(sessao.getAttribute("login"));
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

}
	