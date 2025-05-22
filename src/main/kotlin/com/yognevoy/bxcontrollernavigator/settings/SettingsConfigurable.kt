package com.yognevoy.bxcontrollernavigator.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.*

class SettingsConfigurable(private val project: Project) : Configurable {

    /**
     * UI panel reference containing the settings controls.
     */
    private var panel: SettingsPanel? = null

    /**
     * Returns the display name shown in the Settings dialog.
     */
    override fun getDisplayName(): String = "BX Controller Navigator"

    /**
     * Creates the settings component UI.
     */
    override fun createComponent(): JComponent? {
        panel = SettingsPanel()
        return panel?.mainPanel
    }

    /**
     * Checks if settings have been modified from their saved state.
     * @return true if settings were modified, false otherwise
     */
    override fun isModified(): Boolean {
        val settings = Settings.getInstance(project)
        return panel?.localPathField?.text != settings.localPath
    }

    /**
     * Applies the current UI settings to the persistent settings.
     */
    override fun apply() {
        val settings = Settings.getInstance(project)
        panel?.let {
            settings.localPath = it.localPathField.text
        }
    }

    /**
     * Resets UI controls to match the current persistent settings.
     */
    override fun reset() {
        val settings = Settings.getInstance(project)
        panel?.localPathField?.text = settings.localPath
    }

    /**
     * Cleanup resources when settings panel is closed.
     */
    override fun disposeUIResources() {
        panel = null
    }
}
