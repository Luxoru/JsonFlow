package me.luxoru.plugin;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.xml.Xpp3Dom;

@Component(role = AbstractMavenLifecycleParticipant.class, hint = "configure-parameters")
public class ParametersLifecycleParticipant extends AbstractMavenLifecycleParticipant {

    @Override
    public void afterProjectsRead(MavenSession session) {
        session.getAllProjects().forEach(project -> {
            ensureParametersFlag(project);
        });
    }

    private void ensureParametersFlag(MavenProject project) {
        Build build = project.getBuild();
        if (build == null) {
            build = new Build();
            project.setBuild(build);
        }

        Plugin compilerPlugin = build.getPluginsAsMap().get("org.apache.maven.plugins:maven-compiler-plugin");
        if (compilerPlugin == null) {
            compilerPlugin = new Plugin();
            compilerPlugin.setGroupId("org.apache.maven.plugins");
            compilerPlugin.setArtifactId("maven-compiler-plugin");
            compilerPlugin.setVersion("3.8.1");
            build.addPlugin(compilerPlugin);
        }

        Xpp3Dom configuration = (Xpp3Dom) compilerPlugin.getConfiguration();
        if (configuration == null) {
            configuration = new Xpp3Dom("configuration");
        }

        Xpp3Dom parametersNode = configuration.getChild("parameters");
        if (parametersNode == null) {
            parametersNode = new Xpp3Dom("parameters");
            parametersNode.setValue("true");
            configuration.addChild(parametersNode);
        } else {
            parametersNode.setValue("true");
        }

        compilerPlugin.setConfiguration(configuration);
    }
}
