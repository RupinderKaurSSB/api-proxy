package io.descoped.client.api.test;


import io.descoped.client.api.builder.ServiceBuilder;
import io.descoped.client.external.facebook.FacebookClient;
import org.junit.Test;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 07/11/2017
 */
public class ServiceTaskTest {

    @Test
    public void name() throws Exception {
        ServiceBuilder.builder()
                .task(FacebookClient.class)
                .execute()
                .build();

    }
}
