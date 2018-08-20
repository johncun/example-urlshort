/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example.urlshortener.jc;


import com.fasterxml.jackson.databind.ObjectMapper;
import example.urlshortener.jc.common.Constants;
import example.urlshortener.jc.jsonobj.UrlShortenedResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortUrlRepository repository;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        repository.deleteAll();
    }

    private static final String testUrl = "http://gitlab.com";
    private static final String shortenCommand = "/shorten";

    @Test
    public void shouldFailForUnsupportedVersion() throws Exception {

        mockMvc.perform(post(shortenCommand)
                .contentType("application/json")
                .content("{\"version\": " + (Constants.VERSION + 1) + ", \"url\": \"" + testUrl + "\", \"desiredLength\": 8}"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Repeat(value = 10)
    public void shouldCreateShortenedUrlAndRedirect() throws Exception {

        MvcResult result = mockMvc.perform(post(shortenCommand)
                .contentType("application/json")
                .content("{\"version\": 1, \"url\": \"" + testUrl + "\", \"desiredLength\":6}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").exists())
                .andReturn();


        String res = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        UrlShortenedResponse ures = mapper.readValue(res, UrlShortenedResponse.class);

        mockMvc.perform(get(String.format("/%s", ures.getShortUrl())))
                .andExpect(status().is(HttpStatus.MOVED_PERMANENTLY.value()));
    }

    @Test
    public void shouldCreateShortenedUrlOfCorrectSize() throws Exception {

        for (int desiredLen = 4; desiredLen < 10; desiredLen++) {

            MvcResult result = mockMvc.perform(post(shortenCommand)
                    .contentType("application/json")
                    .content("{\"version\": 1, \"url\": \"" + testUrl + "\", \"desiredLength\":" + desiredLen + "}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shortUrl").exists())
                    .andReturn();


            String res = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            UrlShortenedResponse ures = mapper.readValue(res, UrlShortenedResponse.class);

            assertTrue(ures.getShortUrl() != null && ures.getShortUrl().length() == desiredLen);
        }

    }
}