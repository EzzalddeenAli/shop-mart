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
package ph.mart.shopmart.util

/**
 * Extension functions.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ph.mart.shopmart.Event
import ph.mart.shopmart.EventObserver
import ph.mart.shopmart.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.layout_empty_cart.view.*
import kotlinx.android.synthetic.main.layout_no_account.view.*

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, EventObserver { event ->
        Snackbar.make(this, context.getString(event), timeLength).show()
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

fun ViewGroup.showEmptyCartView(retry: () -> Unit) {
    val noAccountView = LayoutInflater.from(context)
        .inflate(R.layout.layout_empty_cart, this, false)

    addView(noAccountView)

    noAccountView.buttonShopNow.setOnClickListener {
        removeView(noAccountView)
        retry()
    }
}

fun ImageView.loadImage(referenceFromUrl: StorageReference) {
    GlideApp.with(this)
        .load(referenceFromUrl)
        .centerCrop()
        .into(this)
}