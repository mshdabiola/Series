package com.mshdabiola.navigation

import com.arkivanov.decompose.ComponentContext

class MainComponent(private val componentContext: ComponentContext) :
    ComponentContext by componentContext

class QuestionComponent(private val componentContext: ComponentContext) :
    ComponentContext by componentContext
