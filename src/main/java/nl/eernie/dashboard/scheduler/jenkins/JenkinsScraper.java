package nl.eernie.dashboard.scheduler.jenkins;

import com.google.common.base.Optional;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.Job;
import nl.eernie.dashboard.model.RemoteConfiguration;
import nl.eernie.dashboard.scheduler.RemoteScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class JenkinsScraper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsScraper.class);

    @EJB
    private RemoteScheduler remoteScheduler;

    public void scrape(RemoteConfiguration remoteConfiguration) {
        LOGGER.info("Scraping jenkins {}", remoteConfiguration.getName());
        JenkinsServer jenkinsServer = new JenkinsServer(URI.create(remoteConfiguration.getUrl()), remoteConfiguration.getUsername(), remoteConfiguration.getPassword());
        try {
            jenkinsServer.getJobs().forEach((s, job) -> scrapJob(s, job, jenkinsServer));
        } catch (IOException e) {
            LOGGER.error("Something went wrong", e);
        }
        remoteScheduler.schedule(remoteConfiguration);
    }

    private void scrapJob(String jobName, Job job, JenkinsServer jenkinsServer) {
        try {
            Optional<FolderJob> folderJob = jenkinsServer.getFolderJob(job);
            if (folderJob.isPresent()) {
                folderJob.get().getJobs().forEach((name, folderJobChildren) -> scrapJob(jobName + "/" + name, folderJobChildren, jenkinsServer));
            } else {
                LOGGER.info("Finally getting a job {}", jobName);

                Stream<LinkedHashMap<String, String>> stream = job.details().getLastBuild().details().getActions().stream();
                List<LinkedHashMap<String, String>> result = stream.filter((LinkedHashMap o) -> o.containsKey("_class") && o.get("_class").equals("hudson.plugins.git.util.BuildData")).collect(Collectors.toList());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
