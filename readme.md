# PushLib

### 使用方法：

1. 在项目的build.gradle中：
```groovy
   buildscript{
       repositories {
           //添加下面
           jcenter()
       }
   }
   
   allprojects {
       repositories {
           //添加下面
           jcenter()
       }
   }
```
2. 在项目中引入pushlib模块。

3. 编写用于接收pushlib发送的广播接收类并注册，自定义的BroadcastReceive需要继承PushBroadcastReceiver。

4. 调用PushLib.getInstance().init(this.getApplication());进行自动初始化

#### 至此pushlib相关配置已结束

### 进行推送的配置：（配置后需要将推送模块在app中引入）

#### 先介绍使用以及定义好的推送配置：

#### *在App的build.gradle中的id或key注意加上转义否则会出现无法转换为String的情况

- ##### 极光
 1. 极光使用的是自动配置方式只需要在app的build.gradle中添加：
```groovy
  android{
      defaultConfig{
          manifestPlaceholders = [
                  JPUSH_PKGNAME : applicationId,
                  JPUSH_APPKEY : "JPush 上注册的包名对应的 Appkey",
                  JPUSH_CHANNEL : "developer-default", //暂时填写默认值即可.
          ]
      }
  }
```

 2. 在app的build.gradle中添加：
```groovy
    <meta-data
    	android:name="pushkey_jiguang"
        android:value="JPush 上注册的包名对应的 Appkey" />
```

​    


即可。

- ##### 华为

  根据官方配置要求：

  1. 在华为开发平台上创建APP并配置密钥
  
  2. 将agconnect-services.json下载到app model下
  
  3. 在项目的build.gradle中添加：
  
```groovy
          allprojects {
              repositories {
                  maven {url 'https://developer.huawei.com/repo/'}
              }
          }
          
          buildscript{
              repositories {
                  maven {url 'https://developer.huawei.com/repo/'}
              }
          }
          
          buildscript {
              dependencies {
                  classpath 'com.huawei.agconnect:agcp:1.2.1.301'
              }
          }
```


  4. 在app的build.gradle 中添加
```groovy
     apply plugin: 'com.huawei.agconnect'
```
  5. 配置签名：将[生成签名证书指纹](https://developer.huawei.com/consumer/cn/doc/development/HMS-Guides/Preparations#generate_finger)步骤中生成的签名文件拷贝到工程的app目录下，在build.gradle文件中配置签名。
```groovy
     android {
          signingConfigs {
              config {
                  keyAlias 'xxx'
                  keyPassword '123456'
                  storeFile file('xxx.jks')
                  storePassword '123456'
              }
          }
          buildTypes {
              debug {
                  signingConfig signingConfigs.config
              }
              release {
                  signingConfig signingConfigs.config
                  minifyEnabled false
                  proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
              }
          }
      }
```

- ##### 小米
1. 引入xiaomipush模块

  2. 修改xiaomipush的AndroidManifest.xml 将包名替换

  3. 在app的build.gradle中添加：

     ```groovy
     <meta-data
     	android:name="pushid_xiaomi"
         android:value="xxxxxx" />
             
     <meta-data
     	android:name="pushkey_xiaomi"
     	android:value="xxxxxx" />
     ```


- ##### VIVO

1. 在app的build.gradle中添加：
```groovy
   <meta-data
       android:name="com.vivo.push.api_key"
       android:value="请填写Key" />
               
   <meta-data>
   	 android:name="com.vivo.push.app_id"
        android:value="请填写Appid" />        
```
- ##### OPPO

  只需要引入和配置id与key

#### 自定义方式：(暂不支持)

1. 引入sdk与完成配置后需要引入pushlib模块

2. 定义一个推送Client需要实现PushClient

3. 在推送模块的AndroidManifest.xml中添加定义好的Client的类名，需要以pushlib_开头。如下：
```xml
   <application>
           <meta-data
               android:name="pushlib_jiguang"
               android:value="com.lc9.pushlib.jiguang.JIPushClient" />
   </application>
```
即可