package checkvn.com.viettiepbhdt.data.model

import checkvn.com.viettiepbhdt.utils.Constants.OverviewItemProperties

data class OverviewItem(
    val title: String?,
    val count: Int,
    val type: String,
    val description: String,
    val action: String,
    val properties: OverviewItemProperties
) {
    constructor(
        count: Int,
        type: String,
        description: String,
        action: String,
        properties: OverviewItemProperties
    ) : this(
        null,
        count,
        type,
        description,
        action,
        properties
    )
}