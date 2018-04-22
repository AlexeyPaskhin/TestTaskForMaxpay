package com.Maxpay;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class ApiTest {
    private JSONParser parser = new JSONParser();
    private HttpUriRequest request;
    private HttpResponse response;
    JSONObject result;
    String firstFilm;
    String lukeSkywalker;
    String homeWorldOfLukeSkywalker;

    @Test
    public void checkingTheTatooinePlanet() throws ParseException {
        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

            lukeSkywalker = "https://swapi.co/api/people/1/";
            request = makeRequest(lukeSkywalker);
            response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE 1: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                result = (JSONObject) parser.parse(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
                homeWorldOfLukeSkywalker = (String) result.get("homeworld");
                System.out.println(homeWorldOfLukeSkywalker);
                request = makeRequest(homeWorldOfLukeSkywalker);
                response = client.execute(request);
                System.out.println("RISK POLICY CHECK RESPONSE 2: " + response.getStatusLine().getStatusCode());
            } else throw new AssertionError("rest request 1 was failed!");

            if (response.getStatusLine().getStatusCode() == 200) {
                result = (JSONObject) parser.parse(IOUtils.toString(response.getEntity().getContent(), "utf-8"));

                assertEquals((String) result.get("name"), "Tatooine", "wrong planet1 name!");
                assertEquals((String) result.get("population"), "200000", "wrong planet1 population!");

                List<String> films = (List<String>) result.get("films");
                firstFilm = films.get(0);
                System.out.println(firstFilm);
            } else throw new AssertionError("rest request 2 was failed!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "checkingTheTatooinePlanet")
    public void checkingTheAttackOfTheClonesFilm() throws ParseException {
                    try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

                         request = makeRequest(firstFilm);
                    HttpResponse response = client.execute(request);
                    System.out.println("RISK POLICY CHECK RESPONSE 3: " + response.getStatusLine().getStatusCode());

                    if (response.getStatusLine().getStatusCode() == 200) {
                        result = (JSONObject) parser.parse(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
                        assertEquals((String) result.get("title"), "Attack of the Clones",
                                "wrong title of the film!");

                        List<String> characters = (List<String>) result.get("characters");
                        assertTrue(characters.contains(lukeSkywalker), "Luke Skywalker is absent in the film " +
                                "'Attack of the Clones'");

                        List<String> planetsOfAttackOfTheClones = (List<String>) result.get("planets");
                        assertTrue(planetsOfAttackOfTheClones.contains(homeWorldOfLukeSkywalker),
                                "Luke Skywalker's home planet is absent in the film 'Attack of the Clones'");

                    } else throw new AssertionError("rest request 3 was failed!");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    HttpUriRequest makeRequest(String Url) {
        return RequestBuilder.create("GET")
                .setUri(Url)
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }


}
