
package com.yanze.building.generator.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.springframework.stereotype.Repository;

/**
 * Mybatis自动生成代码插件
 * 
 * @author sulei
 *
 */
public class MybatisGeneratorPlugin extends PluginAdapter {

	private static String XMLFILE_POSTFIX = "Ext";

	private static String JAVAFILE_POTFIX = "Ext";

	private static String ANNOTATION_REPOSITORY = Repository.class.getName();

	/**
	 * 在XXExample对象里添加StringFiled对象属性
	 * 
	 * @param topLevelClass
	 * @param introspectedTable
	 * @param name
	 */
	public void addStringField(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.lang.String"));
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType("java.lang.String"));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String"), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	// 添删改Document的sql语句及属性
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

		XmlElement parentElement = document.getRootElement();

		updateDocumentNameSpace(introspectedTable, parentElement);

		moveDocumentInsertSql(parentElement);

		updateDocumentInsertSelective(parentElement);

		moveDocumentUpdateByPrimaryKeySql(parentElement);

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private void moveDocumentUpdateByPrimaryKeySql(XmlElement parentElement) {
		XmlElement updateElement = null;
		for (Element element : parentElement.getElements()) {
			XmlElement xmlElement = (XmlElement) element;
			if (xmlElement.getName().equals("update")) {
				for (Attribute attribute : xmlElement.getAttributes()) {
					if (attribute.getValue().equals("updateByPrimaryKey")) {
						updateElement = xmlElement;
						break;
					}
				}
			}
		}
		parentElement.getElements().remove(updateElement);
	}

	private void updateDocumentInsertSelective(XmlElement parentElement) {
		XmlElement oldElement = null;
		XmlElement newElement = null;
		for (Element element : parentElement.getElements()) {
			XmlElement xmlElement = (XmlElement) element;
			if (xmlElement.getName().equals("insert")) {
				for (Attribute attribute : xmlElement.getAttributes()) {
					if (attribute.getValue().equals("insertSelective")) {
						oldElement = xmlElement;
						newElement = xmlElement;
						newElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
						newElement.addAttribute(new Attribute("keyProperty", "id"));
						break;
					}
				}
			}
		}
		parentElement.getElements().remove(oldElement);
		parentElement.getElements().add(newElement);
	}

	private void moveDocumentInsertSql(XmlElement parentElement) {
		XmlElement insertElement = null;
		for (Element element : parentElement.getElements()) {
			XmlElement xmlElement = (XmlElement) element;
			if (xmlElement.getName().equals("insert")) {
				for (Attribute attribute : xmlElement.getAttributes()) {
					if (attribute.getValue().equals("insert")) {
						insertElement = xmlElement;
						break;
					}
				}
			}
		}
		parentElement.getElements().remove(insertElement);
	}

	private void updateDocumentNameSpace(IntrospectedTable introspectedTable, XmlElement parentElement) {
		Attribute namespaceAttribute = null;
		for (Attribute attribute : parentElement.getAttributes()) {
			if (attribute.getName().equals("namespace")) {
				namespaceAttribute = attribute;
			}
		}
		parentElement.getAttributes().remove(namespaceAttribute);
		parentElement.getAttributes().add(new Attribute("namespace", introspectedTable.getMyBatis3JavaMapperType()
		                                        + JAVAFILE_POTFIX));
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		List<Method> methods = interfaze.getMethods();
		Method insertMethod = null;
		for (Method method : methods) {
			if (method.getName().equals("insert")) {
				insertMethod = method;
				break;
			}
		}
		methods.remove(insertMethod);

		Method updateMethod = null;
		for (Method method : methods) {
			if (method.getName().equals("updateByPrimaryKey")) {
				updateMethod = method;
				break;
			}
		}
		methods.remove(updateMethod);

		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	// 生成XXExt.xml
	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
		String xmlName = introspectedTable.getMyBatis3XmlMapperFileName();

		// 删除*Mapper.xml文件
		deleteFile(context.getSqlMapGeneratorConfiguration().getTargetProject(), introspectedTable.getMyBatis3XmlMapperPackage(), xmlName);

		String[] splitFile = xmlName.split("\\.");
		String fileNameExt = null;
		if (splitFile[0] != null) {
			fileNameExt = splitFile[0] + XMLFILE_POSTFIX + ".xml";
		}

		if (isExistExtFile(context.getSqlMapGeneratorConfiguration().getTargetProject(), introspectedTable.getMyBatis3XmlMapperPackage(), fileNameExt)) {
			return super.contextGenerateAdditionalXmlFiles(introspectedTable);
		}

		Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);

