package com.yognevoy.bxcontrollernavigator

object BXControllerPathParser {

    private val CONTROLLER_PATTERN = Regex("([\\w]+):([\\w.]+)\\.([\\w.]+)\\.([\\w]+)\\.([\\w]+)")

    fun parse(path: String): BXControllerPath? {
        val match = CONTROLLER_PATTERN.matchEntire(path) ?: return null

        return BXControllerPath(
            vendor = match.groupValues[1],
            module = match.groupValues[2],
            controllerNamespace = match.groupValues[3],
            controller = match.groupValues[4],
            action = match.groupValues[5]
        )
    }

    fun isControllerPath(path: String): Boolean {
        return CONTROLLER_PATTERN.matches(path)
    }
}
