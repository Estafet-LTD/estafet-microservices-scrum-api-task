package com.estafet.microservices.api.task.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class GenerateTaskAPISchema {

	public static void main(String[] args) throws IOException {
		File create = new File("create-task-api-db.ddl");
		File drop = new File("drop-task-api-db.ddl");
		create.delete();
		drop.delete();
		EntityManager em = Persistence.createEntityManagerFactory("build").createEntityManager();
		em.close();
		em.getEntityManagerFactory().close();
		appendSemicolon(create);
		appendSemicolon(drop);
	}

	private static void appendSemicolon(File ddl) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(ddl))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine + ";");
			}
			ddl.delete();
		}
		writeToFile(lines, ddl);
	}

	private static void writeToFile(List<String> lines, File ddl) throws IOException {
		for (String line : lines) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(ddl, true))) {
				bw.write(line);
				bw.newLine();
				bw.flush();
			}
		}
	}
	
}
