package cn.ibard.firstmavenplugin;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mojo(name = "newCount")
public class NewCountMojo extends AbstractMojo {

  private static final String[] INCLUDES_DEFAULT = {" java", "xml", "properties"};

  /**
   * @parameter expression = "${project.basedir}"
   * @required
   * @readonly
   */
  private File basedir;
  /**
   * @parameter expression = "${project.build.sourceDirectory}"
   * @required
   * @readonly
   */
  private File sourceDirectory;
  /**
   * @parameter expression = "${project.build.testSourceDirectory}"
   * @required
   * @readonly
   */
  private File testSourceDirectory;
  /**
   * @parameter expression = "${project.build.resources}"
   * @required
   * @readonly
   */
  private List<Resource> resources;
  /**
   * @parameter expression = "${project.build.testResources}"
   * @required
   * @readonly
   */
  private List<Resource> testResources;
  /**
   * 允许用户进行配置
   *
   * @parameter
   */
  private String[] includes;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (includes == null || includes.length == 0) {
      includes = INCLUDES_DEFAULT;
    }

    try {
      // 统计源代码目录
      countDir(sourceDirectory);

      countDir(testSourceDirectory);

      // 统计资源文件目录
      for (Resource resource : resources) {
        countDir(new File(resource.getDirectory()));
      }

      for (Resource resource : testResources) {
        countDir(new File(resource.getDirectory()));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 统计某个目录下的代码行数
   *
   * @param dir
   * @throws IOException
   */
  private void countDir(File dir) throws IOException {
    if (!dir.exists()) {
      return;
    }

    List<File> collected = new ArrayList<>();
    collectFiles(collected, dir);
    int lines = 0;

    for (File sourceFile : collected) {
      lines += countLine(sourceFile);
    }

    String path = dir.getAbsolutePath().substring(basedir.getAbsolutePath().length());

    getLog().info(path + ":" + lines + " lines of code in " + collected.size() + " files ");
  }

  private void collectFiles(List<File> collected, File file) {
    if (file.isFile()) {
      for (String include : includes) {
        if (file.getName().endsWith("." + include)) {
          collected.add(file);

          break;
        }
      }
    } else {
      for (File sub : Objects.requireNonNull(file.listFiles())) {
        collectFiles(collected, sub);
      }
    }
  }

  /**
   * 计算某个文件的行数
   *
   * @param file
   * @return
   * @throws IOException
   */
  private int countLine(File file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));

    int line = 0;

    try {
      while (reader.ready()) {
        reader.readLine();

        line++;
      }
    } finally {
      reader.close();
    }

    return line;
  }
}
