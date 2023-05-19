package com.puk.compiler.controller;

import com.puk.compiler.pojo.CompilerRequest;
import com.puk.compiler.pojo.CompilerResponse;
import com.puk.compiler.service.CompileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/puk")
public class PukController {


    @Inject
    private CompileService service;



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response compilePukCode(CompilerRequest compilerRequest){
       CompilerResponse response = service.compileCodeAndGetOutput(compilerRequest);
       return Response.ok(response).build();
    }
}
