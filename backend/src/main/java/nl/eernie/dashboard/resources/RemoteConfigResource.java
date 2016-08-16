package nl.eernie.dashboard.resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.eernie.dashboard.dao.RemoteConfigurationDao;
import nl.eernie.dashboard.model.RemoteConfiguration;

@Path("remote-config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class RemoteConfigResource
{
	@EJB
	private RemoteConfigurationDao remoteConfigurationDao;

	@GET
	public List<RemoteConfiguration> doGet()
	{
		return remoteConfigurationDao.getAll();
	}

	@GET
	@Path("{id}")
	public RemoteConfiguration doGet(@PathParam("id") Long id)
	{
		RemoteConfiguration remoteConfiguration = remoteConfigurationDao.find(id);
		if (remoteConfiguration == null)
		{
			throw new NotFoundException();
		}
		return remoteConfiguration;
	}

	@PUT
	public RemoteConfiguration doPut(RemoteConfiguration remoteConfiguration)
	{
		if (remoteConfiguration.getId() != null)
		{
			throw new BadRequestException(Response.status(400).type(MediaType.TEXT_PLAIN_TYPE).entity("Creating new shouldn't have an ID").build());
		}
		try
		{
			return remoteConfigurationDao.create(remoteConfiguration);
		}
		catch (EJBTransactionRolledbackException exception)
		{
			throw new BadRequestException(Response.status(400).type(MediaType.TEXT_PLAIN_TYPE).entity("Something went wrong while creating the remote-config").build(), exception);
		}
	}
}