

package com.secrethq.utils;

public class PTJniHelper {
	public static String password() {
		return "Hi/9Ic+r4FgcKKpwzfq0Whl5qnLNrr0MHX3xIc6v5AgfLa5yyfrjWxx7rCXJ+uMMS3j7IMiv5ggbL610lKy2Cg==";
	}
	public static native boolean isAdNetworkActive( String name ); 
    public static native String jsSettingsString();
    
    public static native void setSettingsValue(String path, String value);
    public static native String getSettingsValue(String path);
}
