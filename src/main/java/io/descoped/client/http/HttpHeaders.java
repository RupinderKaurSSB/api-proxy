package io.descoped.client.http;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

public interface HttpHeaders {

    Optional<String> firstValue(String name);

    OptionalLong firstValueLong(String name);

    List<String> allValues(String name);

    Map<String,List<String>> map();

}
