package com.supersonic.heartrate.screens.homepage

import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.supersonic.heartrate.util.heartRateMeasurement.HeartRateAnalyzer

@Composable
fun CameraPreview(
    onHeartRateCalculated: (Int) -> Unit,
    isFingerDetected: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }

    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var camera: Camera? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        val executor = ContextCompat.getMainExecutor(context)
        val cameraProvider = cameraProviderFuture.get()

        val preview: Preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    executor,
                    HeartRateAnalyzer(onHeartRateCalculated, isFingerDetected)
                )
            }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // Function to start the camera
        fun startCamera() {
            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
                camera?.cameraControl?.enableTorch(true)
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Use case binding failed", exc)
            }
        }

        // Start the camera when the effect is launched
        startCamera()

        onDispose {
            // Turn off the torch when the composable is disposed
            camera?.cameraControl?.enableTorch(false)
            // Unbind all use cases to release the camera
            cameraProvider.unbindAll()
        }
    }
}

