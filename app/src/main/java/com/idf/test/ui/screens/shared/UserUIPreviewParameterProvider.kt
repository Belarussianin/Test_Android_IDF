package com.idf.test.ui.screens.shared

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.idf.test.ui.model.AddressUI
import com.idf.test.ui.model.CompanyUI
import com.idf.test.ui.model.GeoUI
import com.idf.test.ui.model.UserUI

class UserUIPreviewParameterProvider : PreviewParameterProvider<UserUI> {
    private val loremIpsumText = LoremIpsum(words = 25).values.joinToString()

    override val values = listOf(
        UserUI(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "john.mckinley@examplepetstore.biz",
            address =
            AddressUI(
                street = "Kulas Light",
                suite = "Apt. 556",
                city = "Gwenborough",
                zipcode = "9299",
                geo = GeoUI(
                    lat = "-37.3159",
                    lng = "81.1496"
                ),
            ),
            phone = "1-770-736-8031 x56442",
            website = "hildegard.org",
            company = CompanyUI(
                name = "Romaguera-Jacobson",
                catchPhrase = "Multi-layered client-server neural-net",
                bs = "harness real-time e-markets"
            )
        ),
        UserUI(
            id = 2,
            name = loremIpsumText,
            username = loremIpsumText,
            email = loremIpsumText,
            address =
            AddressUI(
                street = loremIpsumText,
                suite = loremIpsumText,
                city = loremIpsumText,
                zipcode = loremIpsumText,
                geo = GeoUI(
                    lat = loremIpsumText,
                    lng = loremIpsumText
                ),
            ),
            phone = loremIpsumText,
            website = loremIpsumText,
            company = CompanyUI(
                name = loremIpsumText,
                catchPhrase = loremIpsumText,
                bs = loremIpsumText
            )
        )
    ).asSequence()
}