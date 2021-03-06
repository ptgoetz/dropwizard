package com.yammer.dropwizard.db;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.yammer.dropwizard.util.Duration;
import com.yammer.dropwizard.validation.ValidationMethod;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@SuppressWarnings("FieldMayBeFinal")
public class DatabaseConfiguration {
    public static class DatabaseConnectionConfiguration {
        @NotNull
        private String driverClass = null;
        
        @NotNull
        private String user = null;

        private String password = "";

        @NotNull
        private String url = null;

        @NotNull
        private Map<String, String> properties = Maps.newHashMap();

        @NotNull
        private Duration maxWaitForConnection = Duration.seconds(1);
        
        @NotNull
        private String validationQuery = "/* Health Check */ SELECT 1";

        @Max(1024)
        @Min(1)
        private int minSize = 1;

        @Max(1024)
        @Min(1)
        private int maxSize = 8;

        private boolean checkConnectionWhileIdle;

        @NotNull
        private Duration checkConnectionHealthWhenIdleFor = Duration.seconds(10);

        @NotNull
        private Duration closeConnectionIfIdleFor = Duration.minutes(1);

        public String getDriverClass() {
            return driverClass;
        }

        public void setDriverClass(String driverClass) {
            this.driverClass = driverClass;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, String> properties) {
            this.properties = ImmutableMap.copyOf(properties);
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Duration getMaxWaitForConnection() {
            return maxWaitForConnection;
        }

        public void setMaxWaitForConnection(Duration maxWait) {
            this.maxWaitForConnection = maxWait;
        }

        public String getValidationQuery() {
            return validationQuery;
        }

        public void setValidationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
        }

        public int getMinSize() {
            return minSize;
        }

        public void setMinSize(int minSize) {
            this.minSize = minSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public boolean checkConnectionWhileIdle() {
            return checkConnectionWhileIdle;
        }

        public void setCheckConnectionWhileIdle(boolean checkConnectionWhileIdle) {
            this.checkConnectionWhileIdle = checkConnectionWhileIdle;
        }

        public Duration getCheckConnectionHealthWhenIdleFor() {
            return checkConnectionHealthWhenIdleFor;
        }

        public void setCheckConnectionHealthWhenIdleFor(Duration timeout) {
            this.checkConnectionHealthWhenIdleFor = timeout;
        }

        public Duration getCloseConnectionIfIdleFor() {
            return closeConnectionIfIdleFor;
        }

        public void setCloseConnectionIfIdleFor(Duration timeout) {
            this.closeConnectionIfIdleFor = timeout;
        }

        @ValidationMethod(message = ".minSize must be less than or equal to maxSize")
        public boolean isPoolSizedCorrectly() {
            return minSize <= maxSize;
        }
    }

    @Valid
    private Map<String, DatabaseConnectionConfiguration> connections = Maps.newHashMap();
    
    public DatabaseConnectionConfiguration getConnection(String name) {
        return connections.get(name);
    }
    
    public void addConnection(String name, DatabaseConnectionConfiguration config) {
        connections.put(name, config);
    }
}
