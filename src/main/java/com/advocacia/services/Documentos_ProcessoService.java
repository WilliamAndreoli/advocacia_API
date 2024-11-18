package com.advocacia.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.advocacia.entities.Documentos_Processo;
import com.advocacia.entities.Processo;
import com.advocacia.repositories.Documentos_ProcessoRepository;

@Service
public class Documentos_ProcessoService {

	@Autowired
	private Documentos_ProcessoRepository doc_ProcessoRepository;
	
	@Autowired
	private ProcessoService processoService;
	
	private String caminhoDiretorio = "/home/william/Documentos/Desktop/stay-developing/advocacia_andreoli/back-end/advocacia_API/uploads/";
	
	public List<Documentos_Processo> findAll() {
		return doc_ProcessoRepository.findAll();
	}
	
	public Documentos_Processo findByNomeArquivo(String nomeArquivo) {
		return doc_ProcessoRepository.findByNomeArquivo(nomeArquivo);
	};
	
	public Documentos_Processo uploadArquivo(MultipartFile file, String numeroProcesso) {
	    // Define o nome completo do arquivo e o caminho final
	    String fileName = file.getOriginalFilename();
	    String filePath = caminhoDiretorio + numeroProcesso + "/" + fileName; // Subdiretório baseado no número do processo

	    Processo processo = processoService.findByNumeroProcesso(numeroProcesso);
	    
	    try {
	        // Cria o diretório se não existir
	        File dir = new File(caminhoDiretorio + numeroProcesso);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        // Salva o arquivo no diretório especificado
	        File destino = new File(filePath);
	        file.transferTo(destino);

	        // Cria o objeto Documentos_Processo e atribui os valores
	        Documentos_Processo doc = new Documentos_Processo();
	        doc.setNomeArquivo(fileName);
	        doc.setCaminhoArquivo(filePath);
	        doc.setProcesso(processo);

	        // Salva os metadados do arquivo no banco de dados
	        return doc_ProcessoRepository.save(doc);
	    } catch (IOException e) {
	        throw new RuntimeException("Erro ao salvar o arquivo", e);
	    }
	}
	
	public Resource downloadArquivo(String numeroProcesso, String nomeArquivo) {
        try {
            // Constrói o caminho completo para o arquivo
            Path filePath = Paths.get(caminhoDiretorio + numeroProcesso).resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado ou não pode ser lido.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar o arquivo.", e);
        }
    }
	
	public void deleteArquivo(String numeroProcesso, String nomeArquivo) {
        // Busca o documento no banco pelo nome do arquivo
        Documentos_Processo documento = doc_ProcessoRepository.findByNomeArquivo(nomeArquivo);
        
        if (documento == null) {
            throw new RuntimeException("Documento não encontrado no banco de dados.");
        }

        try {
            // Deleta o arquivo do sistema de arquivos
            Path filePath = Paths.get(caminhoDiretorio + numeroProcesso).resolve(nomeArquivo).normalize();
            Files.deleteIfExists(filePath);

            // Remove o registro do banco de dados
            doc_ProcessoRepository.delete(documento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o arquivo.", e);
        }
    }
	
}
