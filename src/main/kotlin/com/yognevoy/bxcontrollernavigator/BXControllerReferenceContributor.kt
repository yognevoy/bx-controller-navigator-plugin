package com.yognevoy.bxcontrollernavigator

import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class BXControllerReferenceContributor : PsiReferenceContributor() {

    /**
     * Registers a reference provider for JavaScript string literals.
     *
     * @param registrar
     */
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(JSLiteralExpression::class.java),
            BXControllerReferenceProvider()
        )
    }
}
