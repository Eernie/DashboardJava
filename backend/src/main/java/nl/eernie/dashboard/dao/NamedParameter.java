package nl.eernie.dashboard.dao;

public class NamedParameter
{
	private final String name;
	private final Object value;

	public NamedParameter(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public Object getValue()
	{
		return value;
	}
}