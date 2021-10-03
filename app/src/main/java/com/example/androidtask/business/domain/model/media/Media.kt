package com.example.androidtask.business.domain.model.media

import com.example.androidtask.business.domain.model.channel.CoverAsset

data class Media(
    val mediaChannel: MediaChannel = MediaChannel(),
    val coverAsset: CoverAsset = CoverAsset(),
    val title: String = "",
    val type: String = ""
)