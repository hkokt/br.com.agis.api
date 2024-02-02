package com.br.fatec.AGIS.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.DataDto;
import com.br.fatec.AGIS.model.Data;
import com.br.fatec.AGIS.repository.DataRepository;

@Service
public class DataService {
	@Autowired
	private DataRepository dataRepository;
	
	public List<Data> selectAll() {
		return dataRepository.findAll();
	}
	
	public Data selectById(LocalDate dia) throws Exception {
		Optional<Data> data = dataRepository.findById(dia);
		if (data.isEmpty()) {
			throw new Exception("Data não registrado");
		}

		return data.get();
	}

	public Data insert(DataDto dataDto) {
		var dataModel = new Data();
		BeanUtils.copyProperties(dataDto, dataModel);
		dataModel.setAno(LocalDate.now().getYear());
		return dataRepository.save(dataModel);
	}

	public Data update(LocalDate dia, DataDto dataDto) throws Exception {
		Optional<Data> data = dataRepository.findById(dia);
		if (data.isEmpty()) {
			throw new Exception("Curso não registrado");
		}

		var dataModel = data.get();
		BeanUtils.copyProperties(dataDto, dataModel);

		return dataRepository.save(dataModel);
	}

	public Data delete(LocalDate dia) throws Exception {
		Optional<Data> data = dataRepository.findById(dia);
		if (data.isEmpty()) {
			throw new Exception("Curso não registrado");
		}

		var dataModel = data.get();
		dataRepository.delete(dataModel);

		return dataModel;
	}
}
