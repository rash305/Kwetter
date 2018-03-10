package restfull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloResource {

  @GET
  @Path("hello")
  public String helloworld() {
      return "Hello World!";
  }
}
