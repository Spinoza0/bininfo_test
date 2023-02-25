package com.spinoza.bininfotest.data.mapper

import com.spinoza.bininfotest.data.database.BinDbModel
import com.spinoza.bininfotest.domain.model.*
import javax.inject.Inject

class BinMapper @Inject constructor() {

    fun mapEntityToDbModel(bin: Bin) = BinDbModel(bin.value)

    private fun mapDbModelToEntity(binDbModel: BinDbModel) = Bin(binDbModel.value)

    fun mapDbModelToEntity(binDbModelList: List<BinDbModel>) = binDbModelList.map {
        mapDbModelToEntity(it)
    }

    fun mapDtoToEntity(binInfoDto: BinInfoDto): BinInfo {
        val number = BinNumber(
            binInfoDto.number?.length,
            binInfoDto.number?.luhn
        )
        val country = Country(
            binInfoDto.country?.numeric,
            binInfoDto.country?.alpha2,
            binInfoDto.country?.name,
            binInfoDto.country?.emoji,
            binInfoDto.country?.currency,
            binInfoDto.country?.latitude,
            binInfoDto.country?.longitude,
        )
        val bank = Bank(
            binInfoDto.bank?.name,
            binInfoDto.bank?.url,
            binInfoDto.bank?.phone,
            binInfoDto.bank?.city,
        )
        return BinInfo(
            number = number,
            scheme = binInfoDto.scheme,
            type = binInfoDto.type,
            brand = binInfoDto.brand,
            prepaid = binInfoDto.prepaid,
            country = country,
            bank = bank
        )
    }
}