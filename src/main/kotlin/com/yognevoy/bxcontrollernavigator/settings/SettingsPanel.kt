package com.yognevoy.bxcontrollernavigator.settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class SettingsPanel {
    val localPathField = JBTextField()
    val mainPanel: JPanel

    init {
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Path to local:"), localPathField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}
