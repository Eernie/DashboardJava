package nl.eernie.dashboard;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.batch.jberet.BatchFraction;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.postgresql.PostgreSQLJPAFraction;
import org.wildfly.swarm.transactions.TransactionsFraction;

public class Main
{
	public static void main(String... args) throws Exception
	{
		System.setProperty("swarm.ds.name", "dashboardDS");
		Container swarm = new Container(args);

		swarm.fraction(TransactionsFraction.createDefaultFraction());
		swarm.fraction(BatchFraction.createDefaultFraction());
		swarm.fraction(PostgreSQLJPAFraction.createDefaultFraction());
		JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
		archive.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
		archive.addPackages(true, Main.class.getPackage());
		archive.addAllDependencies();

		swarm.start().deploy(archive);
	}
}