# KGenerator

## 1.关于KGenerator
- 数据库反向生成工具，用于生成常用的Dao、DaoImpl、Entity、xml等文件；
- 基于freemarker模板生成文件，高度灵活定义文件和扩展文件；
- 可设定数据库表的范围，包括全表扫描，指定表扫描，正则扫描；
- 可灵活指定生成文件的文件名、文件路径、文件内容、必要代码等，实现项目代码规范化；
- 支持多库扫描，通过配置多个json文件，在config.json中引用需要的部分；
    
## 2.安装
- 该项目基于jdk8，引用了stream和Lambda，如想兼容之前版本jdk，需要修改代码中jdk8的部分；
- 项目引用了Lombok插件，需要从idea的市场里下载Lombok；
- 运行Main类主方法，如正常输出"Build config XXX success!"，则表示无异常；
    
## 3.简单使用
通过修改config.json和xxx.json文件可实现基本的反向生成；
- /resources/config中，通过"configs"指定需要扫描的配置文件集合，每个配置文件应对应一个数据库连接；
- /resources/config_test.json中是示例配置，其中主要结构有三方面内容:params,db,export;
    
### 3.1 params
params可自定义一组key-value，在ft文件中通过${params.key}可调用到value内容；

示例中的params.timeFormatter目前是必须定义的，否则会报错；
    
### 3.2 db
db用于指定数据库方面配置；
- driver、url、username、password用于连接数据库；
- catalog、schemaPattern、tableNamePattern、types用于指定表的扫描范围；
- tables可指定只扫描指定的表，如果为空，则全部都扫描；
    
### 3.3 export
export用于文件输出设定，只改变该文件能够实现基本的反向生成;
- export.outPath：生成文件的总目录；
- export.clearDirs：是否会清除outPath下原有文件；
- export.items：指定需要生成哪些模板的文件，每个表都会生成items下所有模板；
- export.items[n].outPath：文件所在文件夹的路径，在export.outPath后追加路径，可使用变量${tableName}；
- export.item[n].templateName:指定对应的模板名称；
- export.item[n].fileName：指定生成文件的文件名，可使用变量${tableName}；
    
## 4.templates
这是KGenerator灵活的主要原因；
- /resources/templates中指定了常用的几种模板，通过修改模板适应当前项目；
- 可再次扩展其他针对各自项目的ft文件；
- 模板中可以引用到TableParams中的变量;
- TableParams中params对应config中的params元素，其中保存用户自定义的key-value；
    
## 5.transaction包
transaction包中定义了一些常用的数据库与文件中所需内容的转换规则；

- TableNameTransaction.java 
    - 指定数据表转成tableName的规则，tableName是保存在TableParams中的变量,也是json中export.items[n]唯一可引用的变量；
- DBTypeTransaction.java
    - 数据库column类型对应的实体类中类型，保存在ColumnParams中的typeName;
- ColumnNameTransaction.java
    - 数据库column字段名对应的实体类中名字，保存在ColumnParams中的columnName；
    
## 6.注意事项
- 示例中的driver配置的是mysql8.0之后的推荐配置，8.0之前可以使用以前的mysql-java驱动；
- 目前不支持多线程，因为考虑生成工具中，时间不是主要考虑因素，所以图了省事；

## 7.待优化(我可能懒得写了，静待有缘人看到这个插件...)
- 数据库表定义别名机制(别名机制放入transaction中，config中定义是否启用别名)，在mapper.xml中使用别名，这样可以更加方便的相互引用；
- mapper.xml中列过多不换行问题，加入循环下标判断；
- 定义数据库常用字段的过滤处理机制，比如createUserId,modifyUserId,version,delFlag等，做特殊处理，放入transaction中处理；
- 关于一些表字段特殊后缀，如status，生成Enum类，transaction中定义后缀和需要生成的tf文件(不只是Enum了)；
- 数据库字段转化成实体类时候，对长度进行判断，int(4)和int(10)可转成int和long两种类型；
- 添加常用service、serviceImpl、vo、bo、qo、controller等类型tf；

## 8.我的联系方式
    微信：gutianqingyue 
    帮我指明bug，拜谢！