package com.yognevoy.bxcontrollernavigator

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.psi.PhpFile

class BXControllerReference(
    element: PsiElement,
    range: TextRange,
    private val path: String
) : PsiReferenceBase<PsiElement>(element, range) {

    override fun resolve(): PsiElement? {
        val project = element.project

        val controllerPath = BXControllerPathParser.parse(path) ?: return null

        val resolver = BXControllerResolver(project)
        val controllerFile = resolver.findFile(controllerPath) ?: return null

        val psiFile = PsiManager.getInstance(project).findFile(controllerFile) as PhpFile

        val phpIndex = PhpIndex.getInstance(project)
        val phpClasses = phpIndex.getClassesByName(controllerPath.controller)

        for (phpClass in phpClasses) {
            if (phpClass.name.equals(controllerPath.controller, ignoreCase = true)) {
                val actionMethodName = "${controllerPath.action}Action"
                val methods = phpClass.methods
                for (method in methods) {
                    if (method.name.equals(actionMethodName, ignoreCase = true)) {
                        return method
                    }
                }
            }
        }

        return psiFile
    }

    override fun getVariants(): Array<Any> = emptyArray()
}
