package dev.ropimasi.csvvalidator1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;




// Programa para validar arquivo CSV.
public class CSVValidator1 {

	// Definição dos nomes dos diretórios.
	public static final String PENDING_DIR_NAME = "PENDENTE";
	public static final String VALID_DIR_NAME = "VALIDO";
	public static final String INVALID_DIR_NAME = "INVALIDO";

	// Definição dos caminhos: na raiz do projeto.
	public static final Path PENDING_DIR_PATH = Paths.get(PENDING_DIR_NAME);
	private static final Path VALID_DIR_PATH = Paths.get(VALID_DIR_NAME);
	private static final Path INVALID_DIR_PATH = Paths.get(INVALID_DIR_NAME);

	// Definição do formato de data-hora: 31/12/2025 23:59:59
	private static final DateTimeFormatter DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");



	public static void main(String[] args) {
		
		System.out.println("Iniciando o validador de arquivos CSV...");
		
		try {
			configDirectories();

			File[] files = getArrayOfFiles();

			if (files != null && files.length > 0) {
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".csv")) {
						validateFiles(file.toPath());
					}
				}
			} else {
				System.out.println("Erro de leitura de arquivos no diretório '" + PENDING_DIR_NAME
						+ "'. Arquivos .csv não encontrado, ou não tem permissão de leitura.");
			}

		} catch (IOException e) {
			System.err.println("Erro ao configurar diretórios: " + e.getMessage());
			return;
		}
		
		System.out.println("\nProcessamento concluído.");
	}



	// Método para configurar os diretórios necessários.
	private static void configDirectories() throws IOException {
		if (!Files.exists(PENDING_DIR_PATH)) {
			System.out.println("Diretório de arquivos pendentes não existe.");
			System.out.println("Criando diretório '" + PENDING_DIR_NAME + "'...");
			Files.createDirectories(PENDING_DIR_PATH);
			System.out.println("Caminho: " + PENDING_DIR_PATH.toAbsolutePath());
			System.out.println("Encerrando o programa. Coloque os arquivos CSV no diretório '" + PENDING_DIR_NAME
					+ "' e execute novamente.");
			return;
		}

		if (!Files.exists(VALID_DIR_PATH)) {
			System.out.println("Criando diretório VALIDO...");
			Files.createDirectories(VALID_DIR_PATH);
		}

		if (!Files.exists(INVALID_DIR_PATH)) {
			System.out.println("Criando diretório INVALIDO...");
			Files.createDirectories(INVALID_DIR_PATH);
		}
	}



	// Método auxiliar para por arquivos .csv do diretório em um array de arquivos.
	protected static File[] getArrayOfFiles() {
		File folder = PENDING_DIR_PATH.toFile();
		File[] files = folder.listFiles();
		return files;
	}



	// Método para validar os arquivos CSV.
	private static void validateFiles(Path arquivo) {
		String fileName = arquivo.getFileName().toString();

		// O uso de resolve() combina o caminho da pasta com o nome do arquivo
		try (BufferedReader reader = Files.newBufferedReader(arquivo);
				BufferedWriter writerValid = Files.newBufferedWriter(VALID_DIR_PATH.resolve(fileName));
				BufferedWriter writerInvalid = Files.newBufferedWriter(INVALID_DIR_PATH.resolve(fileName))) {

			System.out.println("\nIniciando processamento do arquivo: " + fileName);
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(";", -1);

				System.out.print("Validando linha: " + line + " :");
				
				if (isValidLine(fields)) {
					writerValid.write(line);
					writerValid.newLine();
					System.out.println("Linha válida.");
				} else {
					writerInvalid.write(line);
					writerInvalid.newLine();
					System.out.println("Linha inválida.");
				}
			}
			System.out.println("Processado: " + fileName);

		} catch (IOException e) {
			System.err.println("Erro ao processar " + fileName + ": " + e.getMessage());
		}
	}



	// Método centralizador de regras de validação.
	private static boolean isValidLine(String[] fields) {
		// Regra 1: Deve ter exatamente 5 campos.
		if (fields.length != 5) {
			return false;
		}

		// Regra 2: Nenhum campo pode ser vazio ou só espaços.
		for (String field : fields) {
			if (field == null || field.isBlank()) {
				return false;
			}
		}

		// Regra 3: Validar se os campos 0, 1, 2 e 3 são Inteiros.
		for (int i = 0; i <= 3; i++) {
			if (!isInteger(fields[i])) {
				return false;
			}
		}

		// Regra 4: O campo 4 tem que ser DateTime.
		if (!isDateTime(fields[4])) {
			return false;
		}

		return true;
	}



	// Método auxiliar para testar se a String é um Integer válido.
	private static boolean isInteger(String value) {
		try {
			Integer.parseInt(value.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}



	// Método auxiliar para testar se a String é um LocalDateTime válido.
	private static boolean isDateTime(String date) {
		try {
			LocalDateTime.parse(date.trim(), DATE_HOUR_FORMAT);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

}
