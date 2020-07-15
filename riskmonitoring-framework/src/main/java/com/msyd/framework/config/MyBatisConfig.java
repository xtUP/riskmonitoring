package com.msyd.framework.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * Mybatis支持*匹配扫描包
 * 
 * @author ruoyi
 */
@Configuration
public class MyBatisConfig {
	@Autowired
	private Environment env;

	static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/**
	 * 解析别名的通配符** springboot别名不支持通配符，就是如typeAliasesPackage: com.msyd.**.domain
	 * 那么就要将这种形式转换成多个具体包名，用逗号分隔 xxx.xxx,xxx.xxx
	 * 
	 * @param typeAliasesPackage
	 * @return
	 */
	public static String setTypeAliasesPackage(String typeAliasesPackage) {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		List<String> allResult = new ArrayList<String>();
		try {
			for (String aliasesPackage : typeAliasesPackage.split(",")) {
				List<String> result = new ArrayList<String>();
				aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/"
						+ DEFAULT_RESOURCE_PATTERN;
				Resource[] resources = resolver.getResources(aliasesPackage);
				if (resources != null && resources.length > 0) {
					MetadataReader metadataReader = null;
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							metadataReader = metadataReaderFactory.getMetadataReader(resource);
							try {
								String className = metadataReader.getClassMetadata().getClassName();
								String packageName = className.substring(0, className.lastIndexOf("."));
								result.add(packageName);
								// springboot打包后riskmonitoring-admin.jar的classes包结构是
								// riskmonitoring-admin.jar/BOOT-INF/classes/
								// 那么这里用Class.forName(metadataReader.getClassMetadata().getClassName())就获取不到了报错ClassNotFound，不知道什么原因(多了一层BOOT-INF目录)
//								result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage()
//										.getName());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (result.size() > 0) {
					HashSet<String> hashResult = new HashSet<String>(result);
					allResult.addAll(hashResult);
				}
			}
			if (allResult.size() > 0) {
				typeAliasesPackage = String.join(",", (String[]) allResult.toArray(new String[0]));
			} else {
				throw new RuntimeException(
						"mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:" + typeAliasesPackage + "未找到任何包");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeAliasesPackage;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		String typeAliasesPackage = env.getProperty("mybatis.typeAliasesPackage");
		String mapperLocations = env.getProperty("mybatis.mapperLocations");
		String configLocation = env.getProperty("mybatis.configLocation");
		VFS.addImplClass(SpringBootVFS.class);
		typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);

		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
		sessionFactory.setVfs(SpringBootVFS.class);
		return sessionFactory.getObject();
	}
}