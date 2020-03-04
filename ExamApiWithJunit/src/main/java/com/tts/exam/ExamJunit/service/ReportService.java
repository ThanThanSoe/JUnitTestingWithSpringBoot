package com.tts.exam.ExamJunit.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.tts.exam.ExamJunit.model.Answer;
import com.tts.exam.ExamJunit.repository.AnswerRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/*
 * for jasper report
 * by TTS
 * URL : http://localhost:8080/report/html*/
@Service
public class ReportService {

	@Autowired
	private AnswerRepository answerRepository; 

	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		String path = "C:\\Users\\User\\Desktop\\report";
		List<Answer> answer = answerRepository.findAll();
		File file = ResourceUtils.getFile("classpath:answer.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(answer);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy","TTS");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\answer.html");
		}
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\answer.pdf");
		}
		
		return "report generated in path : "+ path;
	}
}
