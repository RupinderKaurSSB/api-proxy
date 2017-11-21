package io.descoped.client.external.enhreg;

import com.github.kevinsawicki.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class BusinessEntityRegisterCentreClient {

    private static final Logger log = LoggerFactory.getLogger(BusinessEntityRegisterCentreClient.class);
    private static String BRREG_SELSKAPSINFO_URL = "http://data.brreg.no/enhetsregisteret/download/enheter";
    private String json;
    private int line;
    private String body;

    public BusinessEntityRegisterCentreClient() {
    }

    public static final List<String> FIELD_DEFS = getDatatypeList();

    private static List<String> getDatatypeList() {
        List<String> list = new ArrayList<>();
        list.add("organisasjonsnummer");
        list.add("navn");
        list.add("stiftelsesdato");
        list.add("registreringsdatoEnhetsregisteret");
        list.add("organisasjonsform");
        list.add("hjemmeside");
        list.add("registrertIFrivillighetsregisteret");
        list.add("registrertIMvaregisteret");
        list.add("registrertIForetaksregisteret");
        list.add("registrertIStiftelsesregisteret");
        list.add("frivilligRegistrertIMvaregisteret");
        list.add("antallAnsatte");
        list.add("institusjonellSektorkode.kode");
        list.add("institusjonellSektorkode.beskrivelse");
        list.add("naeringskode1.kode");
        list.add("naeringskode1.beskrivelse");
        list.add("naeringskode2.kode");
        list.add("naeringskode2.beskrivelse");
        list.add("naeringskode3.kode");
        list.add("naeringskode3.beskrivelse");
        list.add("postadresse.adresse");
        list.add("postadresse.postnummer");
        list.add("postadresse.poststed");
        list.add("postadresse.kommunenummer");
        list.add("postadresse.kommune");
        list.add("postadresse.landkode");
        list.add("postadresse.land");
        list.add("forretningsadresse.adresse");
        list.add("forretningsadresse.postnummer");
        list.add("forretningsadresse.poststed");
        list.add("forretningsadresse.kommunenummer");
        list.add("forretningsadresse.kommune");
        list.add("forretningsadresse.landkode");
        list.add("forretningsadresse.land");
        list.add("sisteInnsendteAarsregnskap");
        list.add("konkurs");
        list.add("underAvvikling");
        list.add("underTvangsavviklingEllerTvangsopplosning");
        list.add("overordnetEnhet");
        list.add("målform");
        list.add("orgform.kode");
        list.add("orgform.beskrivelse");
        return list;
    }

    private String[] splitLine(String row) {
        return row.split(";(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    private String convertToJson(String[] splitted) {
        StringBuffer buf = new StringBuffer();
        buf.append("{\n");
        List<String> fieldDefs = FIELD_DEFS;
        for (int n = 0; n < splitted.length; n++) {
            String value = splitted[n];
            String field = fieldDefs.get(n);

            buf.append("   \"").append(field).append("\": ");

            if (value.length() == 0)
                buf.append("null");
            else
                buf.append(value).append("");

            if (n < splitted.length - 1) buf.append(",");

            buf.append("\n");
        }
        buf.append("}");
        return buf.toString();
    }
    private String getOrganisationNumber(String[] splitted) {
        return splitted[0].replaceAll("\"", "");
    }


    public void gunzipIt(String inFile, String outFile) {
        byte[] buffer = new byte[1024];
        try {
            GZIPInputStream gzis;
            try (FileInputStream fis = new FileInputStream(inFile)) {
                gzis = new GZIPInputStream(fis);
                try (FileOutputStream out = new FileOutputStream(outFile)) {
                    int len;
                    while ((len = gzis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }

                    gzis.close();
                    out.close();
                }
            }
            System.out.println("GZip Done");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected String fetchEnhetsRegister() throws Exception {
        boolean FETCH_NEW_DB = false;

        if (FETCH_NEW_DB) {
            log.trace("Fetch: {}", BRREG_SELSKAPSINFO_URL);
            HttpRequest req = HttpRequest.get(BRREG_SELSKAPSINFO_URL);
            if (!req.ok()) throw new RuntimeException("Error fetching brreg selskapsdata");
            log.trace("Downloading..");
//        String payload = req.body();
            int code = req.code();

            File file = File.createTempFile("brreg-selskaper", ".tmp.gz");
            log.trace("File: {}", file.getAbsolutePath());
            req.receive(file);

            log.debug("Done download, now GUnZipping..");
            gunzipIt(file.getAbsolutePath(), "/tmp/brreg-selskaper.csv");
            log.debug("Done GUnZipping..");
        }

        json = null;
        line = 0;

        String rowLine;
        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/brreg-selskaper.csv"))) {
            while ((rowLine = br.readLine()) != null) {
                if (line == 0) {
                    line++;
                    continue;
                }
                if (line > 5) break;
                String[] splitted = splitLine(rowLine);
                json = convertToJson(splitted);
//                log.trace("{}", json);

                if (line % 100 == 0) log.trace("line: {}", line);
                line++;
            }

        } catch (IOException e) {
            log.error("line: {}, json: {}", line, json);
            throw new RuntimeException(e);
        }

        return null;
    }

}
