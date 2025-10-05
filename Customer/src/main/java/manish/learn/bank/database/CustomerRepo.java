package manish.learn.bank.database;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger logger = LoggerFactory.getLogger(CustomerRepo.class);

    public byte[] findAllCustomersFromDBasCSV() throws Exception {

        logger.info("Inside findAllCustomersFromDBasCSV method of CustomerRepo");

        // For checking csv file on local machine
        String fileName = "/Users/manishkeshav/_Work2025/Microservices2025/Customer/Customers.csv";
        Path myPath = Paths.get(fileName);
        if (!Files.exists(myPath)) {
            Files.createFile(myPath);
        } // End "For checking csv file on local machine"

        // Use JdbcTemplate to reliably get List<Map<String,Object>> instead of a projection proxy
        List<Map<String, Object>> listOfMaps = jdbcTemplate.queryForList(
                "select custemail, custcellnumber, custfirstname, custlastname, custmiddlename, custphonenumber from customer"
        );

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

        CSVWriter csvWriter = new CSVWriter(outputStreamWriter);
        CSVWriter csvWriterLocal = new CSVWriter(new FileWriter(myPath.toString())); // For checking csv file on local machine

        String[] keyHeaderArray = new String[0];
        if (!listOfMaps.isEmpty()) {
            // Column order is preserved by Spring's LinkedCaseInsensitiveMap returned by queryForList
            Map<String, Object> firstRow = listOfMaps.get(0);
            keyHeaderArray = firstRow.keySet().toArray(new String[firstRow.keySet().size()]);
        }
        csvWriter.writeNext(keyHeaderArray);
        csvWriterLocal.writeNext(keyHeaderArray); // For checking csv file on local machine


        // Write rows following the header order
        for (Map<String, Object> row : listOfMaps) {
            String[] values = new String[keyHeaderArray.length];
            for (int i = 0; i < keyHeaderArray.length; i++) {
                Object v = row.get(keyHeaderArray[i]);
                values[i] = (v == null) ? "" : v.toString();
            }
            csvWriter.writeNext(values);
            csvWriterLocal.writeNext(values); // For checking csv file on local machine
        }
        csvWriter.close();
        csvWriterLocal.close(); // For checking csv file on local machine
        return byteArrayOutputStream.toByteArray();
    }

}
