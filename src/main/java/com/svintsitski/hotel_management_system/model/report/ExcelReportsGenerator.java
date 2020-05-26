package com.svintsitski.hotel_management_system.model.report;

import com.svintsitski.hotel_management_system.model.database.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReportsGenerator extends AbstractXlsView {

    String title;
    Row row;
    Sheet sheet;
    int rowCount = 0;
    float sum = 0;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest
            request, HttpServletResponse response) {
        @SuppressWarnings("unchecked")
        String reportType = (String) model.get("report_type");
        @SuppressWarnings("unchecked")
        Reservation reservations = (Reservation) model.get("applications");
        @SuppressWarnings("unchecked")
        List<Accommodation> accommodations = (List<Accommodation>) model.get("accommodations");
        @SuppressWarnings("unchecked")
        List<Customer> customers = (List<Customer>) model.get("customers");
        @SuppressWarnings("unchecked")
        List<Apartment> apartments = (List<Apartment>) model.get("apartments");
        @SuppressWarnings("unchecked")
        List<ApartmentType> apartmentTypes = (List<ApartmentType>) model.get("apartmentTypes");

        if (reportType.equals("stay_report")) {
            response.setHeader("Content-Disposition", "attachment; filename=\"Hotel Stay Report ("
                    + reservations.getArrival_date() + " - " + reservations.getDeparture_date() + ").xls\"");
            title = "Отчет о проживании в гостинице за " + reservations.getArrival_date()
                    + " - " + reservations.getDeparture_date();
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"Foreign Stay Report ("
                    + reservations.getArrival_date() + " - " + reservations.getDeparture_date() + ").xls\"");
            title = "Отчет об иностранных клиентах за " + reservations.getArrival_date()
                    + " - " + reservations.getDeparture_date();
        }

        sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(30);

        CellStyle centeredBoldOnGray = setCellStyle2(workbook, HSSFColor.HSSFColorPredefined.BLACK.getIndex(), HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex(), true, HorizontalAlignment.CENTER);
        CellStyle CenteredNormalOnWhite = setCellStyle2(workbook, HSSFColor.HSSFColorPredefined.BLACK.getIndex(), HSSFColor.HSSFColorPredefined.LEMON_CHIFFON.getIndex(), false, HorizontalAlignment.CENTER);

        createCell(new String[]{title, " ", " "}, centeredBoldOnGray);
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 0, 2));

        createCell(new String[]{" ", " ", " "}, CenteredNormalOnWhite);

        if (reportType.equals("stay_report")) {
            createCell(new String[]{"Дата", "Апартамент", "Выручка (BYN)"}, centeredBoldOnGray);
        } else {
            createCell(new String[]{"Дата", "Страна", "Выручка (BYN)"}, centeredBoldOnGray);
        }

        if (reportType.equals("stay_report")) {

            for (Date date = reservations.getArrival_date();
                 !date.equals(reservations.getDeparture_date());
                 date = addDays(date, 1)) {

                boolean flag = false;
                float amountPerDay = 0;

                for (int i = 0; i < accommodations.size(); i++) {
                    if ((accommodations.get(i).getArrival_date().before(date) ||
                            accommodations.get(i).getArrival_date().equals(date)) &&
                            accommodations.get(i).getDeparture_date().after(date)) {

                        float price = apartmentTypes.get(i).getPrice();
                        amountPerDay += price;
                        createCell(new String[]{date.toString(), String.valueOf(apartments.get(i).getNumber()),
                                String.valueOf(price)}, CenteredNormalOnWhite);

                        flag = true;
                    }
                }
                if (flag) {
                    sum += Math.round(amountPerDay * 100) / 100.0f;
                    createCell(new String[]{"Итого за сутки", " ", String.valueOf(Math.round(amountPerDay * 100) / 100.0f)}, centeredBoldOnGray);
                }
            }

            createCell(new String[]{" ", " ", " "}, CenteredNormalOnWhite);
            createCell(new String[]{"Выручка за " + reservations.getArrival_date() + " - " +
                    reservations.getDeparture_date(), " ", String.valueOf(Math.round(sum * 100) / 100.0f)}, centeredBoldOnGray);
            sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 0, 1));

        } else {
            Map<String, Float> revenue = new HashMap<>();
            @SuppressWarnings("unchecked")
            List<ForeignCustomer> foreignCustomers = (List<ForeignCustomer>) model.get("foreignCustomers");

            float fullSum = 0;

            for (Date date = reservations.getArrival_date();
                 !date.equals(reservations.getDeparture_date());
                 date = addDays(date, 1)) {

                Map<String, Float> dailyRevenue = new HashMap<>();

                for (int i = 0; i < accommodations.size(); i++) {
                    if ((accommodations.get(i).getArrival_date().before(date) ||
                            accommodations.get(i).getArrival_date().equals(date)) &&
                            accommodations.get(i).getDeparture_date().after(date)) {
                        if (foreignCustomers.get(i) != null) {
                            if (foreignCustomers.get(i).getCitizenship() != null) {
                                float price = apartmentTypes.get(i).getPrice();

                                Float a = dailyRevenue.get(foreignCustomers.get(i).getCitizenship().toLowerCase());

                                if (a != null) {
                                    float sum = dailyRevenue.get(foreignCustomers.get(i).getCitizenship().toLowerCase()) + price;
                                    dailyRevenue.put(foreignCustomers.get(i).getCitizenship().toLowerCase(), Math.round(sum * 100) / 100.0f);
                                } else {
                                    dailyRevenue.put(foreignCustomers.get(i).getCitizenship().toLowerCase(), price);
                                }
                            }
                        }
                    }
                }

                if (dailyRevenue.size() != 0) {
                    float amountPerDay = 0;
                    for (Map.Entry<String, Float> entry : dailyRevenue.entrySet()) {
                        createCell(new String[]{date.toString(), firstUpperCase(entry.getKey()),
                                String.valueOf(entry.getValue())}, CenteredNormalOnWhite);
                        amountPerDay += entry.getValue();
                    }
                    createCell(new String[]{"Итого за сутки", " ", String.valueOf(Math.round(amountPerDay * 100) / 100.0f)}, centeredBoldOnGray);
                }

                for (Map.Entry<String, Float> entry : dailyRevenue.entrySet()) {
                    if (revenue.containsKey(entry.getKey())) {
                        float sum = entry.getValue() + revenue.get(entry.getKey());
                        revenue.put(entry.getKey().toLowerCase(), sum);
                    } else {
                        revenue.put(entry.getKey().toLowerCase(), entry.getValue());
                    }
                }
            }
            for (Map.Entry<String, Float> entry : revenue.entrySet()) {
                fullSum += entry.getValue();
            }
            createCell(new String[]{" ", " ", " "}, CenteredNormalOnWhite);
            createCell(new String[]{"Выручка за " + reservations.getArrival_date() + " - " +
                    reservations.getDeparture_date(), " ", String.valueOf(Math.round(fullSum * 100) / 100.0f)}, centeredBoldOnGray);
            sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 0, 1));

            boolean flag = true;

            for (Map.Entry<String, Float> entry : revenue.entrySet()) {
                if (flag) {
                    createCell(new String[]{"В том числе", firstUpperCase(entry.getKey()),
                            String.valueOf(Math.round(entry.getValue() * 100) / 100.0f)}, CenteredNormalOnWhite);
                    flag = false;
                } else {
                    createCell(new String[]{"", firstUpperCase(entry.getKey()),
                            String.valueOf(Math.round(entry.getValue() * 100) / 100.0f)}, CenteredNormalOnWhite);
                }
            }
        }
    }

    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

    public String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private void createCell(String[] texts, CellStyle cellStyle) {
        row = sheet.createRow(rowCount++);
        for (int i = 0; i < texts.length; i++) {
            row.createCell(i).setCellValue(texts[i]);
            row.getCell(i).setCellStyle(cellStyle);
        }
    }

    private CellStyle setCellStyle2(Workbook workbook, short color, short foregroundColor, Boolean bold, HorizontalAlignment alignment) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Book Antiqua");
        font.setFontHeightInPoints((short) 12);
        style.setFillForegroundColor(foregroundColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (alignment != null) {
            style.setAlignment(alignment);
        }
        font.setBold(bold);
        font.setColor(color);
        style.setFont(font);
        return style;
    }
}
