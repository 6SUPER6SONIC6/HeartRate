package com.supersonic.heartrate.util.heartRateMeasurement

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream

class HeartRateAnalyzer(
    private val onHeartRateCalculated: (Int) -> Unit,
    private val isFingerDetected: (Boolean) -> Unit
) : ImageAnalysis.Analyzer {
    private var lastAnalyzedTimestamp = 0L
    private val frameTimestamps = ArrayList<Long>()
    private val redIntensity = ArrayList<Double>()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()

        if (currentTimestamp - lastAnalyzedTimestamp >= 100) {
            val bitmap = yuvToRgb(image.image)
            if (bitmap == null) {
                Log.e("HeartRateAnalyzer", "Bitmap creation failed")
                image.close()
                return
            }

            if (!isFingerCoveringCamera(bitmap)) {
                Log.d("HeartRateAnalyzer", "Finger not detected on camera")
                onHeartRateCalculated(0)
                isFingerDetected(false)
                image.close()
                return
            }

            val avgRedIntensity = calculateAverageRedIntensity(bitmap)
            redIntensity.add(avgRedIntensity)
            frameTimestamps.add(currentTimestamp)

            if (frameTimestamps.size > 30) {
                val heartRate = calculateHeartRate(frameTimestamps, redIntensity)
                onHeartRateCalculated(heartRate)
                isFingerDetected(true)
                frameTimestamps.clear()
                redIntensity.clear()
            }

            lastAnalyzedTimestamp = currentTimestamp
        }

        image.close()
    }

    private fun calculateAverageRedIntensity(bitmap: Bitmap): Double {
        var redSum = 0.0
        var pixelCount = 0

        for (y in 0 until bitmap.height step 10) {
            for (x in 0 until bitmap.width step 10) {
                val pixel = bitmap.getPixel(x, y)
                val red = (pixel shr 16) and 0xFF
                redSum += red
                pixelCount++
            }
        }

        return if (pixelCount > 0) redSum / pixelCount else 0.0
    }

    private fun calculateHeartRate(timestamps: List<Long>, intensities: List<Double>): Int {
        val smoothedIntensities = smoothData(intensities, windowSize = 5)
        val peaks = findPeaks(smoothedIntensities)
        if (peaks.size < 2) return 0

        val durations = peaks.zipWithNext { a, b -> timestamps[b] - timestamps[a] }
        val averageDuration = durations.average()

        return (60000 / averageDuration).toInt()
    }

    private fun findPeaks(intensities: List<Double>): List<Int> {
        val peaks = mutableListOf<Int>()
        for (i in 1 until intensities.size - 1) {
            if (intensities[i] > intensities[i - 1] && intensities[i] > intensities[i + 1]) {
                peaks.add(i)
            }
        }
        return peaks
    }

    private fun smoothData(intensities: List<Double>, windowSize: Int): List<Double> {
        val smoothedIntensities = mutableListOf<Double>()
        for (i in intensities.indices) {
            val start = maxOf(0, i - windowSize / 2)
            val end = minOf(intensities.size, i + windowSize / 2)
            val window = intensities.subList(start, end)
            smoothedIntensities.add(window.average())
        }
        return smoothedIntensities
    }
}

fun yuvToRgb(image: Image?): Bitmap? {
    if (image == null) return null

    val nv21 = yuv420ToNv21(image)
    val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(android.graphics.Rect(0, 0, image.width, image.height), 100, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

private fun yuv420ToNv21(image: Image): ByteArray {
    val yBuffer = image.planes[0].buffer // Y
    val uBuffer = image.planes[1].buffer // U
    val vBuffer = image.planes[2].buffer // V

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    return nv21
}



private fun isFingerCoveringCamera(bitmap: Bitmap): Boolean {
    val regionWidth = bitmap.width / 4
    val regionHeight = bitmap.height / 4
    val startX = bitmap.width / 2 - regionWidth / 2
    val startY = bitmap.height / 2 - regionHeight / 2

    var redPixelCount = 0
    var totalPixelCount = 0

    for (y in startY until startY + regionHeight) {
        for (x in startX until startX + regionWidth) {
            val pixel = bitmap.getPixel(x, y)
            val red = (pixel shr 16) and 0xFF
            val green = (pixel shr 8) and 0xFF
            val blue = pixel and 0xFF

            if (red > green && red > blue) {
                redPixelCount++
            }
            totalPixelCount++
        }
    }

    val redRatio = redPixelCount.toDouble() / totalPixelCount
    Log.d("HeartRateAnalyzer", "Red pixel ratio: $redRatio")
    return redRatio > 0.8 // Adjust this threshold as necessary
}










