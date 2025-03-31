package me.luxoru.plugin;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;

import java.util.List;

@Component(role = AbstractMavenLifecycleParticipant.class)
public class ParametersLifecycleParticipant extends AbstractMavenLifecycleParticipant {

    @Override
    public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
        List<MavenProject> projects = session.getProjects();

        for (MavenProject project : projects) {
            // Check if our plugin is a dependency
            if (hasDependency(project, "me.luxoru", "jsonflow-core")) {
                if (!project.getProperties().containsKey("maven.compiler.parameters")) {
                    project.getProperties().setProperty("maven.compiler.parameters", "true");
                }

                Object compilerArgs = project.getProperties().get("maven.compiler.args");
                if (compilerArgs == null) {
                    project.getProperties().setProperty("maven.compiler.args", "-parameters");
                } else if (!compilerArgs.toString().contains("-parameters")) {
                    project.getProperties().setProperty("maven.compiler.args",
                            compilerArgs.toString() + " -parameters");
                }
            }
        }
    }

    private boolean hasDependency(MavenProject project, String groupId, String artifactId) {
        return project.getDependencies().stream()
                .anyMatch(d -> groupId.equals(d.getGroupId()) && artifactId.equals(d.getArtifactId()));
    }
}
