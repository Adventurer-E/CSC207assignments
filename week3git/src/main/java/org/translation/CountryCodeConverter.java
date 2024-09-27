package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private Map<String, String> alpha3ToCountry = new HashMap<String, String>();
    private Map<String, String> countryToAlpha3 = new HashMap<String, String>();

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            List<String> instanceVariables = Arrays.asList(lines.get(0).split("\t"));
            int indexAlpha3 = instanceVariables.indexOf("Alpha-3 code");
            int indexCountry = instanceVariables.indexOf("Country");
            for (int i = 1; i < lines.size(); i++) {
                List<String> line = Arrays.asList(lines.get(i).split("\t"));
                this.alpha3ToCountry.put(line.get(indexAlpha3), line.get(indexCountry));
                this.countryToAlpha3.put(line.get(indexCountry), line.get(indexAlpha3));
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        return this.alpha3ToCountry.get(code.toUpperCase());
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return this.countryToAlpha3.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return this.countryToAlpha3.size();
    }
}
