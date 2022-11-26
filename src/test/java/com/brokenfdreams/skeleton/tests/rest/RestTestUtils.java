package com.brokenfdreams.skeleton.tests.rest;

import com.brokenfdreams.skeleton.tests.dto.ErrorDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class RestTestUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String performRequestAndReturnBody(MockMvc mockMvc,
                                                     MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                                     ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn().getResponse()
                .getContentAsString();
    }

    public static void performRequestAndAssertResult(MockMvc mockMvc,
                                                     MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                                     ResultMatcher resultMatcher,
                                                     TypeReference<?> typeReference,
                                                     String jsonFilePath) throws Exception {
        String resultString = performRequestAndReturnBody(mockMvc, mockHttpServletRequestBuilder, resultMatcher);

        assertEquals(
                OBJECT_MAPPER.readValue(
                        getJsonFile(jsonFilePath),
                        typeReference),
                OBJECT_MAPPER.readValue(resultString, typeReference)
        );
    }

    public static void performRequestAndAssertResultString(MockMvc mockMvc,
                                                           MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                                           String jsonFilePath) throws Exception {
        String resultString = performRequestAndReturnBody(mockMvc, mockHttpServletRequestBuilder, status().isOk());

        assertEquals(
                OBJECT_MAPPER.readValue(
                        getJsonFile(jsonFilePath),
                        String.class),
                resultString
        );
    }

    public static void performRequestAndAssertException(MockMvc mockMvc,
                                                        MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                                        ResultMatcher resultMatcher,
                                                        String jsonFilePath) throws Exception {
        String result = performRequestAndReturnBody(mockMvc, mockHttpServletRequestBuilder, resultMatcher);

        Assertions.assertEquals(
                OBJECT_MAPPER.readValue(getJsonFile(jsonFilePath), ErrorDTO.class),
                OBJECT_MAPPER.readValue(result, ErrorDTO.class)
        );
    }

    private static File getJsonFile(String jsonFilePath) {
        return new File(Objects.requireNonNull(RestTestUtils.class.getClassLoader()
                .getResource(jsonFilePath)).getFile());
    }
}
