package util;

import model.Person;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.TypeDefinition;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.annotations.NamedEntityGraphDefinition;
import org.hibernate.cfg.annotations.NamedProcedureCallDefinition;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.ResultSetMappingDefinition;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.hibernate.mapping.FetchProfile;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HibernateUtil {
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
                Map<String, Object> setting = new HashMap<String, Object>();
                setting.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                setting.put(Environment.URL, "jdbc:mysql://localhost:3306/sangha?useSSL=false");
                setting.put(Environment.USER, "root");
                setting.put(Environment.PASS, "Sangha@100");
                setting.put(Environment.HBM2DDL_AUTO, "update");
                setting.put(Environment.SHOW_SQL, true);

                //Hikari setting

                // Maximum watiting time for connection from the pool

                // Maximum waiting time for a connection from the pool
                setting.put("hibernate.hikari.connectionTimeout", "20000");
                // Minimum number of ideal connections in the pool
                setting.put("hibernate.hikari.minimumIdle", "10");
                // Maximum number of actual connection in the pool
                setting.put("hibernate.hikari.maximumPoolSize", "20");
                // Maximum time that a connection is allowed to sit ideal in the pool
                setting.put("hibernate.hikari.idleTimeout", "300000");

                builder.applySettings(setting);
                standardServiceRegistry = builder.build();

                MetadataSources sources = new MetadataSources(standardServiceRegistry)
                        .addAnnotatedClass(Person.class);
                Metadata metadata = sources.getMetadataBuilder().build();


                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                if (standardServiceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
                }
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (standardServiceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
        }
    }
}

