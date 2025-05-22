package com.yognevoy.bxcontrollernavigator

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.PhpFile
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.ArrayHashElement
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

class BXSettingsParser(private val project: Project) {

    fun parseControllerNamespaces(settingsFile: VirtualFile): Map<String, String> {
        val namespaces = mutableMapOf<String, String>()

        val psiFile = PsiManager.getInstance(project).findFile(settingsFile) as? PhpFile ?: return namespaces

        val arrays = PsiTreeUtil.findChildrenOfType(psiFile, ArrayCreationExpression::class.java)

        for (array in arrays) {
            val hashElements = PsiTreeUtil.getChildrenOfAnyType(array, ArrayHashElement::class.java)
            for (element in hashElements) {
                val key = element.key
                if (key is StringLiteralExpression && key.contents == "controllers") {
                    val value = element.value
                    if (value is ArrayCreationExpression) {
                        extractNamespaces(value, namespaces)
                    }
                }
            }
        }

        return namespaces
    }

    private fun extractNamespaces(array: ArrayCreationExpression, namespaces: MutableMap<String, String>) {
        val hashElements = PsiTreeUtil.findChildrenOfType(array, ArrayHashElement::class.java)

        for (hashElement in hashElements) {
            val key = hashElement.key
            if (key is StringLiteralExpression && key.contents == "value") {
                val valueArray = hashElement.value as? ArrayCreationExpression ?: continue

                val valueHashElements = PsiTreeUtil.findChildrenOfType(valueArray, ArrayHashElement::class.java)
                for (valueHashElement in valueHashElements) {
                    val valueKey = valueHashElement.key
                    if (valueKey is StringLiteralExpression && valueKey.contents == "namespaces") {
                        val namespacesArray = valueHashElement.value as? ArrayCreationExpression ?: continue

                        val namespaceElements = PsiTreeUtil.findChildrenOfType(namespacesArray, ArrayHashElement::class.java)
                        for (namespaceElement in namespaceElements) {
                            val nsKey = namespaceElement.key as? StringLiteralExpression ?: continue
                            val nsValue = namespaceElement.value as? StringLiteralExpression ?: continue

                            val namespace = nsKey.contents.trim('\\')

                            val parts = namespace.split("\\")
                            val lastPart = parts.last()

                            val controllerPath = "lib/${lastPart}"

                            namespaces[nsValue.contents] = controllerPath
                        }
                    }
                }
            }
        }
    }
}
