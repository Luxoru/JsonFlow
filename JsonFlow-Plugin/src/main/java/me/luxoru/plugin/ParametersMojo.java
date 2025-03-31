package me.luxoru.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "configure-parameters", defaultPhase = LifecyclePhase.VALIDATE,
        requiresDependencyResolution = ResolutionScope.COMPILE)
public class ParametersMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Configuring compiler to preserve parameter names");

        // Get or create compiler configuration
        Object compilerConfig = project.getProperties().get("maven.compiler.args");

        // Add -parameters flag if it doesn't exist
        if (compilerConfig == null) {
            project.getProperties().setProperty("maven.compiler.args", "-parameters");
        } else if (!compilerConfig.toString().contains("-parameters")) {
            project.getProperties().setProperty("maven.compiler.args",
                    compilerConfig.toString() + " -parameters");
        }

        // Set these for backward compatibility
        project.getProperties().setProperty("maven.compiler.parameters", "true");
    }
}