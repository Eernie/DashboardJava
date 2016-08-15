package nl.eernie.dashboard.scheduler;

import nl.eernie.dashboard.model.RemoteConfiguration;
import nl.eernie.dashboard.scheduler.jenkins.JenkinsScraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Stateless
public class RemoteScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteScheduler.class);

    @Resource
    private ManagedScheduledExecutorService managedScheduledExecutorService;

    @EJB
    private JenkinsScraper jenkinsScraper;

    public void schedule(RemoteConfiguration remoteConfiguration) {
        schedule(remoteConfiguration, 5, TimeUnit.SECONDS);
    }

    public void schedule(final RemoteConfiguration remoteConfiguration, long period, TimeUnit unit) {
        if (!remoteConfiguration.isEnabled()) {
            LOGGER.info("[job] ignoring config [{}] {}", remoteConfiguration.getType(), remoteConfiguration.getName());
            return;
        }
        LOGGER.info("[job] Scheduling config [{}] {}", remoteConfiguration.getType(), remoteConfiguration.getName());
        managedScheduledExecutorService.schedule(() -> jenkinsScraper.scrape(remoteConfiguration), period, unit);
    }
}
