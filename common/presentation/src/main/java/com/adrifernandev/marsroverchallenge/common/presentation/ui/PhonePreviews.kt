package com.adrifernandev.marsroverchallenge.common.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "1-Light Mode ESP",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    locale = "es",
)
@Preview(
    name = "2-Dark Mode ENG",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    locale = "en",
    device = Devices.PIXEL_4A
)
@Preview(
    name = "3-Small device",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    locale = "en",
    device = Devices.NEXUS_5
)
@Preview(
    name = "4-Foldable device",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    locale = "en",
    device = Devices.PIXEL_FOLD
)

annotation class PhonePreviews
