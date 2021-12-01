package com.astrosnail;

import java.net.URI;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.postgresql.ds.PGSimpleDataSource;

public class Main {

	public static void main(String[] args) throws Exception {
		
		String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        final Server server = new Server(Integer.valueOf(webPort));
        final WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part of the
        // container. Setting parent loader priority to true changes this behavior.
        // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);
        root.setWar("target/web-student-tracker-mvn.war"); 
        root.setConfigurations(new Configuration[] 
        { 
        		new AnnotationConfiguration(),
                new WebInfConfiguration(), 
                new WebXmlConfiguration(),
                new MetaInfConfiguration(), 
                new FragmentConfiguration(), 
                new EnvConfiguration(),
                new PlusConfiguration(), 
                new JettyWebXmlConfiguration() 
        });
        
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		String dbUser = dbUri.getUserInfo().split(":")[0];
		String dbPassword = dbUri.getUserInfo().split(":")[1];
		        
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(dbUri.getHost());
        ds.setPortNumber(dbUri.getPort());
        ds.setDatabaseName(dbUri.getPath().substring(1)); // Remove leading forward slash
        ds.setUser(dbUser);
        ds.setPassword(dbPassword);
         
        new Resource(root, "jdbc/web_student_tracker", ds);

        server.setHandler(root);
       	server.start();
       	server.join();
 	}
}
