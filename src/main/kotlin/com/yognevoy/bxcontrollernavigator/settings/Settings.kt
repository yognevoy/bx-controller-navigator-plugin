package com.yognevoy.bxcontrollernavigator.settings

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "BXControllerNavigatorSettings",
    storages = [Storage(StoragePathMacros.WORKSPACE_FILE)]
)
class Settings : PersistentStateComponent<Settings> {

    /**
     * Path to the local directory in Bitrix project structure.
     */
    var localPath: String = "/public/www/"

    /**
     * Returns current state of settings.
     */
    override fun getState(): Settings = this

    /**
     * Loads state from the saved settings.
     */
    override fun loadState(state: Settings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        /**
         * Gets the settings instance for the current project.
         */
        fun getInstance(project: Project): Settings {
            return project.getService(Settings::class.java)
        }
    }
}
