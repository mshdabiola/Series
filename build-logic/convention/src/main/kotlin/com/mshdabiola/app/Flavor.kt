package com.mshdabiola.app

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    subjectType,
    premiumType
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
enum class Flavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionNameSuffix: String? = null
) {
    Physics(FlavorDimension.subjectType, applicationIdSuffix = ".physics", "-physics"),

    // Math(FlavorDimension.subjectType, applicationIdSuffix = ".math", "-math"),
    Free(FlavorDimension.premiumType, applicationIdSuffix = ".free", "-free"),
    Paid(FlavorDimension.premiumType, applicationIdSuffix = ".paid", "-paid")
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: Flavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.subjectType.name
        flavorDimensions += FlavorDimension.premiumType.name
        productFlavors {
            Flavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            this.applicationIdSuffix = it.applicationIdSuffix
                        }
                        if (it.versionNameSuffix != null) {
                            this.versionNameSuffix = it.versionNameSuffix
                        }
                    }
                }
            }
        }
    }
}
