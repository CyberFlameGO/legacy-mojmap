package mappings.internal.tasks.build;

import cuchaz.enigma.command.DropInvalidMappingsCommand;
import org.gradle.api.tasks.TaskAction;
import mappings.internal.Constants;
import mappings.internal.tasks.DefaultMappingsTask;
import mappings.internal.tasks.setup.DownloadMinecraftServerTask;

import java.io.File;

public class DropInvalidMappingsTask extends DefaultMappingsTask {
    public static final String TASK_NAME = "dropInvalidMappings";
    private final File mappings = getProject().file("mappings");

    public DropInvalidMappingsTask() {
        super(Constants.Groups.BUILD_MAPPINGS_GROUP);
        getInputs().dir(mappings);
        this.dependsOn(DownloadMinecraftServerTask.TASK_NAME);
    }

    @TaskAction
    public void dropInvalidMappings() {
        getLogger().info(":dropping invalid mappings");

        String[] args = new String[]{
                fileConstants.minecraftJar.getAbsolutePath(),
                mappings.getAbsolutePath()
        };

        try {
            new DropInvalidMappingsCommand().run(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}