package ro.weekendrrsapps.sdk.utils

import android.view.View

interface RecyclerViewItemClickListenerWithView {
    fun onItemClick(position: Int, view: View)
}

interface RecyclerViewItemClickListener {
    fun onItemClick(position: Int)
}