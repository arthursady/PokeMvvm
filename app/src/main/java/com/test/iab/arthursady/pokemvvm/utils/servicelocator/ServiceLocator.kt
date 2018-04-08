package com.test.iab.arthursady.pokemvvm.utils.servicelocator

import android.support.annotation.VisibleForTesting
import com.test.iab.arthursady.pokemvvm.utils.repositories.PokelistApiRepository
import com.test.iab.arthursady.pokemvvm.utils.repositories.PokelistRepository
import java.util.concurrent.Executors


interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(): PokelistRepository
}

/**
 * default implementation of ServiceLocator that uses production endpoints.
 */
open class DefaultServiceLocator: ServiceLocator {
    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    override fun getRepository(): PokelistRepository {
        return PokelistApiRepository(networkExecutor = NETWORK_IO)
    }
}