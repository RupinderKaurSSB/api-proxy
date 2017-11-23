package io.descoped.client.api.builder;

import java.util.List;
import java.util.Map; /**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public interface Operation<R extends Outcome> {
    String getURL();

    void requestHeaders(Map<String, List<String>> headers);

    boolean execute();

    R get();
}
