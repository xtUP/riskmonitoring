package com.msyd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author risk
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RiskMonitorApplication
{
    public static void main(String[] args)
    {
    	SpringApplication.run(RiskMonitorApplication.class, args);
    	System.out.println("(♥◠‿◠)ﾉﾞ  监控启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
    			"#####  #  ####  #    # #    #  ####  #    # # #####  ####  ##### \n" +  
    			"#    # # #      #   #  ##  ## #    # ##   # #   #   #    # #    #\n" + 
    			"#    # #  ####  ####   # ## # #    # # #  # #   #   #    # #    #\n" + 
    			"#####  #      # #  #   #    # #    # #  # # #   #   #    # ##### \n" + 
    			"#   #  # #    # #   #  #    # #    # #   ## #   #   #    # #   # \n" +  
    			"#    # #  ####  #    # #    #  ####  #    # #   #    ####  #    # ");
    	
    }
}