/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.statistics

import com.intellij.internal.statistic.service.fus.collectors.ApplicationUsageTriggerCollector
import com.intellij.internal.statistic.service.fus.collectors.FUSApplicationUsageTrigger
import com.intellij.internal.statistic.service.fus.collectors.FUSUsageContext
import org.jetbrains.kotlin.idea.statistics.StatisticsUtils.Companion.PLUGIN_VERSION

open class KotlinStatisticsTrigger(private val groupIdSufix: String) : ApplicationUsageTriggerCollector() {
    override fun getGroupId() = "statistics.kotlin.$groupIdSufix"

    companion object {
        public fun trigger(clazz: Class<out KotlinStatisticsTrigger>, event: String) {
            FUSApplicationUsageTrigger.getInstance().trigger(clazz, event, FUSUsageContext.create(PLUGIN_VERSION))
        }
    }
}

open class KotlinIdeStatisticsTrigger(groupIdSufix: String) : KotlinStatisticsTrigger("ide.$groupIdSufix")

open class KotlinGradlePluginStatisticsTrigger(groupIdSufix: String) : KotlinStatisticsTrigger("gradle.$groupIdSufix")
open class KotlinMavenPluginStatisticsTrigger(groupIdSufix: String) : KotlinStatisticsTrigger("maven.$groupIdSufix")
open class KotlinJPSPluginStatisticsTrigger(groupIdSufix: String) : KotlinStatisticsTrigger("jps.$groupIdSufix")

class KotlinVersionTrigger : KotlinGradlePluginStatisticsTrigger("kotlin_version")

class KotlinTargetTrigger : KotlinGradlePluginStatisticsTrigger("target")

class KotlinMavenTargetTrigger : KotlinMavenPluginStatisticsTrigger("target")

class KotlinJPSTargetTrigger : KotlinJPSPluginStatisticsTrigger("target")

class KotlinProjectLibraryUsageTrigger : KotlinGradlePluginStatisticsTrigger("library")

open class KotlinIdeActionTrigger(groupIdSufix: String? = null) : KotlinIdeStatisticsTrigger("action" + (if (groupIdSufix != null) ".$groupIdSufix" else ""))

class KotlinIdeRefactoringTrigger : KotlinIdeActionTrigger("refactoring")

class KotlinIdeNewFileTemplateTrigger : KotlinIdeStatisticsTrigger("newFileTempl")