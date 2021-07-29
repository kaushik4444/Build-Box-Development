APP_OPTIM := release
NDK_TOOLCHAIN_VERSION := clang
APP_STL      := c++_static
APP_CPPFLAGS := -frtti -fexceptions -Wformat=0 -std=c++11 -DGOOGLE_PLAY_STORE -fsigned-char -Wno-nonportable-include-path
APP_LDFLAGS := -latomic -lEGL 
APP_ABI := arm64-v8a armeabi-v7a
APP_PLATFORM := android-21
LOCAL_SHORT_COMMANDS := true
APP_SHORT_COMMANDS := true
