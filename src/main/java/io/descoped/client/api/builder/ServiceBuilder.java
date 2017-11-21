package io.descoped.client.api.builder;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 07/11/2017
 */
public class ServiceBuilder {

    public static ServiceBuilder.Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public Builder() {
        }

        private ServiceTask call(ServiceTask task) {
            return new ServiceTask();
        }

        public Builder task(Class<?> clazz, Object... params) {
            return this;
        }

        public Builder build() {
            return this;
        }

        public Builder execute() {
            return this;
        }

    }

}
