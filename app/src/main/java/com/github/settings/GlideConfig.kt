package com.github.settings

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.github.network.ok.createCommonClientBuilder
import java.io.InputStream

@GlideModule
@Excludes(value = [com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule::class])
class GlideConfig : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        DiskCacheStrategy.AUTOMATIC
            .let(RequestOptions()::diskCacheStrategy)
            .let(builder::setDefaultRequestOptions)

        DiskLruCacheFactory(
            "${context.externalCacheDir}/glideCacheImage",
            50 * 1024 * 1024
        ).let(builder::setDiskCache)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = createCommonClientBuilder().build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))
    }
}