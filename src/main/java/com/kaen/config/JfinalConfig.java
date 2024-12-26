package com.kaen.config;

import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.RenderFactory;
import com.jfinal.template.Engine;
import com.kaen.config.handler.AntiLeechHandler;
import com.kaen.config.handler.GlobalHandler;
import com.kaen.config.handler.UAHandler;
import com.kaen.config.handler.XssHandler;
import com.kaen.config.interceptor.ParamPkgInterceptor;
import com.kaen.constants.DsConstans;
import com.kaen.constants.EhCacheConstants;
import com.kaen.entity._MappingKit;
import com.kaen.service.TimerService;
import com.loserstar.utils.system.LoserStarSystemUtil;

public class JfinalConfig extends JFinalConfig {
	public static final String propertiesFileNameString_test = "init-cs.properties";//加载的配置文件
	public static final String propertiesFileNameString_product = "init-cs.properties";//加载的配置文件
	
/*	private static Properties properties;//原生方式拿到的配置全局缓存
	public static Properties getProperties() {
		return properties;
	}*/
	
	@Override
	public void configConstant(Constants me) {
		//看情况是否使用判断服务器名称加载配置文件的方法
		/*		String serverName = "";
				try {
					serverName = Inet4Address.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				//jfinal的方法获取配置文件
				if (serverName.equalsIgnoreCase("c1ep1vm14.hongta.com")||serverName.equalsIgnoreCase("HTWXQYH")) {
					prop = PropKit.use(propertiesFileNameString_product);
				}else {
					prop = PropKit.use(propertiesFileNameString_test);
				}*/
		
		//手动直接指定加载哪个配置文件
		prop = PropKit.use(propertiesFileNameString_test);
		String jfinal_jdbc_url = prop.get("local.jdbcUrl");
		System.out.println("jfinal工具加载配置："+jfinal_jdbc_url);
		
		
		//原生的方式，通过class路径获取配置文件,这鬼方式在was上好像是有问题的，不用了
/*		properties = LoserStarPropertiesUtil.getProperties(JFinalConfig.class.getResource("/").getPath()+propertiesFileNameString);
		System.out.println("原生的方式获取配置：");
		LoserStarPropertiesUtil.printPropertiesInfo(properties);//打印配置信息
*/		System.out.println("系统信息");
		LoserStarSystemUtil.printSystemInfo();
		
		// log.info("configConstant 设置字符集");
		me.setEncoding("UTF-8");

		// log.info("configConstant 设置是否开发模式");
		me.setDevMode(prop.getBoolean("config.devMode"));

		// log.info("configConstant 视图error page设置");
		me.setError401View("/common/401.html");
		me.setError403View("/common/403.html");
		me.setError404View("/common/404.html");
		me.setError500View("/common/500.html");

		RenderFactory renderFactory = new RenderFactory();
		me.setRenderFactory(renderFactory);
//		me.setViewType(ViewType.FREE_MARKER);
		//设置上传文件大小200 M=209715200 b
//		me.setMaxPostSize(209715200);
		//1G = 1000000000Byte
		me.setMaxPostSize(1000000000);
		
		
		//fastjson全局配置，是否取消循环引用的检测,如果取消该配置，则代码上要注意不能循环引用，否则会造成内存溢出
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		me.setDenyAccessJsp(false);
	}

	@Override
	public void configHandler(Handlers me) {
		// log.info("configHandler 全局配置处理器，主要是记录日志和request域值处理");
		me.add(new UAHandler());
		me.add(new AntiLeechHandler());
		me.add(new GlobalHandler());
		me.add(new XssHandler());
		me.add(new UrlSkipHandler(".*/services.*",false));//用于过滤掉webservice，否则生成服务端都会报错
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// log.info("configInterceptor 权限认证拦截器");
		me.add(new ParamPkgInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("mysql.jdbcUrl"),
				PropKit.get("mysql.userName"),
				PropKit.get("mysql.passWord"));
		druidPlugin.setDriverClass(PropKit.get("mysql.driverClass"));
		druidPlugin.set(PropKit.getInt("mysql.initialSize"), PropKit.getInt("mysql.minIdle"),PropKit.getInt("mysql.maxActive"));
		me.add(druidPlugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(DsConstans.dataSourceName.myql,druidPlugin);
		me.add(arp);
		arp.setDialect(new AnsiSqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));//ture是小写，false大写
		_MappingKit.mapping(arp);
		
		//ehcache缓存
		URL url = getClass().getResource("resources/ehcache.xml"); 
		me.add(new EhCachePlugin(url));
		
		//redis缓存
//		RedisPlugin bbsRedis = new RedisPlugin(DsConstans.redisCacheName.cacheName, PropKit.get("redis.host"),PropKit.getInt("redis.port"),PropKit.getInt("redis.timeOut"),PropKit.get("redis.password"),PropKit.getInt("redis.dbIndex"));
//	    me.add(bbsRedis);
	    
	    Cron4jPlugin cp = new Cron4jPlugin();
		//每1分钟获取一次应聘须知
		String cron1 = "* * * * *";
		cp.addTask(cron1,new TimerService());
		me.add(cp);
	}

	@Override
	public void configRoute(Routes me) {
		//路由扫描注册（已作废，  jfinal 4.9.03 新增了路由扫描功能，扫描功能需要在 Controller 声明之处使用 @Path 注解）
//		new ControllerPlugin(me).start();
		me.scan(PropKit.get("config.scan.package"));
	}

	@Override
	public void configEngine(Engine me) {

	}
	

	/**
	 * 系统启动时候调用一次
	 */
	@Override
	public void onStart() {
		try {
			System.out.println("-----------系统启动-------------");
			CacheKit.put(EhCacheConstants.EhCacheSession, "onStartData", "-----------系统启动-------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}