package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ProductResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ProductResult

object ProductResultEntityMapper : EntityMapper<ProductResultEntity, ProductResult> {
    override fun mapToDomains(entity: ProductResultEntity): ProductResult {
        return ProductResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Image = entity.Image ?: "",
            NoItem = entity.NoItem ?: 0,
            ProductBrandName = entity.ProductBrandName ?: "",
            ProductBrand_ID = entity.ProductBrand_ID ?: 0,
            ProductName = entity.ProductName ?: "",
            Product_ID = entity.Product_ID ?: 0,
            QRCodeSecretContent = entity.QRCodeSecretContent ?: "",
            WarrantyActiveBy = entity.WarrantyActiveBy ?: "",
            WarrantyEndDate = entity.WarrantyEndDate ?: "",
            WarrantyMonth = entity.WarrantyMonth ?: 0,
            WarrantyStartDate = entity.WarrantyStartDate ?: "",
            QRCodeStatus_ID = entity.QRCodeStatus_ID ?: 0,
            QRCodeType_ID = entity.QRCodeType_ID ?: 0,
            StoreAddress = entity.StoreAddress ?: "",
            StoreName = entity.StoreName ?: "",
            UsedAddress = entity.UsedAddress ?: "",
            UsedIdentityCard = entity.UsedIdentityCard ?: "",
            UsedName = entity.UsedName ?: "",
            UsedPhone = entity.UsedPhone ?: "",
            UsedWarrantySerial = entity.UsedWarrantySerial ?: "",
            User_ID = entity.User_ID ?: "",
            WarrantyActiveByName = entity.WarrantyActiveByName ?: "",
            WarrantyStatus_ID = entity.WarrantyStatus_ID ?: 0,
            ProductContent = entity.ProductContent ?: "",
            ActiveAddress = entity.ActiveAddress ?: ""
        )
    }

    override fun mapToEntities(domain: ProductResult): ProductResultEntity {
        return ProductResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Image = domain.Image,
            NoItem = domain.NoItem,
            ProductBrandName = domain.ProductBrandName,
            ProductBrand_ID = domain.ProductBrand_ID,
            ProductName = domain.ProductName,
            Product_ID = domain.Product_ID,
            QRCodeSecretContent = domain.QRCodeSecretContent,
            WarrantyActiveBy = domain.WarrantyActiveBy,
            WarrantyEndDate = domain.WarrantyEndDate,
            WarrantyMonth = domain.WarrantyMonth,
            WarrantyStartDate = domain.WarrantyStartDate,
            QRCodeStatus_ID = domain.QRCodeStatus_ID,
            QRCodeType_ID = domain.QRCodeType_ID,
            StoreAddress = domain.StoreAddress,
            StoreName = domain.StoreName,
            UsedAddress = domain.UsedAddress,
            UsedIdentityCard = domain.UsedIdentityCard,
            UsedName = domain.UsedName,
            UsedPhone = domain.UsedPhone,
            UsedWarrantySerial = domain.UsedWarrantySerial,
            User_ID = domain.User_ID,
            WarrantyActiveByName = domain.WarrantyActiveByName,
            WarrantyStatus_ID = domain.WarrantyStatus_ID,
            ProductContent = domain.ProductContent,
            ActiveAddress = domain.ActiveAddress
        )
    }
}
