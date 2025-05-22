package com.yognevoy.bxcontrollernavigator

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.yognevoy.bxcontrollernavigator.settings.Settings

class BXControllerResolver(private val project: Project) {

    /**
     * Finds the virtual file corresponding to a Bitrix controller path.
     * Searches for .settings.php files in the module directory to determine
     * the correct controller location based on namespace mappings.
     *
     * @param path The parsed Bitrix controller path
     * @return The VirtualFile of the controller
     */
    fun findFile(path: BXControllerPath): VirtualFile? {
        val vendor = path.vendor.lowercase()
        val moduleName = path.module.lowercase()
        val controllerName = path.controller

        val settings = Settings.getInstance(project)
        val localPath = settings.localPath.removeSuffix("/")

        val modulePath = "${localPath}/local/modules/${vendor}.${moduleName}"

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

    /**
     * Helper method to find a file by its relative path.
     *
     * @param path The relative path to the file
     * @return The VirtualFile
     */
    private fun findFileByPath(path: String): VirtualFile? {
        val baseDir = project.baseDir ?: return null
        return baseDir.findFileByRelativePath(path)
    }
}
