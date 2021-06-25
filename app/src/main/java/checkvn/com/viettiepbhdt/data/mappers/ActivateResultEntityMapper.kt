package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ActivateResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ActivateResult

object ActivateResultEntityMapper : EntityMapper<ActivateResultEntity, ActivateResult> {
    override fun mapToDomains(entity: ActivateResultEntity): ActivateResult {
        return ActivateResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Image = entity.Image ?: "",
            ProductBrandName = entity.ProductBrandName ?: "",
            ProductBrand_ID = entity.ProductBrand_ID ?: 0,
            ProductName = entity.ProductName ?: "",
            Product_ID = entity.Product_ID ?: 0,
            StoreAddress = entity.StoreAddress ?: "",
            StoreName = entity.StoreName ?: "",
            UsedAddress = entity.UsedAddress ?: "",
            UsedIdentityCard = entity.UsedIdentityCard ?: "",
            UsedName = entity.UsedName ?: "",
            UsedPhone = entity.UsedPhone ?: "",
            UsedWarrantySerial = entity.UsedWarrantySerial ?: "",
            User_ID = entity.User_ID ?: "",
            WarrantyActiveByName = entity.WarrantyActiveByName ?: "",
            WarrantyEndDate = entity.WarrantyEndDate ?: "",
            WarrantyMonth = entity.WarrantyMonth ?: 0,
            WarrantyStartDate = entity.WarrantyStartDate ?: "",
            QRCodeContent = entity.QRCodeContent ?: "",
            Serial = entity.Serial ?: ""
        )
    }

    override fun mapToEntities(domain: ActivateResult): ActivateResultEntity {
        return ActivateResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Image = domain.Image,
            ProductBrandName = domain.ProductBrandName,
            ProductBrand_ID = domain.ProductBrand_ID,
            ProductName = domain.ProductName,
            Product_ID = domain.Product_ID,
            StoreAddress = domain.StoreAddress,
            StoreName = domain.StoreName,
            UsedAddress = domain.UsedAddress,
            UsedIdentityCard = domain.UsedIdentityCard,
            UsedName = domain.UsedName,
            UsedPhone = domain.UsedPhone,
            UsedWarrantySerial = domain.UsedWarrantySerial,
            User_ID = domain.User_ID,
            WarrantyActiveByName = domain.WarrantyActiveByName,
            WarrantyEndDate = domain.WarrantyEndDate,
            WarrantyMonth = domain.WarrantyMonth,
            WarrantyStartDate = domain.WarrantyStartDate,
            QRCodeContent = domain.QRCodeContent,
            Serial = domain.Serial
        )
    }
}
