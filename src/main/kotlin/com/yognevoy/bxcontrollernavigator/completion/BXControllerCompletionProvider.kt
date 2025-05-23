package com.yognevoy.bxcontrollernavigator.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScopes
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.psi.PhpFile
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.yognevoy.bxcontrollernavigator.BXSettingsParser
import com.yognevoy.bxcontrollernavigator.settings.Settings
import com.yognevoy.bxcontrollernavigator.utils.BXScriptContextUtil

class BXControllerCompletionProvider : CompletionProvider<CompletionParameters>() {

    /**
     * Provides code completion for controller paths in ajax calls.
     */
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val element = parameters.position.parent as? JSLiteralExpression ?: return
        if (!element.isStringLiteral) {
            return
        }

        if (!BXScriptContextUtil.isBXAjaxCall(element)) {
            return
        }

        val project = parameters.position.project
        val settings = Settings.getInstance(project)
        val localPath = settings.localPath.removeSuffix("/")

        val localDir = project.baseDir?.findFileByRelativePath("${localPath}/local/") ?: return
        val modulesDir = localDir.findFileByRelativePath("modules/") ?: return

        for (vendorModuleDir in modulesDir.children) {
            if (!vendorModuleDir.isDirectory) {
                continue
            }

            val parts = vendorModuleDir.name.split(".")
            if (parts.size != 2) {
                continue
            }

            val vendor = parts[0]
            val module = parts[1]

            val settingsFiles = FilenameIndex.getVirtualFilesByName(
                ".settings.php",
                GlobalSearchScopes.directoryScope(project, vendorModuleDir, true)
            )
            if (settingsFiles.isEmpty()) {
                continue
            }

            val settingsFile = settingsFiles.first()

            val settingsParser = BXSettingsParser(project)
            val namespaces = settingsParser.parseControllerNamespaces(settingsFile)

            if (namespaces.isEmpty()) {
                continue
            }

            for ((namespace, controllerPath) in namespaces) {
                val controllerDir = vendorModuleDir.findFileByRelativePath(controllerPath) ?: continue

                val phpFiles = mutableListOf<VirtualFile>()
                for (child in controllerDir.children) {
                    if (child.extension == "php") {
                        phpFiles.add(child)
                    }
                }

                for (phpFile in phpFiles) {
                    val psiFile = PsiManager.getInstance(project).findFile(phpFile) as? PhpFile ?: continue

                    val phpClasses = mutableListOf<PhpClass>()

                    val classes = PsiTreeUtil.findChildrenOfType(psiFile, PhpClass::class.java)
                    for (phpClass in classes) {
                        phpClasses.add(phpClass)
                    }

                    for (phpClass in phpClasses) {
                        val controllerName = phpClass.name
                        val methods = phpClass.ownMethods

                        for (method in methods) {
                            if (method.name.endsWith("Action")) {
                                val actionName = method.name.removeSuffix("Action")

                                val fullPath =
                                    "$vendor:$module.$namespace.${controllerName.lowercase()}.${actionName.lowercase()}"

                                result.addElement(
                                    LookupElementBuilder.create(fullPath)
                                        .withPresentableText(fullPath)
                                        .withCaseSensitivity(false)
                                        .withTypeText("Controller Action")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
