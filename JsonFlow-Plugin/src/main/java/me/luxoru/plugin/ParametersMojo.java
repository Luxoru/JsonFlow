package me.luxoru.plugin;

import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.utils.xml.Xpp3Dom;

@Mojo(name = "configure-parameters", defaultPhase = LifecyclePhase.VALIDATE)
public class ParametersMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Ensuring -parameters flag is set in Maven Compiler Plugin");

        // Ensure the project has a build section
        Build build = project.getBuild();
        if (build == null) {
            build = new Build();
            project.setBuild(build);
        }

        // Get existing compiler plugin or create a new one
        Plugin compilerPlugin = build.getPluginsAsMap().get("org.apache.maven.plugins:maven-compiler-plugin");
        if (compilerPlugin == null) {
            compilerPlugin = new Plugin();
            compilerPlugin.setGroupId("org.apache.maven.plugins");
            compilerPlugin.setArtifactId("maven-compiler-plugin");
            compilerPlugin.setVersion("3.8.1"); // Adjust as needed
            build.addPlugin(compilerPlugin);
        }

        // Ensure <configuration> exists
        Xpp3Dom configuration = (Xpp3Dom) compilerPlugin.getConfiguration();
        if (configuration == null) {
            configuration = new Xpp3Dom("configuration");
        }

        // Ensure <parameters>true</parameters> is set
        Xpp3Dom parametersNode = configuration.getChild("parameters");
        if (parametersNode == null) {
            parametersNode = new Xpp3Dom("parameters");
            parametersNode.setValue("true");
            configuration.addChild(parametersNode);
        } else {
            parametersNode.setValue("true");
        }

        // Apply the modified configuration
        compilerPlugin.setConfiguration(configuration);

        getLog().info("Successfully enforced -parameters in Maven Compiler Plugin.");
    }
}