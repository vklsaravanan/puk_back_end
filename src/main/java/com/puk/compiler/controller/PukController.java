package com.puk.compiler.controller;

import com.puk.compiler.pojo.CompilerRequest;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/puk")
@Singleton
public class PukController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response compilePukCode(CompilerRequest compilerRequest){
        return Response.ok(compilerRequest.language()).build();
    }
}
