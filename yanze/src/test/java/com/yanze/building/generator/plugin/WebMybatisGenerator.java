
package com.yanze.building.generator.plugin;

import org.mybatis.generator.api.ShellRunner;

/**
 * Mybatis自动生成代码插件
 * 
 * @author sulei
 *
 */
public class WebMybatisGenerator {

	public static void main(String[] args) {
		String config = MybatisGeneratorPlugin.class.getClassLoader().getResource("generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config };
		ShellRunner.main(arg);
	}
}
