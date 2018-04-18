package restfull;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@ApplicationPath("/api")
public class Rest extends Application { }


