/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.configuration;

/**
 *
 * @author luan
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ControllerTest {

        @Autowired
        protected WebApplicationContext webApplicationContext;

        protected MockMvc mockMvc;

        protected final MediaType MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("UTF-8"));

        @SuppressWarnings("rawtypes")
        private HttpMessageConverter mappingJackson2HttpMessageConverter;

        @Before
        public void setupMockMvc() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        @Autowired
        void setConverters(HttpMessageConverter<?>[] converters) {
                this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                        .findAny()
                        .orElse(null);
        }

        @SuppressWarnings("unchecked")
        protected String json(Object o) throws IOException {
                MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
                this.mappingJackson2HttpMessageConverter.write(
                        o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
                return mockHttpOutputMessage.getBodyAsString();
        }
}
