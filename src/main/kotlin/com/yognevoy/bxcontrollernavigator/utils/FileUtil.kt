package com.yognevoy.bxcontrollernavigator.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager

object FileUtil {

    /**
     * Helper method to find a file by its relative path.
     *
     * @param project The current project
     * @param path The relative path to the file
     * @return The VirtualFile
     */
    fun findFileByPath(project: Project, path: String): VirtualFile? {
        val basePath = project.basePath ?: return null
        return VirtualFileManager.getInstance().findFileByUrl("file://$basePath/$path")
    }
}
