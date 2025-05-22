package com.yognevoy.bxcontrollernavigator

data class BXControllerPath(
    val vendor: String,
    val module: String,
    val controllerNamespace: String,
    val controller: String,
    val action: String
)
