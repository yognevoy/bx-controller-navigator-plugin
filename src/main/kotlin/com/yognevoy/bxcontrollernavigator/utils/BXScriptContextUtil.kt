package com.yognevoy.bxcontrollernavigator.utils

import com.intellij.lang.javascript.psi.JSArgumentList
import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.lang.javascript.psi.JSReferenceExpression

object BXScriptContextUtil {
    
    /**
     * List of supported Bitrix AJAX method calls
     */
    private val SUPPORTED_BX_AJAX_METHODS = listOf(
        "BX.ajax.runAction",
    )
    
    /**
     * Checks if the element is inside ajax function call
     *
     * @param element
     * @return true if element is inside supported method call, false otherwise
     */
    fun isBXAjaxCall(element: JSLiteralExpression): Boolean {
        val argumentList = element.parent
        if (argumentList is JSArgumentList) {
            val callExpression = argumentList.parent
            if (callExpression is JSCallExpression) {
                val methodExpression = callExpression.methodExpression
                if (methodExpression is JSReferenceExpression) {
                    val qualifiedName = methodExpression.text
                    return SUPPORTED_BX_AJAX_METHODS.contains(qualifiedName)
                }
            }
        }
        return false
    }
}
