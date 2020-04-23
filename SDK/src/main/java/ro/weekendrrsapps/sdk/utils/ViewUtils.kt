package ro.weekendrrsapps.sdk.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ShareCompat

object ViewUtils {

    private const val TAG = "ViewUtils"

    fun openLink(ctx: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(ctx.packageManager) != null) {
            ctx.startActivity(intent)
        } else {
            Log.d(TAG, "Could not open url:$url")
        }
    }

    fun hideSoftKeyboard(view: View) {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun shareLink(ctx: Activity, url: String, chooserTitle: String) {
        ShareCompat.IntentBuilder.from(ctx)
            .setType("text/plain")
            .setChooserTitle(chooserTitle)
            .setText(url)
            .startChooser()
    }

}