		XmlElement root = new XmlElement("mapper");
		document.setRootElement(root);
		String namespace = introspectedTable.getMyBatis3SqlMapNamespace() + XMLFILE_POSTFIX;
		root.addAttribute(new Attribute("namespace", namespace));

		GeneratedXmlFile gxf = new GeneratedXmlFile(document, fileNameExt, introspectedTable.getMyBatis3XmlMapperPackage(), context.getSqlMapGeneratorConfiguration().getTargetProject(), false, context.getXmlFormatter());

		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>(1);
		answer.add(gxf);

		return answer;
	}

	// 生成XXExt.java
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		String javaName = introspectedTable.getMyBatis3JavaMapperType();
		String[] splitFile = javaName.split("\\.");

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(javaName + JAVAFILE_POTFIX);
		Interface interfaze = new Interface(type);
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		context.getCommentGenerator().addJavaFileComment(interfaze);

		FullyQualifiedJavaType baseInterfaze = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		interfaze.addSuperInterface(baseInterfaze);

		FullyQualifiedJavaType annotation = new FullyQualifiedJavaType(ANNOTATION_REPOSITORY);
		interfaze.addAnnotation("@" + Repository.class.getSimpleName());
		interfaze.addImportedType(annotation);

		CompilationUnit compilationUnits = interfaze;
		GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(compilationUnits, context.getJavaModelGeneratorConfiguration().getTargetProject(), context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());

		// 删除*Mapper.java文件
		deleteFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage(), splitFile[splitFile.length
		                                        - 1] + ".java");

		// 删除model文件
		deleteFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage().replace("mapper", "model"), splitFile[splitFile.length
		                                        - 1].replace("Mapper", ".java"));
		deleteFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage().replace("mapper", "model"), splitFile[splitFile.length
		                                        - 1].replace("Mapper", "Example.java"));
		deleteFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage().replace("mapper", "model"), splitFile[splitFile.length
		                                        - 1].replace("Mapper", "Key.java"));
		deleteFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage().replace("mapper", "model"), splitFile[splitFile.length
		                                        - 1].replace("Mapper", "WithBLOBs.java"));

		if (isExistExtFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage(), generatedJavaFile.getFileName())) {
			return super.contextGenerateAdditionalJavaFiles(introspectedTable);
		}
		List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<GeneratedJavaFile>(1);
		generatedJavaFile.getFileName();
		generatedJavaFiles.add(generatedJavaFile);
		return generatedJavaFiles;
	}

	private boolean isExistExtFile(String targetProject, String targetPackage, String fileName) {

		File project = new File(targetProject);
		if (!project.isDirectory()) {
			return true;
		}

		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(targetPackage, ".");
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken());
			sb.append(File.separatorChar);
		}

		File directory = new File(project, sb.toString());
		if (!directory.isDirectory()) {
			boolean rc = directory.mkdirs();
			if (!rc) {
				return true;
			}
		}

		File testFile = new File(directory, fileName);
		if (testFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	private void deleteFile(String targetProject, String targetPackage, String fileName) {
		File project = new File(targetProject);
		if (!project.isDirectory()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(targetPackage, ".");
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken());
			sb.append(File.separatorChar);
		}

		File directory = new File(project, sb.toString());
		if (!directory.isDirectory()) {
			boolean rc = directory.mkdirs();
			if (!rc) {
				return;
			}
		}

		File testFile = new File(directory, fileName);
		if (testFile.exists()) {
			System.out.println("Remove existing file: " + fileName);
			testFile.delete();
		}
	}

	/**
	 * This plugin is always valid - no properties are required
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}

	public static void main(String[] args) {
		String config = MybatisGeneratorPlugin.class.getClassLoader().getResource("generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config };
		ShellRunner.main(arg);
	}
}
