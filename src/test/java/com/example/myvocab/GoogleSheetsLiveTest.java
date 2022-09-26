package com.example.myvocab;

import com.example.myvocab.googleapi.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class GoogleSheetsLiveTest {
    private static final String SPREADSHEET_ID = "1duHWbL6m-0XjV9lW1doFZI0wI8JdyMI9YbzknyDVdd8";
    private static Sheets sheetsService;

    @BeforeAll
    public static void setup() throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
    }

    @Test
    public void whenWriteSheet_thenReadSheetOk() throws IOException {
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("Expenses January"),
                        Arrays.asList("books", "30"),
                        Arrays.asList("pens", "10"),
                        Arrays.asList("Expenses February"),
                        Arrays.asList("clothes", "20"),
                        Arrays.asList("shoes", "5")
                ));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }

    @Test
    public void batchUpdate() throws IOException {
        List<ValueRange> data = new ArrayList<>();
        data.add(new ValueRange()
                .setRange("D1")
                .setValues(Arrays.asList(
                        Arrays.asList("January Total", "=B2+B3")
                )));

        data.add(new ValueRange()
                .setRange("D4")
                .setValues(Arrays.asList(
                        Arrays.asList("February Total", "=B5+B6")
                )));

        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                .setValueInputOption("USER_ENTERED")
                .setData(data);

        BatchUpdateValuesResponse batchResult = sheetsService.spreadsheets().values()
                .batchUpdate(SPREADSHEET_ID, batchBody)
                .execute();
    }

    @Test
    public void appendData() throws IOException {
        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("Total", "=E1+E4")));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, "Sheet2!A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    @Test
    public void readValueFromSheet() throws IOException {
        List<String> ranges = Arrays.asList("E1", "E4");
        BatchGetValuesResponse readResult = sheetsService.spreadsheets().values()
                .batchGet(SPREADSHEET_ID)
                .setRanges(ranges)
                .execute();

        ValueRange januaryTotal = readResult.getValueRanges().get(0);
        assertThat(januaryTotal.getValues().get(0).get(0))
                .isEqualTo("40");

    }

    @Test
    public void readValueFromSheet3() throws IOException {
        List<String> ranges = Arrays.asList("Sheet3");
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

        System.out.println(readResult.getValueRanges().get(0));
        System.out.println(readResult.getValueRanges().get(0).getValues());
        String a=readResult.getValueRanges().get(0).getValues().get(0).get(0).toString();
        String b=readResult.getValueRanges().get(0).getValues().get(0).get(1).toString();
        assertThat(a).isEqualTo("books");
        assertThat(Integer.parseInt(b)).isEqualTo(30);


    }


}
