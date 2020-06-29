/*
 * Copyright (C) 2019 The Android Open Source Project
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
package com.example.shopmart.util

/**
 * Extension functions.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.shopmart.Event
import com.example.shopmart.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_no_account.view.*

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            Snackbar.make(this, context.getString(it), timeLength).show()
        }
    })
}

fun ViewGroup.showNoAccountView(retry: () -> Unit) {
    val noAccountView = LayoutInflater.from(context)
        .inflate(R.layout.layout_no_account, this, false)

    addView(noAccountView)

    noAccountView.buttonRetry.setOnClickListener {
        removeView(noAccountView)
        retry()
    }
}