package com.yognevoy.bxcontrollernavigator.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.*

class SettingsConfigurable(private val project: Project) : Configurable {
    private var panel: SettingsPanel? = null

    override fun getDisplayName(): String = "BX Controller Navigator"

    override fun createComponent(): JComponent? {
        panel = SettingsPanel()
        return panel?.mainPanel
    }

    override fun isModified(): Boolean {
        val settings = Settings.getInstance(project)
        return panel?.localPathField?.text != settings.localPath
    }

    override fun apply() {
        val settings = Settings.getInstance(project)
        panel?.let {
            settings.localPath = it.localPathField.text
        }
    }

    override fun reset() {
        val settings = Settings.getInstance(project)
        panel?.localPathField?.text = settings.localPath
    }

    override fun disposeUIResources() {
        panel = null
    }
}
