package com.yognevoy.bxcontrollernavigator

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope

class BXControllerResolver(private val project: Project) {

    fun findFile(path: BXControllerPath): VirtualFile? {
        val vendor = path.vendor.lowercase()
        val moduleName = path.module.lowercase()
        val controllerName = path.controller

        val modulePath = "/public/www/local/modules/${vendor}.${moduleName}"

        val settingsFiles = FilenameIndex.getVirtualFilesByName(
            ".settings.php",
            GlobalSearchScope.projectScope(project)
        )

        var controllerFile: VirtualFile? = null

        for (settingsFile in settingsFiles) {
            if (settingsFile.path.contains(modulePath)) {
                val settingsParser = BXSettingsParser(project)
                val namespaces = settingsParser.parseControllerNamespaces(settingsFile)

                /*val expectedNamespace = "\\${path.vendor}\\${path.module}\\Controller"
                val controllerDir = namespaces[expectedNamespace]*/
                var controllerDir: String? = null

                if (namespaces.isNotEmpty()) {
                    controllerDir = namespaces.entries.first().value
                }

                if (controllerDir != null) {
                    val controllerFilePath = "${modulePath}/${controllerDir}/${controllerName.lowercase()}.php"
                    controllerFile = findFileByPath(controllerFilePath)
                    if (controllerFile != null) break
                }
            }
        }

        return controllerFile
    }

    private fun findFileByPath(path: String): VirtualFile? {
        val baseDir = project.baseDir ?: return null
        return baseDir.findFileByRelativePath(path)
    }
}
