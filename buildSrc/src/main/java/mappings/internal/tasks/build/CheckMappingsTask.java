package mappings.internal.tasks.build;

import cuchaz.enigma.command.CheckMappingsCommand;
import mappings.internal.Constants;
import mappings.internal.tasks.DefaultMappingsTask;
import mappings.internal.tasks.setup.MergeJarsTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class CheckMappingsTask extends DefaultMappingsTask {
    public static final String TASK_NAME = "checkMappings";
    private final File mappings = getProject().file("mappings");

    public CheckMappingsTask() {
        super(Constants.Groups.BUILD_MAPPINGS_GROUP);
        getInputs().dir(mappings);
        this.dependsOn(MergeJarsTask.TASK_NAME);
    }

    @TaskAction
    public void checkMappings() {
        getLogger().lifecycle(":checking mappings");

        String[] args = new String[]{
                fileConstants.mergedJar.getAbsolutePath(),
                mappings.getAbsolutePath()
        };

        try {
            new CheckMappingsCommand().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}