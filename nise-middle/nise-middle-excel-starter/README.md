# 基础介绍
> 该项目主要是让快速使用`easyexcel`进行通用的文件上传方案，封装了一些操作，具体使用方式下面详细介绍

### `yml`配置
```yaml
nise:
  excel:
    enabled: true
```
### `controller`默认提供的几个方法
- `excel/template/export` 导出模板
- `excel/data/import` 导入excel文件
- `excel/summary/query` 查询导入结果总览
- `excel/importResult/page` 查询导入的每一行结果
- `excel/importResult/export` 导入的结果导出
- `excel/importData/execute` 执行导入校验成功的数据后续操作

### 代码开始案例
- 配置常量类
```java
public class ExcelBusinessConstant {

    public static final Integer USER_TYPE = 0;

    public static final String USER_COLLECT_NAME = "user_excel";

    public static final String USER_TEMPLATE_FILE = "excel/user_import_template.xlsx";
}
```
- 导入模型
```java
@Data
public class UserExportModel {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "地址", index = 2)
    private String address;

    @ExcelProperty(value = "余额", index = 3)
    private BigDecimal balance;

    @ExcelProperty(value = "结果状态", index = 4)
    private Boolean resultFlag;

    @ExcelProperty(value = "结果描述", index = 5)
    private String resultMsg;

}
```
- 导出模型
```java
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = ExcelBusinessConstant.USER_COLLECT_NAME)
public class UserImportModel extends AbstractImportModel {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "地址", index = 2)
    private String address;

    @ExcelProperty(value = "余额", index = 3)
    private BigDecimal balance;
}
```
- 导入监听器
```java
public class UserImportExcelListener extends AbstractImportExcelListener<UserImportModel> {


    public UserImportExcelListener(Long resultId, MongoTemplate mongoTemplate, Integer businessCode, String businessCollectName, String businessTemplateUrl, String data) {
        super(resultId, mongoTemplate, businessCode, businessCollectName, businessTemplateUrl, data);
    }

    @Override
    public void executeAfterAllAnalysed(AnalysisContext analysisContext) {
        for (UserImportModel userImportModel : this.datas) {
            userImportModel.setResultFlag(true);
            userImportModel.setResultMsg("SUCCESS");
        }
    }

    @Override
    public void invoke(UserImportModel userImportModel, AnalysisContext analysisContext) {
        this.datas.add(userImportModel);
    }
}
```
- 导入处理器
```java
@Component
public class UserExcelHandler extends AbstractExcelHandler<UserImportModel, UserExportModel> {

    @Override
    public AbstractImportExcelListener getListenerInstance(Long resultId,String data) {
        return new UserImportExcelListener(resultId,mongoTemplate,this.getBusinessCode(),this.getBusinessCollectName(),this.getBusinessTemplateUrl(),data);
    }

    @Override
    public Integer getBusinessCode() {
        return ExcelBusinessConstant.USER_TYPE;
    }

    @Override
    public String getBusinessDesc() {
        return "";
    }

    @Override
    public String getBusinessCollectName() {
        return ExcelBusinessConstant.USER_COLLECT_NAME;
    }

    @Override
    public String getBusinessTemplateUrl() {
        return ExcelBusinessConstant.USER_TEMPLATE_FILE;
    }

    @Override
    public void doImportData(List<UserImportModel> importModels) {
        for (int i = 0; i < importModels.size(); i++) {
            UserImportModel userImportModel = importModels.get(i);
            if(i%2 ==0){
                userImportModel.setResultFlag(Boolean.FALSE);
                userImportModel.setResultMsg("失败");
            }else{
                userImportModel.setResultFlag(Boolean.TRUE);
                userImportModel.setResultMsg("成功");
            }
        }
    }
}
```

### 测试用例结果

