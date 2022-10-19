package com.codinginflow.mvvmnewsapp.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.launch

inline fun <RequestType, ResultType> networkBoundResource(
    /**
     * Get data from the BD to the the viewModel
     */
    crossinline query: () -> Flow<ResultType>,
    /**
     * get Data from the web to the repository
     */
    crossinline fetch: suspend () -> RequestType,

    /**
     * Save data from the repository to the DB
     */
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) =
    /**Channel Flow can work concurrently
     * Cold flow keeps multiple values while hot flow only holds one the latest value
     */
    channelFlow {
        /**Channel Flow can work concurrently
         * Cold flow keeps multiple values while hot flow only holds one the latest value
         */
        val data = query().first()

        if (shouldFetch(data)) {
            val loading = launch {
                //emit data to the receive from dp
                query().collect { send(Resource.Loading(it)) }
            }

            try {
                delay(3000)
                //fetch data from web and save to the db
                saveFetchResult(fetch())
                // cancel initial loading emission
                loading.cancel()

                // Start emitting the new saved data
                query().collect { send(Resource.Success(it)) }

            } catch (e: Throwable) {
                // cancel initial loading emission
                loading.cancel()
                // emit error message to the receiver
                query().collect { send(Resource.Error(e, it)) }

            }

        } else {
            query().collect { send(Resource.Success(it)) }
        }


    }