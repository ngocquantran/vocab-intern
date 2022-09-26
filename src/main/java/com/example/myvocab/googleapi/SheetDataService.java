package com.example.myvocab.googleapi;

import com.example.myvocab.model.Vocab;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class SheetDataService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void renderVocabFromGoogleSheetToDatabase() throws IOException, GeneralSecurityException {
        log.info("Start to retrieve Vocab data from GoogleSheet");
        List<List<Object>> valueRanges = getDataFromGoogleSheet("vocab");
        List<Vocab> vocabs = convertGoogleSheetDataToVocab(valueRanges);
        batchInsertVocab(vocabs);
        log.info("Render Vocab data to database successfully!");
    }

    public List<List<Object>> getDataFromGoogleSheet(String sheetName) throws IOException, GeneralSecurityException {
        String SPREADSHEET_ID = "1duHWbL6m-0XjV9lW1doFZI0wI8JdyMI9YbzknyDVdd8";
        Sheets sheetsService = SheetsServiceUtil.getSheetsService();

        List<String> ranges = Arrays.asList(sheetName);
        // The default render option is ValueRenderOption.FORMATTED_VALUE.
        String valueRenderOption = "FORMATTED_VALUE"; // TODO: Update placeholder value.

        // The default dateTime render option is [DateTimeRenderOption.SERIAL_NUMBER].
        String dateTimeRenderOption = "FORMATTED_STRING"; // TODO: Update placeholder value.

        BatchGetValuesResponse readResult = sheetsService.spreadsheets().values()
                .batchGet(SPREADSHEET_ID)
                .setRanges(ranges)
                .setValueRenderOption(valueRenderOption)
                .setDateTimeRenderOption(dateTimeRenderOption)
                .execute();

        return readResult.getValueRanges().get(0).getValues();
    }

    public List<Vocab> convertGoogleSheetDataToVocab(List<List<Object>> values) {
        List<Vocab> vocabs = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Vocab vocab = Vocab.builder()
                    .id(Long.parseLong(values.get(i).get(0).toString()))
                    .audio(values.get(i).get(1).toString())
                    .enMeaning(values.get(i).get(2).toString())
                    .enSentence(values.get(i).get(3).toString())
                    .img(values.get(i).get(4).toString())
                    .phonetic(values.get(i).get(5).toString())
                    .senAudio(values.get(i).get(6).toString())
                    .type(values.get(i).get(7).toString())
                    .vnMeaning(values.get(i).get(8).toString())
                    .vnSentence(values.get(i).get(9).toString())
                    .word(values.get(i).get(10).toString())
                    .build();
            vocabs.add(vocab);
        }
        return vocabs;
    }

    public void batchInsertVocab(List<Vocab> vocabs) {
        String sql = "INSERT INTO vocab (id,audio,en_meaning,en_sentence,img,phonetic,sen_audio,type,vn_meaning,vn_sentence,word) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Vocab v = vocabs.get(i);
                ps.setLong(1, v.getId());
                ps.setString(2, v.getAudio());
                ps.setString(3, v.getEnMeaning());
                ps.setString(4, v.getEnSentence());
                ps.setString(5, v.getImg());
                ps.setString(6, v.getPhonetic());
                ps.setString(7, v.getSenAudio());
                ps.setString(8, v.getType());
                ps.setString(9, v.getVnMeaning());
                ps.setString(10, v.getVnSentence());
                ps.setString(11, v.getWord());
            }

            @Override
            public int getBatchSize() {
                return vocabs.size();
            }
        });
    }
}
