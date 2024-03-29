package com.wang.controller.system.createcode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wang.controller.base.BaseController;
import com.wang.util.DelAllFile;
import com.wang.util.FileDownload;
import com.wang.util.FileZip;
import com.wang.util.Freemarker;
import com.wang.util.PageData;
import com.wang.util.PathUtil;

/**
 * 类名称：FreemarkerController 创建人：研发中心 创建时间：2015年1月12日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/createCode")
public class CreateCodeController extends BaseController {

	/**
	 * 生成代码
	 */
	@RequestMapping(value = "/proCode")
	public void proCode(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		/* ============================================================================================= */
		String packageName = pd.getString("packageName"); // 包名 ========1
		String objectName = pd.getString("objectName"); // 类名 ========2
		String tabletop = pd.getString("tabletop"); // 表前缀 ========3
		tabletop = null == tabletop ? "" : tabletop.toUpperCase(); // 表前缀转大写
		String zindext = pd.getString("zindex"); // 属性总数
		int zindex = 0;
		if (null != zindext && !"".equals(zindext)) {
			zindex = Integer.parseInt(zindext);
		}
		List<String[]> fieldList = new ArrayList<String[]>(); // 属性集合 ========4
		for (int i = 0; i < zindex; i++) {
			fieldList.add(pd.getString("field" + i).split(",wang,")); // 属性放到集合里面
		}

		Map<String, Object> root = new HashMap<String, Object>(); // 创建数据模型
		root.put("fieldList", fieldList);
		root.put("packageName", packageName); // 包名
		root.put("objectName", objectName); // 类名
		root.put("objectNameLower", objectName.toLowerCase()); // 类名(全小写)
		root.put("objectNameUpper", objectName.toUpperCase()); // 类名(全大写)
		root.put("tabletop", tabletop); // 表前缀
		root.put("nowDate", new Date()); // 当前日期

		DelAllFile.delFolder(PathUtil.getClasspath() + "admin/ftl"); // 生成代码前,先清空之前生成的代码
		/* ============================================================================================= */

		String filePath = "admin/ftl/code/"; // 存放路径
		String ftlPath = "createCode"; // ftl路径

		/* 生成controller */
		Freemarker.printFile("controllerTemplate.ftl", root, "controller/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName + "Controller.java", filePath, ftlPath);

		/* 生成service */
		Freemarker.printFile("serviceTemplate.ftl", root, "service/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName + "Service.java", filePath, ftlPath);

		/* 生成mybatis xml */
		Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/" + packageName + "/" + objectName + "Mapper.xml", filePath, ftlPath);

		/* 生成SQL脚本 */
		Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);

		/* 生成jsp页面 */
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName.toLowerCase() + "_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName.toLowerCase() + "_edit.jsp", filePath, ftlPath);

		/* 生成的全部代码压缩成zip文件 */
		FileZip.zip(PathUtil.getClasspath() + "admin/ftl/code", PathUtil.getClasspath() + "admin/ftl/code.zip");

		/* 下载代码 */
		FileDownload.fileDownload(response, PathUtil.getClasspath() + "admin/ftl/code.zip", "code.zip");

	}

}
