 package com.medsecure.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.medsecure.model.Appointment;
import com.medsecure.repository.AppointmentRepository;
import com.medsecure.service.AppointmentService;
import com.medsecure.service.EmailService;
import com.medsecure.service.SmsService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AppointmentController {
	
	@Autowired
	private SmsService smsService;
	@Autowired
	private EmailService emailService;
    @Autowired
    private AppointmentService appointmentService;
    

    @GetMapping("/addAppointment")
    public String addAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "add-appointment";
    }
    @PostMapping("/reception/saveAppointment")
    public String receptionSaveAppointment(
            @ModelAttribute Appointment appointment) {
    	appointment.setAppointmentStatus("Pending");
    	appointment.setQueueStatus("Not Checked In");
    	
        appointmentService.saveAppointment(appointment);

        return "redirect:/reception/appointments";
    }

  

    @GetMapping("/appointments")
    public String appointments(Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments());

        model.addAttribute(
                "appointmentCount",
                appointmentService.getAppointmentCount());

        model.addAttribute(
                "confirmedAppointments",
                appointmentService.getConfirmedAppointmentCount());

        model.addAttribute(
                "pendingAppointments",
                appointmentService.getPendingAppointmentCount());

        return "appointments";
    }
    @GetMapping("/reception/appointments")
    public String receptionAppointments(Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments());

        model.addAttribute(
                "appointmentCount",
                appointmentService.getAllAppointments().size());

        model.addAttribute(
                "pendingCount",
                appointmentService.getPendingCount());

        model.addAttribute(
                "completedCount",
                appointmentService.getCompletedCount());

        return "reception-appointments";
    }
    
    @GetMapping("/searchAppointments")
    public String searchAppointments(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.search(keyword));

        model.addAttribute(
                "appointmentCount",
                appointmentService.getAppointmentCount());

        model.addAttribute(
                "confirmedAppointments",
                appointmentService.getConfirmedAppointmentCount());

        model.addAttribute(
                "pendingAppointments",
                appointmentService.getPendingAppointmentCount());

        return "appointments";
    }
   
    @GetMapping("/editAppointment/{id}")
    public String editAppointment(
            @PathVariable Long id,
            Model model) {

        Appointment appointment =
                appointmentService.getAppointmentById(id);

        model.addAttribute("appointment", appointment);

        return "edit-appointment";
    }
        @GetMapping("/editAppointmentDoctor/{id}")
        public String editAppointmentDoctor(
                @PathVariable Long id,
                Model model) {

            Appointment appointment =
                    appointmentService.getAppointmentById(id);

            model.addAttribute("appointment", appointment);

            return "doctor-edit-appointment";
        
            
    } 
        @GetMapping("/reception/appointment/{id}")
        public String receptionAppointmentProfile(
                @PathVariable Long id,
                Model model) {

            Appointment appointment =
                    appointmentService.getAppointmentById(id);

            model.addAttribute(
                    "appointment",
                    appointment);

            model.addAttribute(
                    "history",
                    appointmentService.getPatientHistory(
                            appointment.getPatientName()));

            model.addAttribute(
                    "backUrl",
                    "/reception/appointments");

            return "appointment-profile";
        }
        @PostMapping("/updateAppointmentReception")
        public String updateAppointmentReception(
                @ModelAttribute Appointment appointment) {

            Appointment existing =
                    appointmentService.getAppointmentById(
                            appointment.getId());

            existing.setPatientName(
                    appointment.getPatientName());

            existing.setDoctorName(
                    appointment.getDoctorName());

            existing.setPatientCondition(
                    appointment.getPatientCondition());

            existing.setAppointmentStatus(
                    appointment.getAppointmentStatus());

            existing.setRemarks(
                    appointment.getRemarks());

            // Keep original date & time
            existing.setAppointmentDate(appointment.getAppointmentDate());
            existing.setAppointmentTime(appointment.getAppointmentTime());

            appointmentService.saveAppointment(existing);

            return "redirect:/reception/dashboard";
        }

    @PostMapping("/updateAppointmentDoctor")
    public String updateAppointmentDoctor(
            @ModelAttribute Appointment appointment) {
    	System.out.println("Date = " + appointment.getAppointmentDate());
    	System.out.println("Time = " + appointment.getAppointmentTime());

        Appointment existingAppointment =
                appointmentService.getAppointmentById(
                        appointment.getId());

        existingAppointment.setPatientName(
                appointment.getPatientName());

        existingAppointment.setDoctorName(
                appointment.getDoctorName());

        existingAppointment.setPatientCondition(
                appointment.getPatientCondition());

        existingAppointment.setAppointmentStatus(
                appointment.getAppointmentStatus());

        existingAppointment.setRemarks(
                appointment.getRemarks());

        // Keep original date & time
     // Keep original date & time
        existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(appointment.getAppointmentTime());

        appointmentService.saveAppointment(
                existingAppointment);

        return "redirect:/doctor/dashboard";
    }
    @GetMapping("/confirmAppointment/{id}")
    public String confirmAppointment(@PathVariable Long id) {

        appointmentService.confirmAppointment(id);

        return "redirect:/doctor/dashboard";
    }
    @GetMapping("/checkInPatient/{id}")
    public String checkInPatient(
            @PathVariable Long id) {

        appointmentService.checkInPatient(id);

        return "redirect:/reception/appointments";
    }
    @GetMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return "redirect:/reception/dashboard";
    }
    
    @Autowired
    private AppointmentRepository appointmentRepository;
   
    @GetMapping("/trackAppointment")
    public String trackAppointment(
            @RequestParam String appointmentId,
            @RequestParam String otp,
            Model model) {

        Appointment appointment =
                appointmentRepository
                .findByAppointmentIdAndOtp(
                        appointmentId,
                        otp);

        if (appointment == null) {
            model.addAttribute("error", "Invalid Appointment ID or OTP");
            return "track-result";
        }

        model.addAttribute("appointment", appointment);
        return "track-result";
    }
    @GetMapping("/track-appointment")
    public String trackPage() {

        return "track-appointment";
        
    }
    @GetMapping("/online-book")
    public String onlineBooking(Model model) {

        model.addAttribute(
                "appointment",
                new Appointment());

        return "online-booking";
    }
    @GetMapping("/waitingPatients")
    public String waitingPatients(Model model) {

        model.addAttribute(
                "patients",
                appointmentService.getWaitingPatients());

        return "waiting-patients";
    }
    @PostMapping("/bookAppointment")
    public String bookAppointment(
            @ModelAttribute Appointment appointment) {
    	appointment.setAppointmentStatus("Pending");
        appointment.setQueueStatus("Not Checked In");
            

        appointmentService.saveAppointment(
                appointment);
        emailService.sendAppointmentMail(
                appointment.getEmail(),
                appointment);

        return "redirect:/track-appointment";
    }
    
   
    @GetMapping("/appointment/{id}")
    public String appointmentProfile(
            @PathVariable Long id,
            Model model) {

        Appointment appointment =
                appointmentService.getAppointmentById(id);

        model.addAttribute(
                "appointment",
                appointment);

        model.addAttribute(
                "history",
                appointmentService.getPatientHistory(
                        appointment.getPatientName()));

        model.addAttribute(
                "backUrl",
                "/appointments");

        return "appointment-profile";
    }
    @PostMapping("/saveAppointment")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
    	appointment.setAppointmentStatus("Pending");
    	appointment.setQueueStatus("Not Checked In");
    	
        appointmentService.saveAppointment(appointment);

        return "redirect:/appointments";
    }
    @GetMapping("/appointment/pdf/{id}")
    public void downloadPdf(
            @PathVariable Long id,
            HttpServletResponse response) throws Exception {

        Appointment appointment =
                appointmentService.getAppointmentById(id);

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=AppointmentSlip.pdf");


        PdfWriter writer =
                new PdfWriter(response.getOutputStream());

        PdfDocument pdfDocument =
                new PdfDocument(writer);

        Document document =
                new Document(pdfDocument);

        document.add(new Paragraph("MEDISECURE HMS"));
        document.add(new Paragraph("--------------------------------"));

        document.add(new Paragraph(
                "Appointment ID : "
                + appointment.getAppointmentId()));

        document.add(new Paragraph(
                "Token Number : "
                + appointment.getTokenNumber()));

        document.add(new Paragraph(
                "Patient Name : "
                + appointment.getPatientName()));

        document.add(new Paragraph(
                "Doctor Name : "
                + appointment.getDoctorName()));

        document.add(new Paragraph(
                "Date : "
                + appointment.getAppointmentDate()));

        document.add(new Paragraph(
                "Time : "
                + appointment.getAppointmentTime()));

        document.add(new Paragraph(
                "Status : "
                + appointment.getAppointmentStatus()));

        document.add(new Paragraph(
                "Condition : "
                + appointment.getPatientCondition()));

        document.add(new Paragraph(""));
        document.add(new Paragraph(
                "Thank you for choosing MediSecure HMS"));

        document.close();

   
    }
        

	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	
    
}