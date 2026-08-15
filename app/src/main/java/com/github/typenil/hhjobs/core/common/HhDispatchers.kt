package com.github.typenil.hhjobs.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val hhDispatcher: HhDispatchers)

enum class HhDispatchers {
    Default,
    IO,
    Main,
}
