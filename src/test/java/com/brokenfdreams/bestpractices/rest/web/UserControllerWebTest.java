package com.brokenfdreams.bestpractices.rest.web;

import com.brokenfdreams.bestpractices.dto.UpdateUserDTO;
import com.brokenfdreams.bestpractices.rest.RestTestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/scripts/user-controller-web-data.sql"}
)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerWebTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String RESULTS_DIRECTORY_PATH = "results/web/";

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

    @Test
    public void getAllUsersTest() throws Exception {
        String url = "/web/users";
        String jsonPath = "getAllUsersTest.json";

        RestTestUtils.performRequestAndAssertResult(mockMvc,
                MockMvcRequestBuilders.get(url),
                status().isOk(),
                new TypeReference<List<UpdateUserDTO>>() {
                },
                RESULTS_DIRECTORY_PATH + jsonPath);
    }

    @ParameterizedTest
    @MethodSource("getUserByIdTestParameters")
    public void getUserByIdTest(long userId, String jsonPath) throws Exception {
        String url = "/web/users/user/" + userId;

        RestTestUtils.performRequestAndAssertResult(mockMvc,
                MockMvcRequestBuilders.get(url),
                status().isOk(),
                new TypeReference<UpdateUserDTO>() {
                },
                RESULTS_DIRECTORY_PATH + jsonPath);
    }

    @Test
    public void createUserTest() throws Exception {
        String url = "/web/users/user";
        String jsonPath = "createUserTest.json";

        RestTestUtils.performRequestAndAssertResult(mockMvc,
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(UPDATE_USER_DTO)),
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

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    public void deleteUserTest(long userId) throws Exception {
        String url = "/web/users/user/" + userId;

        RestTestUtils.performRequestAndReturnBody(mockMvc,
                MockMvcRequestBuilders.delete(url),
                status().isOk());
    }
}
