package com.dnd_8th_4_android.wery.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class MultiPartFileUtil(private val mContext: Context, private val fileName: String) {

    fun uriToFile(uri: Uri?): MultipartBody.Part {
        val options = BitmapFactory.Options()
        val inputStream: InputStream =
            requireNotNull(mContext.contentResolver.openInputStream(uri!!))

        var bitmap: Bitmap? = null

        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(mContext.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(mContext.contentResolver, uri)
            BitmapFactory.decodeStream(inputStream, null, options)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        val fileBody = byteArrayOutputStream.toByteArray()
            .toRequestBody(
                "image/*".toMediaTypeOrNull(),
                0
            )

        return MultipartBody.Part.createFormData(
            fileName,
            File(uri.toString()).name + ".jpg",
            fileBody
        )
    }

    fun httpToFile(strImageURL: String): MultipartBody.Part? {
        val options = BitmapFactory.Options()
        var imgBitmap: Bitmap? = null

        try {
            val url = URL(strImageURL)
            val conn: URLConnection = url.openConnection()
            conn.connect()

            val nSize = conn.contentLength
            val bis: BufferedInputStream = BufferedInputStream(conn.getInputStream(), nSize)
            imgBitmap = BitmapFactory.decodeStream(bis, null, options)

            val byteArrayOutputStream = ByteArrayOutputStream()
            imgBitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
            val fileBody = byteArrayOutputStream.toByteArray()
                .toRequestBody(
                    "image/*".toMediaTypeOrNull(),
                    0
                )

            val part = MultipartBody.Part.createFormData(
                fileName,
                strImageURL,
                fileBody
            )

            bis.close()

            return part

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return null
    }
}