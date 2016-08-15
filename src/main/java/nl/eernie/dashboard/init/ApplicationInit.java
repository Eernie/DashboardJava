package nl.eernie.dashboard.init;

import nl.eernie.dashboard.dao.RemoteConfigurationDao;
import nl.eernie.dashboard.model.RemoteConfiguration;
import nl.eernie.dashboard.scheduler.RemoteScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import java.util.List;

@Startup
@Singleton
public class ApplicationInit {

    @Resource
    private ManagedScheduledExecutorService executor;

    @EJB
    private RemoteConfigurationDao remoteConfigurationDao;

    @EJB
    private RemoteScheduler remoteScheduler;

    @PostConstruct
    public void onInit() {
        List<RemoteConfiguration> allEnabled = remoteConfigurationDao.getAllEnabled();
        allEnabled.forEach(remoteScheduler::schedule);
    }

}
