package com.advocacia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advocacia.entities.Documentos_Processo;

public interface Documentos_ProcessoRepository extends JpaRepository<Documentos_Processo, Integer> {

	Documentos_Processo findByNomeArquivo(String nomeArquivo);

}
