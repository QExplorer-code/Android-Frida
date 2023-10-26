import frida
import sys

# 目标应用的包名
package_name = "myapp1"

script_code = """
Java.perform(function() {
    //联系人的类
    var contacts_uri = Java.use("android.provider.ContactsContract$CommonDataKinds$Phone").CONTENT_URI.value.toString();
    //hook的本质是重写目标函数，在目标函数调用时执行hook代码
    Java.use("android.content.ContentResolver").query.overload('android.net.Uri', '[Ljava.lang.String;', 'java.lang.String', 
    '[Ljava.lang.String;', 'java.lang.String').implementation = function (uri, str, str1, str2, str3) {
         //插入hook代码
         if(uri == contacts_uri){
            console.log('calling queryContacts');
         }
         return this.query(uri, str, str1, str2, str3);//调用原函数的实现并返回原函数的返回值
    }
});
send("hook queryContacts!");
"""
# 获取设备列表
devices = frida.get_usb_device()

# 获取目标应用的进程
process = devices.attach(package_name)

# 创建Frida脚本
script = process.create_script(script_code)

# 监听消息回调
def on_message(message, data):
    print("Message from app:", message)

script.on('message', on_message)

# 加载并运行脚本
script.load()

# 让脚本持续运行
sys.stdin.read()