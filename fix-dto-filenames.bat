@echo off
echo 修复DTO文件名大小写问题...

cd src\main\java\com\yuehe\app\dto

ren "ProfileDetailDto.java" "ProfileDetailDTO.java"
ren "ShopDetailDto.java" "ShopDetailDTO.java"
ren "YueHeAllShopsDetailDto.java" "YueHeAllShopsDetailDTO.java"
ren "OperationDetailDto.java" "OperationDetailDTO.java"
ren "OperationOperatorToolForDBDto.java" "OperationOperatorToolForDBDTO.java"
ren "SaleClientItemSellerDto.java" "SaleClientItemSellerDTO.java"
ren "SaleDetailForDBDto.java" "SaleDetailForDBDTO.java"
ren "SaleDetailDto.java" "SaleDetailDTO.java"

echo 文件名修复完成！
