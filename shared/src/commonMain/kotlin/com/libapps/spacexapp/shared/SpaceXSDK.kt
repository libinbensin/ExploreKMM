package com.libapps.spacexapp.shared

import com.libapps.spacexapp.shared.cache.Database
import com.libapps.spacexapp.shared.cache.DatabaseDriverFactory
import com.libapps.spacexapp.shared.network.SpaceXApi
import com.libapps.spacexapp.shared.entity.RocketLaunch

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}