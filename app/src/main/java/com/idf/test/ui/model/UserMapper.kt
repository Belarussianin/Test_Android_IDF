package com.idf.test.ui.model

import com.idf.test.data.model.AddressDTO
import com.idf.test.data.model.CompanyDTO
import com.idf.test.data.model.GeoDTO
import com.idf.test.data.model.UserDTO

object UserMapper {
    fun mapToUIModel(userDTO: UserDTO): UserUI {
        return with(userDTO) {
            UserUI(
                id = id,
                name = name,
                username = username,
                email = email,
                address = address.mapToUIModel(),
                phone = phone,
                website = website,
                company = company.mapToUIModel()
            )
        }
    }

    fun GeoDTO.mapToUIModel(): GeoUI {
        return GeoUI(
            lat = lat,
            lng = lng
        )
    }

    fun AddressDTO.mapToUIModel(): AddressUI {
        return AddressUI(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode,
            geo = geo.mapToUIModel()
        )
    }

    fun CompanyDTO.mapToUIModel(): CompanyUI {
        return CompanyUI(
            name = name,
            catchPhrase = catchPhrase,
            bs = bs
        )
    }
}