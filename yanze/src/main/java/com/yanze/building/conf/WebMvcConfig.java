
package com.yanze.building.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * mvc配置
 * 
 * @author sulei
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		// registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
		// registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		// registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		// registry.addResourceHandler("/files/**").addResourceLocations("classpath:/static/files/");
		// registry.addResourceHandler("/execl/**").addResourceLocations("classpath:/static/execl/");
		// registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/html/");
		// registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
		// super.addResourceHandlers(registry);
	}

}
