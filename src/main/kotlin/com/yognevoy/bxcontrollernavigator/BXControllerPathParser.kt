package com.yognevoy.bxcontrollernavigator

object BXControllerPathParser {

    /**
     * Regular expression pattern to match Bitrix controller paths.
     * Format: vendor:module.namespace.controller.action
     */
    private val CONTROLLER_PATTERN = Regex("([\\w]+):([\\w.]+)\\.([\\w.]+)\\.([\\w]+)\\.([\\w]+)")

    /**
     * Parses a string into a BXControllerPath object.
     *
     * @param path
     * @return BXControllerPath object
     */
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

    /**
     * Checks if a given string matches the Bitrix controller path format.
     *
     * @param path
     * @return true if the string matches the controller path format, false otherwise
     */
    fun isControllerPath(path: String): Boolean {
        return CONTROLLER_PATTERN.matches(path)
    }
}
