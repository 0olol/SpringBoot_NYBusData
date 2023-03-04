package com.Oolol.SpringBoot_NYBusData.Helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.Oolol.SpringBoot_NYBusData.Entity.rawData;

public class CSVhelp {
        // @Autowired
    // rawData rawData;
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "recordedAtTime", "directionRef", "publishedLineName","vehicleRef" };

    public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }
    return true;
    }

    public static List<rawData> csvtoh2(InputStream is) {
        try(
          BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
          CSVParser csvParser = new CSVParser(fileReader,CSVFormat.RFC4180.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
          ){
            DateTimeFormatter dtfInput = new DateTimeFormatterBuilder()
                                            .parseCaseInsensitive()// For case-insensitive parsing
                                            .appendPattern("[d-M-uuuu[ H[:m[:s]]]]")
                                            .appendPattern("[uuuu-M-d[ H[:m[:s]]]]")
                                            .appendPattern("[uuuu/M/d[ H[:m[:s]]]]")
                                            .appendPattern("[d/M/uuuu[ H[:m[:s]]]]")
                                            .appendPattern("[d-MMM-uuuu[ H[:m[:s[.SSSSSS]]]]]")
                                            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                                            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                                            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                                            .toFormatter(Locale.ENGLISH);
            List<rawData> rawDatas = new ArrayList<rawData>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord : csvRecords){
              rawData rawData = new rawData(
                LocalDateTime.parse(csvRecord.get("recordedAtTime"), dtfInput),
                Integer.parseInt(csvRecord.get("directionRef")),
                csvRecord.get("vehicleRef"),
                csvRecord.get("publishedLineName")
                // csvRecords.get("publishedLineName"),
                );
              rawDatas.add(rawData);
          }
          return rawDatas;
        } catch (IOException e) {
          throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
      }

        public static ByteArrayInputStream writeExampleToCsv(List<rawData> examples) {
          final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL)
          .withHeader("recordedAtTime", "directionRef","publishedLineName","vehicleRef");
      
          try (ByteArrayOutputStream out = new ByteArrayOutputStream();
              CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (rawData rawData : examples) {
              List<String> data = Arrays.asList(
                String.valueOf(rawData.getRecordedAtTime()),
                String.valueOf(rawData.getDirectionRef()),
                rawData.getPublishedLineName(),
                rawData.getVehicleRef()
                  );
              csvPrinter.printRecord(data);
            }
      
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
          } catch (IOException e) {
            throw new RuntimeException("fail to export data to CSV file: " + e.getMessage());
          }
        }
        
}
