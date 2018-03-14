LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SHARED_LIBRARIES += liblog libnativehelper
LOCAL_PRELINK_MODULE := false

LOCAL_C_INCLUDES := $(JNI_H_INCLUDE) 
LOCAL_SRC_FILES := \
	helloworld_jni.cpp

LOCAL_MODULE := libhelloworldjni
include $(BUILD_SHARED_LIBRARY)
