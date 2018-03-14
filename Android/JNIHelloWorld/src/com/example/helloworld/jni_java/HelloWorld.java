package com.example.helloworld.jni_java;

public class HelloWorld {
	static{  
		System.loadLibrary("helloworldjni");  
	}	
	public static native int getHelloWorld();

}
