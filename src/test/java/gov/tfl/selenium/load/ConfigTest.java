package gov.tfl.selenium.load;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by dev on 12/07/16.
 */
public class ConfigTest {
    private Gson gson;
    @Before
    public void setUp(){
        gson = new Gson();
    }
    @After
    public void tearDown(){
        gson = null;
    }
    @Test
    public void testObject(){
        String json = "{\n" +
                "  \"config\": {\n" +
                "    \"baseUrl\": \"http://localhost:8080/portal/auth/portal/opus/default\",\n" +
                "    \"load\":\"1\",\n" +
                "    \"timeLaps\":\"1\",\n" +
                "    \"rampUpTime\":\"6\",\n" +
                "    \"pageWait\":\"2\"\n" +
                "  },\n" +
                "  \"FailedCardTest\": {\n" +
                "    \"url\": \"portal\",\n" +
                "    \"startTask\": {\n" +
                "      \"name\": \"Opus Login\",\n" +
                "      \"type\": \"WebTask\",\n" +
                "      \"inputFile\":\"mobileAgentFile.txt\",\n" +
                "      \"steps\": {\n" +
                "        \"input_user\": {\n" +
                "          \"identifier\": \"j_username\",\n" +
                "          \"type\": \"id\",\n" +
                "          \"action\": \"input\"\n" +
                "        },\n" +
                "        \"input_password\": {\n" +
                "          \"identifier\": \"j_password\",\n" +
                "          \"type\": \"id\",\n" +
                "          \"action\": \"input\"\n" +
                "        },\n" +
                "        \"submit\": {\n" +
                "          \"identifier\": \"login\",\n" +
                "          \"type\": \"name\",\n" +
                "          \"action\": \"click\",\n" +
                "          \"assertData\":[\"Replace failed Oyster card\",\"Anand Pimple\"]\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"endTask\": {\n" +
                "      \"name\": \"Opus Logout\",\n" +
                "      \"type\": \"WebTask\",\n" +
                "      \"steps\": {\n" +
                "        \"logout\": {\n" +
                "          \"identifier\": \"signIn\",\n" +
                "          \"type\": \"id\",\n" +
                "          \"action\": \"click\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Map map = gson.fromJson(json,Map.class);
        Config config = gson.fromJson(gson.toJson(map.get("config")),Config.class);
        assertTrue(config.isVisibile() == false);
        System.out.println(gson.toJson(config));
    }
}