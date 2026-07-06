package com.medsecure.export;


import java.io.IOException;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.medsecure.model.Patient;

import jakarta.servlet.http.HttpServletResponse;

public class PatientPdfExporter {

    private List<Patient> patients;

    public PatientPdfExporter(List<Patient> patients) {
        this.patients = patients;
    }

    public void export(HttpServletResponse response)
            throws IOException {

        PdfWriter writer =
                new PdfWriter(
                        response.getOutputStream());

        PdfDocument pdf =
                new PdfDocument(writer);

        Document document =
                new Document(pdf);

        document.add(
                new Paragraph(
                        "MediSecure Patient Report"));

        document.add(new Paragraph(" "));

        for (Patient p : patients) {

            document.add(
                    new Paragraph(
                            "ID : " + p.getId()
                            + " | Name : "
                            + p.getFirstName()
                            + " "
                            + p.getLastName()
                            + " | Gender : "
                            + p.getGender()
                            + " | Disease : "
                            + p.getDisease()
                            + " | Phone : "
                            + p.getPhone()));
        }

        document.close();
    }
}