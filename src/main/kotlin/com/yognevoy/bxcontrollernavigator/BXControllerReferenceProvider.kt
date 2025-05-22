package com.yognevoy.bxcontrollernavigator

import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext

class BXControllerReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(
        element: PsiElement,
        context: ProcessingContext
    ): Array<PsiReference> {
        if (element !is JSLiteralExpression || !element.isStringLiteral) {
            return PsiReference.EMPTY_ARRAY
        }

        val stringValue = element.stringValue ?: return PsiReference.EMPTY_ARRAY

        if (!BXControllerPathParser.isControllerPath(stringValue)) {
            return PsiReference.EMPTY_ARRAY
        }

        val valueRange = TextRange(1, stringValue.length + 1)
        return arrayOf(BXControllerReference(element, valueRange, stringValue))
    }
}
