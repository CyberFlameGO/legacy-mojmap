package mappings.internal;

import org.gradle.api.Project;

import java.io.File;

public class FileConstants {
    public static FileConstants INSTANCE = null;
    public final File cacheFilesMinecraft;
    public final File tempDir;
    public final File mergedJar;

    public FileConstants(Project project) {
        INSTANCE = this;
        cacheFilesMinecraft = project.file(".gradle/minecraft");
        tempDir = project.file(".gradle/temp");
        mergedJar = new File(cacheFilesMinecraft, Constants.MINECRAFT_VERSION + "-merged.jar");
    }
}