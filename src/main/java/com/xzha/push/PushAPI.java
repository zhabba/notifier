package com.xzha.push;

import com.xzha.push.storages.inmemory.MapStorage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Hello world!
 *
 */
@Path("/api")
public class PushAPI
{
    private static final Logger LOG = Logger.getLogger(PushAPI.class);
    @Context
    private UriInfo uriInfo;

    @Inject
    private MapStorage storage;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getResponse(@PathParam("id") String id) {
        LOG.debug("Get method + param: " + id);
        LOG.debug("Storage contains: " + storage.count());
        return Response.ok().entity(storage.get(id).toString()).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerApp(JSONObject data) {
        String id = String.valueOf(data.get("id"));
        String token = String.valueOf(data.get("token"));
        LOG.debug("Got register request: id = " + id + "token=" + token);
        storage.save(id, token);
        return Response.accepted().build();
    }

    @PUT
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateApp(JSONObject data) {
        String id = String.valueOf(data.get("id"));
        String token = String.valueOf(data.get("token"));
        LOG.debug("Got update registration request: id = " + id + "token=" + token);
        storage.update(id, token);
        return Response.accepted().build();
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendNotification(JSONObject notification) {
        String id = String.valueOf(notification.get("id"));
        String token = String.valueOf(notification.get("token"));
        return Response.accepted().build();
    }
}
