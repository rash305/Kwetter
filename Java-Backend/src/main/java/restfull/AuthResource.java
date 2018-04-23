package restfull;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import DTO.LoginCredentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import service.AuthService;


@Stateless
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response  getToken(LoginCredentials login) {
        String token = null;
        try {
            token = authService.login(URLDecoder.decode(login.getUsername(), "UTF-8"), URLDecoder.decode(login.getPassword(), "UTF-8"));
        } catch (Exception ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);

        }
        return Response.ok()
                .header(HttpHeaders.AUTHORIZATION,  token)
                .header("Access-Control-Expose-Headers", "Authorization")
                .build();
    }



}