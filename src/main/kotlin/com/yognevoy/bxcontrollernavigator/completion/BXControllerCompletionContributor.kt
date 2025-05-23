package com.yognevoy.bxcontrollernavigator.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns

class BXControllerCompletionContributor : CompletionContributor() {

    /**
     * Registers a completion provider for JavaScript string literals.
     */
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            BXControllerCompletionProvider()
        )
    }
}
