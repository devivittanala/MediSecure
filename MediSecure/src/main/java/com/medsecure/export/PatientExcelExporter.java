package com.medsecure.export;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.medsecure.model.Patient;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class PatientExcelExporter {

    private List<Patient> patients;

    public PatientExcelExporter(List<Patient> patients) {
        this.patients = patients;
    }

    public void export(HttpServletResponse response)
            throws IOException {

        XSSFWorkbook workbook =
                new XSSFWorkbook();

        XSSFSheet sheet =
                workbook.createSheet("Patients");

        Row header =
                sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Gender");
        header.createCell(3).setCellValue("Age");
        header.createCell(4).setCellValue("Disease");
        header.createCell(5).setCellValue("Phone");

        int rowCount = 1;

        for (Patient p : patients) {

            Row row =
                    sheet.createRow(rowCount++);

            row.createCell(0)
                    .setCellValue(p.getId());

            row.createCell(1)
                    .setCellValue(
                            p.getFirstName()
                            + " "
                            + p.getLastName());

            row.createCell(2)
                    .setCellValue(p.getGender());

            row.createCell(3)
                    .setCellValue(p.getAge());

            row.createCell(4)
                    .setCellValue(p.getDisease());

            row.createCell(5)
                    .setCellValue(p.getPhone());
        }

        ServletOutputStream output =
                response.getOutputStream();

        workbook.write(output);

        workbook.close();

        output.close();
    }
}