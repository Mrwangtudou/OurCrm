package com.wang.util;

public class Constants {

	public static String PICTURE_VISIT_FILE_PATH = "";// 图片访问的路径

	public static String PICTURE_SAVE_FILE_PATH = "";// 图片存放的路径

	public static String getPICTURE_VISIT_FILE_PATH() {
		return PICTURE_VISIT_FILE_PATH;
	}

	public static void setPICTURE_VISIT_FILE_PATH(String pICTURE_VISIT_FILE_PATH) {
		PICTURE_VISIT_FILE_PATH = pICTURE_VISIT_FILE_PATH;
	}

	public static String getPICTURE_SAVE_FILE_PATH() {
		return PICTURE_SAVE_FILE_PATH;
	}

	public static void setPICTURE_SAVE_FILE_PATH(String pICTURE_SAVE_FILE_PATH) {
		PICTURE_SAVE_FILE_PATH = pICTURE_SAVE_FILE_PATH;
	}

	public static void main(String[] args) {
		Constants.setPICTURE_SAVE_FILE_PATH("G:/tomcat/apache-tomcat-8.5.35/webapps/wang/topic/");
		Constants.setPICTURE_VISIT_FILE_PATH("http://192.168.2.116:8888/wang/topic/");
	}

}
