package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.WarrantyRepairResultEntity
import checkvn.com.viettiepbhdt.domain.entities.WarrantyRepairResult

object WarrantyRepairResultEntityMapper :
    EntityMapper<WarrantyRepairResultEntity, WarrantyRepairResult> {
    override fun mapToDomains(param: WarrantyRepairResultEntity): WarrantyRepairResult {
        return WarrantyRepairResult(
            Content = param.Content ?: "",
            CreateBy = param.CreateBy ?: "",
            CreateByFullName = param.CreateByFullName ?: "",
            CreateByPhone = param.CreateByPhone ?: "",
            CreateDate = param.CreateDate ?: "",
            ProductBrandName = param.ProductBrandName ?: "",
            ProductBrand_ID = param.ProductBrand_ID ?: 0,
            ProductName = param.ProductName ?: "",
            Product_ID = param.Product_ID ?: 0,
            QRCodeContent = param.QRCodeContent ?: "",
            QRCodeWarrantyRepairCategoryName = param.QRCodeWarrantyRepairCategoryName ?: "",
            QRCodeWarrantyRepairCategory_ID = param.QRCodeWarrantyRepairCategory_ID ?: 0,
            QRCodeWarrantyRepair_ID = param.QRCodeWarrantyRepair_ID ?: 0,
            SerialNumber = param.SerialNumber ?: ""
        )
    }

    override fun mapToEntities(param: WarrantyRepairResult): WarrantyRepairResultEntity {
        return WarrantyRepairResultEntity(
            Content = param.Content,
            CreateBy = param.CreateBy,
            CreateByFullName = param.CreateByFullName,
            CreateByPhone = param.CreateByPhone,
            CreateDate = param.CreateDate,
            ProductBrandName = param.ProductBrandName,
            ProductBrand_ID = param.ProductBrand_ID,
            ProductName = param.ProductName,
            Product_ID = param.Product_ID,
            QRCodeContent = param.QRCodeContent,
            QRCodeWarrantyRepairCategoryName = param.QRCodeWarrantyRepairCategoryName,
            QRCodeWarrantyRepairCategory_ID = param.QRCodeWarrantyRepairCategory_ID,
            QRCodeWarrantyRepair_ID = param.QRCodeWarrantyRepair_ID,
            SerialNumber = param.SerialNumber
        )
    }
}
