package com.xzha.push;

import com.xzha.push.dal.StubDAO;
import com.xzha.push.gcm.GcmSender;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Hello world!
 *
 */
@Path("/v1")
public class PushAPI
{
    private static final Logger LOG = Logger.getLogger(PushAPI.class);

    @Inject
    private StubDAO stubDao;

    @Inject
    private GcmSender gcmSender;
    
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getResponse(@PathParam("id") String id) {
        LOG.debug("Get method + param: " + id);
        LOG.debug("Storage contains: " + stubDao.readAll().size());
        return Response.ok().entity(stubDao.read(id)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerApp(JSONObject data) {
        String id = String.valueOf(data.get("id"));
        String token = String.valueOf(data.get("token"));
        LOG.debug("Got register request: id = " + id + "token=" + token);
        stubDao.create(id, token);
        return Response.accepted().build();
    }

    @PUT
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateApp(JSONObject data) {
        String id = String.valueOf(data.get("id"));
        String token = String.valueOf(data.get("token"));
        LOG.debug("Got update registration request: id = " + id + "token=" + token);
        stubDao.update(id, token);
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
        String userToken = stubDao.read(id);
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
