package dev.ropimasi.validadorcsv1;

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
public class ValidadorCSV1 {

	// Definição dos caminhos: na raiz do projeto.
	public static final Path PENDENTE_DIR = Paths.get("PENDENTE");
	private static final Path VALIDO_DIR = Paths.get("VALIDO");
	private static final Path INVALIDO_DIR = Paths.get("INVALIDO");
	// Definição do formato de data-hora: 31/12/2025 23:59:59
	private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");



	public static void main(String[] args) {
		try {
			configurarDiretorios();

			// Por arquivos .csv do diretório em um array de arquivos.
			File pasta = PENDENTE_DIR.toFile();
			File[] arquivos = pasta.listFiles();

			if (arquivos != null) {
				for (File arquivo : arquivos) {
					if (arquivo.isFile() && arquivo.getName().endsWith(".csv")) {
						processarArquivo(arquivo.toPath());
					}
				}
			} else {
				System.out.println("Nenhum arquivo.CSV encontrado no diretório PENDENTE.");
			}

		} catch (IOException e) {
			System.err.println("Erro ao configurar diretórios: " + e.getMessage());
			return;
		}
	}



	private static void configurarDiretorios() throws IOException {
		if (!Files.exists(PENDENTE_DIR)) {
			System.out.println("Diretório de arquivos pendentes não existe.");
			System.out.println("Criando diretório PENDENTE...");
			Files.createDirectories(PENDENTE_DIR);
			System.out.println("Caminho: " + PENDENTE_DIR.toAbsolutePath());
			System.out.println(
					"Encerrando o programa. Coloque os arquivos CSV no diretório PENDENTE e execute novamente.");
			return;
		}

		if (!Files.exists(VALIDO_DIR)) {
			System.out.println("Criando diretório VALIDO...");
			Files.createDirectories(VALIDO_DIR);
		}

		if (!Files.exists(INVALIDO_DIR)) {
			System.out.println("Criando diretório INVALIDO...");
			Files.createDirectories(INVALIDO_DIR);
		}
	}



	private static void processarArquivo(Path arquivo) {
		String nomeArquivo = arquivo.getFileName().toString();

		// O uso de resolve() combina o caminho da pasta com o nome do arquivo
		try (BufferedReader reader = Files.newBufferedReader(arquivo);
				BufferedWriter writerValido = Files.newBufferedWriter(VALIDO_DIR.resolve(nomeArquivo));
				BufferedWriter writerInvalido = Files.newBufferedWriter(INVALIDO_DIR.resolve(nomeArquivo))) {

			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");

				if (isLinhaValida(campos)) {
					writerValido.write(linha);
					writerValido.newLine();
				} else {
					writerInvalido.write(linha);
					writerInvalido.newLine();
				}
			}
			System.out.println("Processado: " + nomeArquivo);

		} catch (IOException e) {
			System.err.println("Erro ao processar " + nomeArquivo + ": " + e.getMessage());
		}
	}



	// Método centralizador de regras de validação.
	private static boolean isLinhaValida(String[] campos) {
		// Regra 1: Deve ter exatamente 5 campos.
		if (campos.length != 5) {
			return false;
		}

		// Regra 2: Nenhum campo pode ser vazio ou só espaços.
		for (String campo : campos) {
			if (campo == null || campo.isBlank()) {
				return false;
			}
		}

		// Regra 3: Validar se os campos 0, 1, 2 e 3 são Inteiros.
		for (int i = 0; i <= 3; i++) {
			if (!isInteger(campos[i])) {
				return false;
			}
		}

		// Regra 4: O campo 4 tem que ser DateTime.
		if (!isDateTime(campos[4])) {
			return false;
		}

		return true;
	}



	// Método auxiliar para testar se a String é um Integer válido.
	private static boolean isInteger(String valor) {
		try {
			Integer.parseInt(valor.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}



	// Método auxiliar para testar se a String é um LocalDateTime válido.
	private static boolean isDateTime(String valor) {
		try {
			LocalDateTime.parse(valor.trim(), FORMATO_DATA_HORA);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

}
