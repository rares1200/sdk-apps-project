package ro.weekendrrsapps.sdk.utils


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.fragment.app.Fragment
import java.io.IOException
import java.io.InputStream
import kotlin.math.min
import kotlin.math.roundToInt


object ImageUtils {

    const val REQUEST_IMAGE_CAPTURE = 1
    const val REQUEST_IMAGE_GALLERY = 2

    private const val MAX_IMAGE_SIZE = 600f
    const val BIG_IMAGE_SIZE = 1000f

    fun loadImage(view: ImageView, url: String, placeholderResId: Int) {
        Glide.with(view)
            .load(url)
            .fitCenter()
            .placeholder(placeholderResId)
            .into(view)
    }

    fun loadCircularImageFromUrl(imageView: ImageView, url: String, placeholderResId: Int) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholderResId)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    fun loadCircularImageFromBitmap(imageView: ImageView, bitmap: Bitmap, placeholderResId: Int) {
        Glide.with(imageView.context)
            .load(bitmap)
            .placeholder(placeholderResId)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    fun dispatchTakePictureIntent(owner: Fragment) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            owner.context?.let { ctx ->
                takePictureIntent.resolveActivity(ctx.packageManager)?.also {
                    owner.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }

        }
    }

    fun startPhotoPicker(owner: Fragment) {
        Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }.also { loadPictureIntent ->
            owner.context?.let { ctx ->
                loadPictureIntent.resolveActivity(ctx.packageManager)?.also {
                    owner.startActivityForResult(loadPictureIntent, REQUEST_IMAGE_GALLERY)
                }
            }

        }
    }

    @Suppress("SameParameterValue")
    fun getScaledImage(inputStream: InputStream?,  maxImageSize: Float = MAX_IMAGE_SIZE): Bitmap? {
        if (inputStream == null) return null
        val realImage = BitmapFactory.decodeStream(inputStream)
        val ratio = min(
            maxImageSize / realImage.width,
            maxImageSize / realImage.height
        )
        val width = (ratio * realImage.width).roundToInt()
        val height = (ratio * realImage.height).roundToInt()


        var scaledImage = Bitmap.createScaledBitmap(realImage, width, height, true)
        try {
            val exif = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ExifInterface(inputStream)
            } else {
                ExifInterface("")
            }
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    matrix.postRotate(90f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    matrix.postRotate(180f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    matrix.postRotate(270f)
                }
                ExifInterface.ORIENTATION_UNDEFINED -> {

                }
            }
            scaledImage = Bitmap.createBitmap(
                scaledImage,
                0,
                0,
                scaledImage.width,
                scaledImage.height,
                matrix,
                true
            )

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return scaledImage
    }

    fun scaleBitmapImage(realImage: Bitmap, maxImageSize: Float = MAX_IMAGE_SIZE): Bitmap? {
        val ratio = min(
            maxImageSize / realImage.width,
            maxImageSize / realImage.height
        )
        val width = (ratio * realImage.width).roundToInt()
        val height = (ratio * realImage.height).roundToInt()


        return Bitmap.createScaledBitmap(realImage, width, height, true)
    }


}