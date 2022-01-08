package mappings.internal.tasks.setup;

import mappings.internal.Constants;
import mappings.internal.tasks.DefaultMappingsTask;
import net.fabricmc.stitch.merge.JarMerger;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

public class MergeJarsTask extends DefaultMappingsTask {
    public static final String TASK_NAME = "mergeJars";

    private final File clientJar;
    private final File serverJar;

    public MergeJarsTask() {
        super(Constants.Groups.SETUP_GROUP);
        dependsOn(DownloadMinecraftJarsTask.TASK_NAME);

        clientJar = getTaskByType(DownloadMinecraftJarsTask.class).getClientJar();
        serverJar = getTaskByType(DownloadMinecraftJarsTask.class).getServerJar();

        getInputs().files(clientJar, serverJar);
        getOutputs().file(fileConstants.mergedJar);
    }

    @TaskAction
    public void mergeJars() throws IOException {
        getLogger().lifecycle(":merging jars");

        if (fileConstants.mergedJar.exists()) {
            return;
        }

        try (JarMerger jarMerger = new JarMerger(clientJar, serverJar, fileConstants.mergedJar)) {
            jarMerger.merge();
        }
    }
}