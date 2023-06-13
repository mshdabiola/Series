package com.mshdabiola.navigation

import com.arkivanov.decompose.ComponentContext

class MainComponent(private val componentContext: ComponentContext): ComponentContext by componentContext


class QuestionComponent(private val componentContext: ComponentContext): ComponentContext by componentContext

class FinishComponent(private val componentContext: ComponentContext) : ComponentContext by componentContext
class ProfileComponent(private val componentContext: ComponentContext): ComponentContext by componentContext

class StatisticComponent(private val componentContext: ComponentContext): ComponentContext by componentContext


