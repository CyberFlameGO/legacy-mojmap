package mappings.internal.tasks.setup;

import mappings.internal.Constants;
import mappings.internal.tasks.DefaultMappingsTask;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class DownloadVersionsManifestTask extends DefaultMappingsTask {
    public static final String TASK_NAME = "downloadVersionsManifest";
    @OutputFile
    private final File manifestFile;

    public DownloadVersionsManifestTask() {
        super(Constants.Groups.SETUP_GROUP);
        this.getInputs().property("currenttime", new Date());

        manifestFile = new File(fileConstants.cacheFilesMinecraft, "version_manifest_v2.json");
    }

    @TaskAction
    public void downloadVersionsManifestTask() throws IOException {
        getLogger().lifecycle(":downloading minecraft versions manifest");
        startDownload()
                .src("https://launchermeta.mojang.com/mc/game/version_manifest_v2.json")
                .dest(manifestFile)
                .overwrite(true)
                .download();
    }

    public File getManifestFile() {
        return manifestFile;
    }
}