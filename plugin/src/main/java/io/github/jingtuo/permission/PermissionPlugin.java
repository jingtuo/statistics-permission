/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.github.jingtuo.permission;

import org.gradle.api.*;
import org.gradle.api.plugins.PluginContainer;
import io.github.jingtuo.permission.v4.StatisticsPermissionExtForV4;
import io.github.jingtuo.permission.v4.StatisticsPermissionTaskForV4;

import java.util.Locale;

/**
 * Statistics Permission Plugin.
 *
 * Android Task按照group分类:
 * 1. build: assemble, assembleAndroidTest, build,
 *          buildDependents, buildNeeded, clean
 * 2. help: buildEnvironment, dependencies, dependencyInsight, help,
 *          javaToolchains, outgoingVariants, projects, properties, tasks
 * 3. verification: check, connectedCheck, deviceCheck, lint, lintFix
 * 4. Android: sourceSets
 * 5. Install: uninstallAll
 * 6. null: compileLint, components, consumeConfigAttr, dependentComponents,
 *          extractProguardFiles, model, preBuild, resolveConfigAttr
 */
public class PermissionPlugin implements Plugin<Project> {

    public static final String PLUGIN_ANDROID_APP = "com.android.application";
    public static final String PLUGIN_ANDROID_LIB = "com.android.lib";

    public static final int GRADLE_MAJOR_VERSION = 7;

    public void apply(Project project) {
        PluginContainer pluginContainer = project.getPlugins();
        if (pluginContainer.hasPlugin(PLUGIN_ANDROID_APP)
                || pluginContainer.hasPlugin(PLUGIN_ANDROID_LIB)) {
            System.out.println("This is an android application/library");
            String gradleVersion = project.getGradle().getGradleVersion();
            System.out.println("current gradle version: " + gradleVersion);
            String[] versionArray = gradleVersion.split("\\.");
            int majorVersion = GRADLE_MAJOR_VERSION;
            if (versionArray.length >= 1) {
                try {
                    majorVersion = Integer.parseInt(versionArray[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String name = project.getName();
            String first = name.substring(0, 1).toUpperCase(Locale.getDefault());
            String second = "";
            if (name.length() > 1) {
                second = name.substring(1);
            }
            String taskName = "statistics" + first + second + "Permission";
            if (majorVersion <= 4) {
                //4.9版本, Extension、Task不支持接口类、抽象类
                StatisticsPermissionExtForV4 extension = project.getExtensions()
                        .create("statisticsPermission", StatisticsPermissionExtForV4.class);
                project.getTasks().register(taskName, StatisticsPermissionTaskForV4.class, task -> {
                            task.setAndroidManifestRelativePath(extension.getAndroidManifestRelativePath());
                            task.setDataExportRelativePath(extension.getDataExportRelativePath());
                        });
            } else {
                StatisticsPermissionExtension extension = project.getExtensions()
                        .create("statisticsPermission", StatisticsPermissionExtension.class);
                project.getTasks().register(taskName, StatisticsPermissionTask.class, task -> {
                            if (extension.getAndroidManifestRelativePath() != null) {
                                task.getAndroidManifestRelativePath().set(extension.getAndroidManifestRelativePath());
                            }
                            if (extension.getDataExportRelativePath() != null) {
                                task.getDataExportRelativePath().set(extension.getDataExportRelativePath());
                            }
                        });
            }
        }
    }
}
