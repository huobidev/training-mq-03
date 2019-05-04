package io.github.huobidev.jiaruifeng;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(value = {
		"io.github.huobidev.jiaruifeng.service"}, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class, Component.class})}

)
public class ServiceScanConfig {
}
