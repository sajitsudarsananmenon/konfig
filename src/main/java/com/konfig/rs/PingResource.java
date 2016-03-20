
package com.konfig.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.konfig.beans.Ping;

/**
 * @author Sajit Sudarsanan
 * This resource class provides a ping service for the application health check
 *
 */
@Path("/ping")
public class PingResource {

    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Ping getIt() {
    	Ping ping=new Ping();
    	ping.setStatus("ok");
        return ping;
    }
}
