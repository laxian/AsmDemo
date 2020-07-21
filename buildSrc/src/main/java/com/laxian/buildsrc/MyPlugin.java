package com.laxian.buildsrc;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("MyPlugin -> apply");
        AppExtension ext = project.getExtensions().findByType(AppExtension.class);
        assert ext != null;
        ext.registerTransform(new AsmTransform(project));
    }
}
