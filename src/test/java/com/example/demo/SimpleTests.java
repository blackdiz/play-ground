package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class SimpleTests {

  @Test
  void test() throws JsonPatchException, IOException {
    var json = """
              { "team": "Senior Engineer", "id": null }
            """;
    var jsonNode = new ObjectMapper().convertValue(json, JsonNode.class);
    var testDomain = new TestDomain("name", "team");
    
    var testDomainNode = new ObjectMapper().convertValue(testDomain, JsonNode.class);
    System.out.println("before testDomainNode: " + testDomainNode);
    var patch = new ObjectMapper().readValue(json, JsonMergePatch.class);
    testDomainNode = patch.apply(testDomainNode);
    System.out.println("after testDomainNode: " + testDomainNode);
  }
}
