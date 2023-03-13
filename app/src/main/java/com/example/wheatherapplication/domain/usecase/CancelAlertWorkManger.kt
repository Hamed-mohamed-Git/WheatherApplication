package com.example.wheatherapplication.domain.usecase

import android.content.Context
import androidx.work.WorkManager
import com.example.wheatherapplication.constants.Constants
import javax.inject.Inject

class CancelAlertWorkManger @Inject constructor(
    private val context: Context
) {
    operator fun invoke() =
        WorkManager.getInstance(context).cancelAllWorkByTag(Constants.WORK_MANGER_ALERT_TAG_NAME)
}