package strandDev.com.booking.multiTenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {
    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();
        return Objects.requireNonNullElse(tenant, "public");
    }
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}
