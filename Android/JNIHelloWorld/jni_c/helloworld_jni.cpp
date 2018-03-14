#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/ioctl.h>

#include <utils/misc.h>
#include <utils/Log.h>

#include "jni.h"
#include "JNIHelp.h"


static jstring str2jstring(JNIEnv* env,const char* pat){
    jclass strClass = (env)->FindClass("Ljava/lang/String;");
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>",
            "([BLjava/lang/String;)V");
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    (env)->SetByteArrayRegion(bytes,0,strlen(pat), (jbyte*)pat);   
    jstring encoding = (env)->NewStringUTF("GB2312");   
    return (jstring)(env)->NewObject(strClass,ctorID, bytes, encoding);
}

static jint getHelloWorld(JNIEnv *env, jobject clazz) {    
    return 1;
}

// --------------------------------------------------------------------------
static JNINativeMethod gMethods[] = {
    {"getHelloWorld", "()I", (void*)getHelloWorld},
};

static const char *classPathName = "com/example/helloworld/jni_java/HelloWorld";
static int registerNatives(JNIEnv* env){
    jclass clazz;
    clazz = env->FindClass(classPathName);
    if(clazz == NULL){
        return JNI_FALSE;
    }
    if(env->RegisterNatives(clazz,gMethods,sizeof(gMethods)/sizeof(gMethods[0]))<0){
        return JNI_FALSE;
    }
    return JNI_TRUE;
}
jint JNI_OnLoad(JavaVM* vm,void* reserved){
    jint result = -1;
    JNIEnv* env = NULL;
    if(vm->GetEnv((void**)&env,JNI_VERSION_1_4) != JNI_OK){
        goto bail;
    }
    if(registerNatives(env) != JNI_TRUE){
        goto bail;
    }
    result = JNI_VERSION_1_4;
    bail:
    return result;
}



