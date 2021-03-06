# Gradle-Multi-Scaffold-Wizard
一个用于快速构建Gradle多模块项目的Eclipse插件

## 安装
将 production 目录下的Jar包拷贝到Eclipse安装目录下的plugin文件夹内，重启Eclipse后即可在“新建”处找到。

## 使用
* 在Eclipse IDE中“新建”项目，并在对话框中找到“Gradle (MMS)”类目来创建
* 向导页面一中，依次设置根项目的存储位置和根项目名称，然后继续
* 向导页面二中，需添加至少一个子项目，并为其输入子项目名称，然后继续（目前可支持无限个子项目的创建）
* 向导页面三中，设置软件源和构建时的编码格式信息，（此配置将会应用到生成的项目文件中），点击“完成”即可创建在对应目录下，以父子目录结构呈现

## 兼容性
支持 Eclipse/MyEclipse IDE

在以下操作系统平台和Eclipse版本中测试通过：
* Eclipse Version: Mars.2 Release (4.5.2) based on macOS High Sierra 10.13.3
* Eclipse Version: Oxygen.3 Release (4.7.3) based on macOS High Sierra 10.13.3

## 未来将实现
* 根据主项目名快速生成常用的子项目，同时支持自定义子项目名
* 生成之后立即导入到Eclipse工作空间中

## 更新日志（由新到旧）
### 2018/3/31
版本号： 1.0.0.201803311356
主项目的settings.gradle配置文件支持自动拼接生成include命令代码将子项目包含进来。

### 2018/3/9
版本号： 1.0.0.201803091308
代码结构优化。

### 2018/3/8
版本号： 1.0.0.201803090113
第一版发布。