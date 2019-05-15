/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.npm

import com.google.gson.Gson
import org.gradle.api.Project

interface NpmApi {
    fun setup(project: Project)

    @Suppress("EXPOSED_PARAMETER_TYPE")
    fun resolveProject(npmPackage: NpmResolver.NpmPackage)

    @Suppress("EXPOSED_PARAMETER_TYPE")
    fun resolveRootProject(
        rootProject: Project,
        subprojects: MutableList<NpmResolver.NpmPackage>
    )

    /**
     * Change contents of root's project `package.json`.
     * @return true if `package.json` is required even it is empty
     */
    @Suppress("EXPOSED_PARAMETER_TYPE")
    fun hookRootPackage(
        rootProject: Project,
        allWorkspaces: Collection<NpmResolver.NpmPackage>,
        gson: Gson
    ): Boolean = false

    fun cleanProject(project: Project) {
    }

    companion object {
        fun resolveOperationDescription(packageManagerTitle: String): String =
            "Resolving NPM dependencies using $packageManagerTitle"
    }
}