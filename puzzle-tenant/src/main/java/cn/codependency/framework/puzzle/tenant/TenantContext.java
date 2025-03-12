package cn.codependency.framework.puzzle.tenant;

import java.util.Optional;

public class TenantContext {

    private static final ThreadLocal<Tenant> TENANT_CTX = new ThreadLocal<>();

    public static Tenant getTenant() {
        return TENANT_CTX.get();
    }

    public static Long getTenantId() {
        return Optional.ofNullable(getTenant()).map(Tenant::getId).orElse(null);
    }

    public static void setTenant(Tenant tenant) {
        TENANT_CTX.set(tenant);
    }

    public static void clearTenant() {
        TENANT_CTX.remove();
    }
}
