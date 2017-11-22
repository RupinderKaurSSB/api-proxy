package io.descoped.client.util.test;

import io.descoped.client.util.CommonUtil;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 22/11/2017
 */
public class CommonUtilTest {

    @Test
    public void testStreams() throws Exception {
        OutputStream out = CommonUtil.newOutputStream();
        assertThat(out).toString().isEmpty();

        out.write("foo".getBytes());
        assertThat(out.toString()).isEqualTo("foo");

        InputStream in = new ByteArrayInputStream("bar".getBytes());
        out = CommonUtil.writeInputToOutputStream(in);
        assertThat(out.toString()).isEqualTo("bar");

        in = new ByteArrayInputStream("foobar".getBytes());
        CommonUtil.writeInputToOutputStream(in, out);
        assertThat(out.toString()).isEqualTo("barfoobar");

        CommonUtil.closeAndCreateNewOutputStream(out);
    }
}
