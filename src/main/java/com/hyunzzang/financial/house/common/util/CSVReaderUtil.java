package com.hyunzzang.financial.house.common.util;

import com.hyunzzang.financial.house.common.exception.HouseFinancialErrorMessage;
import com.hyunzzang.financial.house.common.exception.HouseFinancialException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Slf4j
public class CSVReaderUtil {

    private CSVReaderUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String[]> readAll(MultipartFile file) throws HouseFinancialException {
        try (Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8")) {
            return readAll(reader);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new HouseFinancialException(HouseFinancialErrorMessage.CSV_READER_ERROR);
        }
    }

    /**
     *
     * @param reader : [주의] 사용하는 쪽에서 close 해야함.
     * @return
     * @throws IOException
     */
    private static List<String[]> readAll(Reader reader) throws IOException {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();

        List<String[]> list;
        try {
            list = csvReader.readAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new HouseFinancialException(HouseFinancialErrorMessage.CSV_READER_ERROR);
        } finally {
            csvReader.close();
        }

        return list;
    }
}
