package com.brokenfdreams.skeleton.tests.rest.api;

import com.brokenfdreams.skeleton.tests.dto.UpdateUserDTO;
import com.brokenfdreams.skeleton.tests.rest.RestTestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/scripts/schema.sql", "/scripts/user-controller-api-data.sql"}
)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerApiTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String RESULTS_DIRECTORY_PATH = "results/api/";

    private static final UpdateUserDTO UPDATE_USER_DTO = UpdateUserDTO.builder()
            .login("TEST_LOGIN")
            .firstName("FIRST_NAME")
            .lastName("LAST_NAME")
            .age(3)
            .build();

    @Autowired
    private MockMvc mockMvc;

    public static Stream<Arguments> getUserByIdTestParameters() {
        return Stream.of(
                Arguments.of(1L, "getUserByIdTestWithId1.json"),
                Arguments.of(2L, "getUserByIdTestWithId2.json"),
                Arguments.of(3L, "getUserByIdTestWithId3.json"),
                Arguments.of(4L, "getUserByIdTestWithId4.json")
        );
    }

    public static Stream<Arguments> updateUserTestParameters() {
        return Stream.of(
                Arguments.of(1L, "updateUserTestWithId1.json"),
                Arguments.of(2L, "updateUserTestWithId2.json"),
                Arguments.of(3L, "updateUserTestWithId3.json"),
                Arguments.of(4L, "updateUserTestWithId4.json")
        );
    }

    @ParameterizedTest
    @MethodSource("getUserByIdTestParameters")
    public void getUserByIdTest(long userId, String jsonPath) throws Exception {
        String url = "/api/users/user/" + userId;

        RestTestUtils.performRequestAndAssertResult(mockMvc,
                MockMvcRequestBuilders.get(url),
                status().isOk(),
                new TypeReference<UpdateUserDTO>() {
                },
                RESULTS_DIRECTORY_PATH + jsonPath);
    }

    @ParameterizedTest
    @MethodSource("updateUserTestParameters")
    public void updateUserTest(long userId, String jsonPath) throws Exception {
        String url = "/web/users/user/" + userId;

        RestTestUtils.performRequestAndAssertResult(mockMvc,
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(UPDATE_USER_DTO)),
                status().isOk(),
                new TypeReference<UpdateUserDTO>() {
                },
                RESULTS_DIRECTORY_PATH + jsonPath);
    }
}
