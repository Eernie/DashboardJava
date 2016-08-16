package nl.eernie.dashboard.dao;

import java.util.List;

import javax.ejb.Stateless;

import nl.eernie.dashboard.model.RemoteConfiguration;

@Stateless
public class RemoteConfigurationDao extends GenericDao<RemoteConfiguration>
{
	public RemoteConfigurationDao()
	{
		super(RemoteConfiguration.class);
	}

	public List<RemoteConfiguration> getAll()
	{
		return listQuery("select c from RemoteConfiguration c");
	}

	public List<RemoteConfiguration> getAllEnabled()
	{
		return listQuery("select c from RemoteConfiguration c where c.enabled = true");
	}
}