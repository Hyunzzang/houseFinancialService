package com.hyunzzang.financial.house.common;

import com.hyunzzang.financial.house.common.exception.HomeFinancialErrorMessage;
import com.hyunzzang.financial.house.common.exception.HomeFinancialException;
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

    public static List<String[]> readAll(MultipartFile file) throws HomeFinancialException {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            return readAll(reader);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new HomeFinancialException(HomeFinancialErrorMessage.CSV_READER_ERROR);
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
                .withIgnoreQuotations(true)
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
            throw new HomeFinancialException(HomeFinancialErrorMessage.CSV_READER_ERROR);
        } finally {
            csvReader.close();
        }

        return list;
    }
}
