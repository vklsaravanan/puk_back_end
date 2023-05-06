package org.acme;

import org.jboss.threads.JBossExecutors;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {
    @GET
//    @Produces(MediaType.TEXT_PLAIN)
      public String hello() {
    	
        return "hello";
    }
    
}