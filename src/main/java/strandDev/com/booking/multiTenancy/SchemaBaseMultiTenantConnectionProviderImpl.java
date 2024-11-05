package strandDev.com.booking.multiTenancy;

import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SchemaBaseMultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {
    private static final Logger logger = LoggerFactory.getLogger(SchemaBaseMultiTenantConnectionProviderImpl.class);
    private final DataSource  dataSource;
    public SchemaBaseMultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
     }

     @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
     }

     @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        logger.info("Getting connection for tenant {}", tenantIdentifier);
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantIdentifier);
        return connection;
     }

     @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        logger.info("Releasing connection for tenant {}", tenantIdentifier);
        String DEFAULT_TENANT = "public";
        connection.setSchema(DEFAULT_TENANT);
        releaseAnyConnection(connection);
     }
    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

}
