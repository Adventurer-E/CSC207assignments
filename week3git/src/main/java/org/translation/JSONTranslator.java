package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    public static final String ALPHA3 = "alpha3";
    private final JSONArray jsonArray;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            this.jsonArray = new JSONArray(jsonString);

            // Note: this will likely be one of the most substantial pieces of code you write in this lab.

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> languages = new ArrayList<>();
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject jsonObject = this.jsonArray.getJSONObject(i);
            if (jsonObject.getString(ALPHA3).equals(country)) {
                jsonObject.keys().forEachRemaining(key -> {
                    if (!(key.equals("id") || key.equals("alpha2") || key.equals(ALPHA3))) {
                        languages.add(key);
                    }
                });
                break;
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject jsonObject = this.jsonArray.getJSONObject(i);
            countries.add(jsonObject.getString(ALPHA3));
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject jsonObject = this.jsonArray.getJSONObject(i);
            if (jsonObject.getString(ALPHA3).equals(country)) {
                try {
                    return jsonObject.getString(language);
                }
                catch (JSONException ex) {
                    break;
                }
            }
        }
        return "Country not found";
    }
}
