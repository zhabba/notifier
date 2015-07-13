package com.xzha.push;

import com.xzha.push.dal.Storage;
import com.xzha.push.gcm.GcmSender;
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
    private static final Storage<String, String> storage = new Storage<>(new MapStorage());

    @Inject
    private GcmSender gcmSender;
    
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getResponse(@PathParam("id") String id) {
        LOG.debug("Get method + param: " + id);
        LOG.debug("Storage contains: " + storage.readAll().size());
        return Response.ok().entity(storage.read(id)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerApp(JSONObject data) {
        String id = String.valueOf(data.get("id"));
        String token = String.valueOf(data.get("token"));
        LOG.debug("Got register request: id = " + id + "token=" + token);
        storage.create(id, token);
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
        String topic = String.valueOf(notification.get("topic"));
        String message = String.valueOf(notification.get("message"));
        String userToken = storage.read(id);
        String response;
        if (userToken != null && userToken.equals(token)) {
            response = gcmSender.send(message, topic);
            return Response.ok().entity(response).build();
        } else {
            response = "Unregistered user";
            return Response.status(401).entity(response).build();
        }
    }
}
