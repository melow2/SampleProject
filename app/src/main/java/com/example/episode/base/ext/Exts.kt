package com.example.episode.base.ext

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.episode.base.viewmodel.AppEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException
import java.io.InputStream
import kotlin.math.roundToInt

inline fun <T : Any> NotNullMutableLiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit,
) = Observer { value: T -> observer(value) }.also { observe(owner, it) }


inline fun <T : Any> LiveData<AppEvent<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) = Observer { event: AppEvent<T>? ->
    event?.getContentIfNotHandled()?.let(observer)
}.also { observe(owner, it) }


open class NotNullLiveData<T : Any>(value: T) : LiveData<T>(value) {
    override fun getValue(): T = super.getValue()!!

    @Suppress("RedundantOverride")
    override fun setValue(value: T) = super.setValue(value)

    @Suppress("RedundantOverride")
    override fun postValue(value: T) = super.postValue(value)
}

/**
 * Live data changed by a value that does not allow null.
 * @author Scarlett
 * @version 1.0.0
 * @since 2021-03-12 오후 2:33
 **/
class NotNullMutableLiveData<T : Any>(value: T) : NotNullLiveData<T>(value) {
    public override fun setValue(value: T) = super.setValue(value)

    public override fun postValue(value: T) = super.postValue(value)
}




@Suppress("nothing_to_inline")
inline infix fun ViewGroup.inflate(layoutRes: Int) =
    LayoutInflater.from(context).inflate(layoutRes, this, false)!!

val Context.isOrientationPortrait get() = this.resources.configuration.orientation == ORIENTATION_PORTRAIT

@Suppress("nothing_to_inline")
@ColorInt
inline fun Context.getColorBy(@ColorRes id: Int) = ContextCompat.getColor(this, id)

@Suppress("nothing_to_inline")
inline fun Context.getDrawableBy(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

/**
 * Get uri from any resource type
 * @receiver Context
 * @param resId - Resource id
 * @return - Uri to resource by given id or null
 */
fun Context.uriFromResourceId(@AnyRes resId: Int): Uri? {
    return runCatching {
        val res = this@uriFromResourceId.resources
        Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }.getOrNull()
}

fun Context.dpToPx(dp: Int): Int {
    val displayMetrics = resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

@Suppress("nothing_to_inline")
inline fun Context.toast(
    @StringRes messageRes: Int,
    short: Boolean = true,
) = this.toast(getString(messageRes), short)

@Suppress("nothing_to_inline")
inline fun Context.toast(
    message: String,
    short: Boolean = true,
) =
    Toast.makeText(
        this,
        message,
        if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).apply { show() }!!


enum class SnackbarLength {
    SHORT {
        override val length = Snackbar.LENGTH_SHORT
    },
    LONG {
        override val length = Snackbar.LENGTH_LONG
    },
    INDEFINITE {
        override val length = Snackbar.LENGTH_INDEFINITE
    };

    abstract val length: Int
}

@SuppressLint("Recycle")
fun Context.themeInterpolator(@AttrRes attr: Int): Interpolator {
    return AnimationUtils.loadInterpolator(
        this,
        obtainStyledAttributes(intArrayOf(attr)).use {
            it.getResourceId(0, android.R.interpolator.fast_out_slow_in)
        }
    )
}


inline fun View.snack(
    @StringRes messageRes: Int,
    length: SnackbarLength = SnackbarLength.SHORT,
    crossinline f: Snackbar.() -> Unit = {},
) = snack(resources.getString(messageRes), length, f)

inline fun View.snack(
    message: String,
    length: SnackbarLength = SnackbarLength.SHORT,
    crossinline f: Snackbar.() -> Unit = {},
) = Snackbar.make(this, message, length.length).apply {
    f()
    show()
}

fun Snackbar.action(
    @StringRes actionRes: Int,
    color: Int? = null,
    listener: (View) -> Unit,
) = action(view.resources.getString(actionRes), color, listener)

fun Snackbar.action(
    action: String,
    color: Int? = null,
    listener: (View) -> Unit,
) = apply {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

/**
 * - If the file exists and is not overwritten ==> true, else In case of delete failure ==> false
 * - Create a parent directory, copy the file, and return the target.
 *
 * @param file: file.
 * @param overwrite: overwrite
 * @param bufferSize:  8 * 1024 = 8192b = 8kb = 0.0078mb
 *
 * @author Scarlett
 * @version 1.0.0
 * @since 2021-02-26 오후 2:12
 **/
fun InputStream.copyTo(
    target: File,
    overwrite: Boolean = false,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
): File {
    if (target.exists()) {
        val stillExists = if (!overwrite) true else !target.delete()
        if (stillExists) {
            throw IllegalAccessException("The destination file already exists.")
        }
    }
    target.parentFile?.mkdirs()
    this.use { input ->
        target.outputStream().use { output ->
            input.copyTo(output, bufferSize)
        }
    }

    return target
}

/**
 * If there is a method that needs to be repeated asynchronously, run the block below.
 *
 * @param times: Number of times to repeat.
 * @param initialDelay: Delay time every iteration.
 * @param factor: Delay time multiplied by argument.
 * @param maxDelay: Maximum delay time by coerceAtMost.
 * @author Scarlett
 * @version 1.0.0
 * @since 2021-02-26 오후 1:29
 **/
suspend fun <T> retryIO(
    times: Int,
    initialDelay: Long,
    factor: Double,
    maxDelay: Long = Long.MAX_VALUE,
    block: suspend () -> T,
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}


@Suppress("unused")
inline val Any?.unit
    get() = Unit

inline val ViewGroup.inflater: LayoutInflater get() = LayoutInflater.from(context)


inline fun <reified T : Any, B : ViewDataBinding> RecyclerView.ViewHolder.onlyBind(
    item: Any,
    binding: B,
    crossinline bind: B.(T) -> Unit
) {
    check(item is T) { "${this::class.java.simpleName}::bind only accept ${T::class.java.simpleName}, but item=$item" }
    binding.bind(item)
}


/**
 * Escape file name: only allow letters, numbers, dots and dashes
 */
private fun String.escapeFileName(): String {
    return replace(
        "[^a-zA-Z0-9.\\-]".toRegex(),
        replacement = "_"
    )
}
