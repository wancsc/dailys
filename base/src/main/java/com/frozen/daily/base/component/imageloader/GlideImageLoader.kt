package com.frozen.daily.base.component.imageloader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class GlideImageLoader : ImageLoaderImpl {
    companion object {
        fun getInstance() = INNER.INSTANCE
    }

    override fun getBitmap(context: Context, url: String, listener: ImageLoaderListener<Bitmap>) {
        GlideApp.with(context).asBitmap().listener(listener).load(url).submit().get()
    }

    override fun clearCache(context: Context) {
        GlideApp.get(context).clearDiskCache()
    }

    @SuppressLint("CheckResult")
    override fun showImage(context: Context, options: ImageLoaderOptions) {
        if (options.mImageView == null || options.mImageView !is ImageView) {
            return
        }

        val load = GlideApp.with(context).load(
                if (options.mResource != null) {
                    options.mResource
                } else {
                    options.mUrl
                }
        )

        if (options.mErrDrawable != -1) {
            load.error(options.mErrDrawable)
        }
        if (options.mPlaceholder != -1) {
            load.placeholder(options.mPlaceholder)
        }

        val transformer = if (options.mISCircle) {
            CircleCrop()
        } else {
            null
        }

        load.diskCacheStrategy(options.mDiskCacheStrategy)


        load.into(options.mImageView as ImageView)
    }

    private object INNER {
        val INSTANCE = GlideImageLoader()
    }
}