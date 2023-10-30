# Android-Frida
## 环境搭建
1、安装jdk：官网https://www.oracle.com/java/technologies/downloads/archive/，下载installer安装程序，自定义安装路径，并添加系统变量JAVA_HOME以及添加环境变量；用java --version检验是否安装成功。

2、安装android studio：参考https://blog.csdn.net/tao_789456/article/details/118093106 

注：期间需要注意虚拟机放置的位置，如果在c盘可能会因为c盘容量不足打不开。

3、创建模拟器，选择Pixel XL和Android12。确保创建的模拟器的Google APIs。
创建后启动模拟器，在终端查看设备：adb devices

注：adb命令如果报错说找不到，请添加环境变量。adb.exe应该是在androidSDK\platform-tools里。在系统变量Path里添加adb.exe的路径。

4、安装python环境：python3、frida、frida-tools。
pip install frida==16.0.0
pip install frida-tools==12.1.0
注：如果用过anaconda，可以用它进行python环境管理，这样子一些包的版本即使下载错了，重新开个环境就可以了。

5、根据frida版本和模拟器架构下载对应的frida-server程序，官网https://github.com/frida/frida/releases。下载后将frida-server推送到模拟器上：adb push frida-server-16.0.0-android-x86_64 /data/local/tmp/

6、进入adb shell对frida-server进行权限配置和启动：
adb shell
su
cd /data/local/tmp/
chmod 777 frida-server-16.0.0-android-x86_64
./ frida-server-16.0.0-android-x86_64

7、检查环境是否搭建成功：frida-ps -U

## Android测试程序
1、参照MainActivity.java。该程序中申请了查询联系人权限。
2、在AndroidManifest.xml中需要添加对应权限的声明。
3、在activity_main.xml中需要添加相应控件的声明。

## Frida监控脚本
1、参照hook1.py。

## 实验结果
1、模拟器启动Android应用。
2、终端下运行：python hook1.py
3、模拟应用运行（如点击“查询联系人”按钮）。
4、观察监控脚本的输出。
