package cn.ibard.firstmavenplugin;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author poirot
 */
@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo {

    @Parameter(property = "sayhi.greeting", defaultValue = "Hello ! defaultGreeting")
    private String greeting;
    @Parameter
    private boolean myBoolean;
    @Parameter
    private Integer myInteger;
    @Parameter
    private Double myDouble;
    @Parameter
    private Date myDate;
    @Parameter
    private File myFile;
    @Parameter
    private URL myURL;
    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hello, first maven plugin");
        getLog().info("Hello, " + greeting);
        getLog().info("Hello, " + myBoolean);
        getLog().info("Hello, " + myInteger);
        getLog().info("Hello, " + myDouble);
        getLog().info("Hello, " + myDate.toString());
        getLog().info("Hello, " + myFile.getAbsolutePath());
        getLog().info("Hello, " + myURL.toString());

        getLog().info(project.getArtifactId());
        List<Dependency> dependencies = project.getDependencies();
        getLog().info("dep:" + dependencies.size());
    }
}
