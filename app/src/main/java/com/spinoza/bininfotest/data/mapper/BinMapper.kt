package com.spinoza.bininfotest.data.mapper

import com.spinoza.bininfotest.data.database.BinDbModel
import com.spinoza.bininfotest.domain.model.Bin

class BinMapper {
    fun mapEntityToDbModel(bin: Bin) = BinDbModel(bin.value)
    fun mapDbModelToEntity(binDbModel: BinDbModel) = Bin(binDbModel.value)
}