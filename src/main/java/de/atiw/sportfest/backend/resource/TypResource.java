package de.atiw.sportfest.backend.resource;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.rules.Typ;

@Path("/typ")
public class TypResource {

    @Resource(name="jdbc/sportfest")
    DataSource db;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Typ> listTypen() throws SQLException {
        return Typ.getAll(db.getConnection());
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Typ createTyp(Typ variable) throws SQLException {
        return Typ.create(db.getConnection(), variable);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{tid:\\d+}")
    public Typ readTyp(@PathParam("tid") String tid) throws SQLException, NotFoundException {
        return Typ.getOne(db.getConnection(), tid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{tid:\\d+}")
    public Typ updateTyp(@PathParam("tid") String tid, Typ tar) throws SQLException, NotFoundException {
        return Typ.edit(db.getConnection(), tid, tar);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{tid:\\d+}")
    public Response deleteTyp(@PathParam("tid") String tid) throws SQLException, NotFoundException {
        Typ.delete(db.getConnection(), tid);
        return Response.ok().build();
    }

}

