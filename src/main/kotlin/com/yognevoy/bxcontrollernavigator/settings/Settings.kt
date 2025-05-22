package com.yognevoy.bxcontrollernavigator.settings

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "BXControllerNavigatorSettings",
    storages = [Storage(StoragePathMacros.WORKSPACE_FILE)]
)
class Settings : PersistentStateComponent<Settings> {
    var localPath: String = "/public/www/"

    override fun getState(): Settings = this

    override fun loadState(state: Settings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(project: Project): Settings {
            return project.getService(Settings::class.java)
        }
    }
}
