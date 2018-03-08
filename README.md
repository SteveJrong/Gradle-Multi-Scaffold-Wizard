# Gradle-Multi-Scaffold-Wizard
一个用于快速构建Gradle多模块项目的Eclipse插件

## 兼容性
理论支持Eclipse/MyEclipse

## 安装
将 production 目录下的Jar包拷贝到Eclipse安装目录下的plugin文件夹内，重启Eclipse后即可在“新建”处找到。

## 使用
* 在Eclipse IDE中“新建”项目，并在对话框中找到“Gradle (MMS)”类目来创建
* 向导页面一中，依次设置根项目的存储位置和根项目名称
* 向导页面二中，需添加至少一个子项目，并为其输入子项目名称，目前可支持无限个子项目的创建
* 向导页面三中，设置软件源和构建时的编码格式信息，这将会应用到生成的项目文件中。最后生成基于Gradle的父子项目结构
