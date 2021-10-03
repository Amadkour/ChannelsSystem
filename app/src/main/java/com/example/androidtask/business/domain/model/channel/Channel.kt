package com.example.androidtask.business.domain.model.channel

data class Channel(
    val coverAsset: CoverAsset = CoverAsset(""),
    val iconAsset: IconAsset = IconAsset(""),
    val id: Int = 0,
    val latestMedia: List<LatestMedia> = listOf<LatestMedia>(),
    val mediaCount: Int = 0,
    val series: List<Series> = listOf<Series>(),
    val title: String = ""
)
