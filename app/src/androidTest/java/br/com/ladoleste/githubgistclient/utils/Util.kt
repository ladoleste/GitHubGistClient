/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.ladoleste.githubgistclient.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.blockingObserver(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}

fun readFromAssets(cx: Context, fileName: String, indice: Int = 0): String {
    val builder = StringBuilder()
    try {
        val stream = cx.assets.open(fileName)
        val bReader = BufferedReader(InputStreamReader(stream, "UTF-8") as Reader?)
        var line: String?
        while (true) {
            line = bReader.readLine()
            if (line == null) {
                break
            }
            builder.append(line)
        }
    } catch (e: IOException) {
        Timber.e(e)
    }

    return builder.toString().substring(indice)
}

fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerViewId)
}