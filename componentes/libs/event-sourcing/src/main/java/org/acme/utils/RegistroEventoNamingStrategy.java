package org.acme.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.eclipse.microprofile.config.ConfigProvider;

public class RegistroEventoNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
        return resolve(logicalName);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier logicalName, JdbcEnvironment context) {
        return resolve(logicalName);
    }

    private Identifier resolve(Identifier logicalName) {
        if (logicalName == null) return null;
        String text = logicalName.getText();
        // Resolve ${...} placeholders
        if (text.startsWith("${") && text.endsWith("}")) {
            String key = text.substring(2, text.length() - 1);
            String value = ConfigProvider.getConfig().getValue(key, String.class);
            return new Identifier(value, logicalName.isQuoted());
        }
        return logicalName;
    }

    // Implement other methods (default to pass-through)
    @Override
    public Identifier toPhysicalCatalogName(Identifier logicalName, JdbcEnvironment context) {
        return logicalName;
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier logicalName, JdbcEnvironment context) {
        return logicalName;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment context) {
        return logicalName;
    }
}
