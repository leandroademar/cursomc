package com.datitecnologia.cursomc.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.datitecnologia.cursomc.domain.Categoria;
import com.datitecnologia.cursomc.repositories.CategoriaRepository;
import com.datitecnologia.cursomc.resources.exceptions.DataIntegrityException;
import com.datitecnologia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		try {
			 repo.deleteById(id);
			
		}catch(DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}catch (EmptyResultDataAccessException e) 
		{
			throw new DataIntegrityException("Não é possível excluir uma categoria que não existe");
		}
		
	}
}
