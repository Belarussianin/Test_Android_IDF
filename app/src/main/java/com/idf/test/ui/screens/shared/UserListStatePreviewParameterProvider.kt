package com.idf.test.ui.screens.shared

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.idf.test.ui.model.AddressUI
import com.idf.test.ui.model.CompanyUI
import com.idf.test.ui.model.GeoUI
import com.idf.test.ui.model.UserUI
import com.idf.test.ui.screens.user_list.UserListState

class UserListStatePreviewParameterProvider : PreviewParameterProvider<UserListState> {
    override val values = sequenceOf(
        UserListState(
            users = List(10) {
                UserUI(
                    id = it,
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
                )
            },
            isLoading = false
        ),
        UserListState(
            users = emptyList(),
            isLoading = true
        )
    )
}