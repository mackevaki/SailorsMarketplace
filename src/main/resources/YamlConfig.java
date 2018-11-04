import com.mysql.cj.Messages;
import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.conf.ConnectionUrlParser;
import com.mysql.cj.conf.PropertyDefinitions.PropertyKey;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.InvalidConnectionAttributeException;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
public class YamlConfig {



//
//    public class Configuration {
//        private String resource = null;
//        private File file = null;
//        private URL res = null;
//
//        public Configuration(String resource) {
//            /* resource string example: /com/org/pkg/project.properties */
//            super();
//            this.resource = resource;
//            this.res = getClass().getResource(resource);
//        }
//
//        public String getProperty(String key){
//
//            Properties prop = new Properties();
//            InputStream input = null;
//            String value = null;
//
//            if (res.toString().startsWith("jar:")) {
//                input = getClass().getResourceAsStream(resource);
//            } else {
//                file = new File(res.getFile());
//                try {
//                    input = new FileInputStream(file.getPath());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            try {
//                prop.load(input);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//            value=prop.getProperty(key);
//
//            if(input !=null){
//                try{
//                    input.close();
//                } catch(IOException e){
//                    e.printStackTrace();
//                }
//            }
//            return value;
//        }
//    }
//
//
//    public static Properties getPropertiesFromConfigFiles(String configFiles) {
//        Properties configProps = new Properties();
//        for (String configFile: configFiles.split(",")) {
//            try (InputStream configAsStream = ConnectionUrl.class.getResourceAsStream("/com/mysql/cj/configurations/" + configFile + ".properties")) {
//                if (configAsStream == null) {
//                    throw ExceptionFactory.createException(InvalidConnectionAttributeException.class,
//                                                           Messages.getString("ConnectionString.10", new Object[] { configFile }));
//                }
//                configProps.load(configAsStream);
//            } catch (IOException e) {
//                throw ExceptionFactory.createException(InvalidConnectionAttributeException.class,
//                                                       Messages.getString("ConnectionString.11", new Object[] { configFile }), e);
//            }
//        }
//        return configProps;
//    }
//
//    private HostInfo buildHostInfo(String host, int port, String user, String password, boolean isDefaultPwd, Map<String, String> hostProps) {
//        // Apply properties transformations if needed.
//        if (this.propertiesTransformer != null) {
//            Properties props = new Properties();
//            props.putAll(hostProps);
//
//            props.setProperty(PropertyKey.HOST.getKeyName(), host);
//            props.setProperty(PropertyKey.PORT.getKeyName(), String.valueOf(port));
//            props.setProperty(PropertyKey.USER.getKeyName(), user);
//            props.setProperty(PropertyKey.PASSWORD.getKeyName(), password);
//
//            Properties transformedProps = this.propertiesTransformer.transformProperties(props);
//
//            host = transformedProps.getProperty(PropertyKey.PORT.getKeyName());
//            try {
//                port = Integer.parseInt(transformedProps.getProperty(PropertyKey.PORT.getKeyName()));
//            } catch (NumberFormatException e) {
//                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.8",
//                                                                                                        new Object[] { PropertyKey.PORT.getKeyName(), transformedProps.getProperty(PropertyKey.PORT.getKeyName()) }), e);
//            }
//            user = transformedProps.getProperty(PropertyKey.USER.getKeyName());
//            password = transformedProps.getProperty(PropertyKey.PASSWORD.getKeyName());
//
//            Map<String, String> transformedHostProps = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//            transformedProps.stringPropertyNames().stream().forEach(k -> transformedHostProps.put(k, transformedProps.getProperty(k)));
//            // Remove surplus keys.
//            transformedHostProps.remove(PropertyKey.HOST.getKeyName());
//            transformedHostProps.remove(PropertyKey.PORT.getKeyName());
//            transformedHostProps.remove(PropertyKey.USER.getKeyName());
//            transformedHostProps.remove(PropertyKey.PASSWORD.getKeyName());
//
//            hostProps = transformedHostProps;
//        }
//
//        return new HostInfo(this, host, port, user, password, isDefaultPwd, hostProps);
//    }
//
//    protected void collectProperties(ConnectionUrlParser connStrParser, Properties info) {
//        // Fill in the properties from the connection string.
//        connStrParser.getProperties().entrySet().stream().forEach(e -> this.properties.put(PropertyKey.normalizeCase(e.getKey()), e.getValue()));
//
//        // Properties passed in override the ones from the connection string.
//        if (info != null) {
//            info.stringPropertyNames().stream().forEach(k -> this.properties.put(PropertyKey.normalizeCase(k), info.getProperty(k)));
//        }
//
//        // Collect properties from additional sources.
//        processColdFusionAutoConfiguration();
//        setupPropertiesTransformer();
//        expandPropertiesFromConfigFiles(this.properties);
//        injectPerTypeProperties(this.properties);
//    }
}
