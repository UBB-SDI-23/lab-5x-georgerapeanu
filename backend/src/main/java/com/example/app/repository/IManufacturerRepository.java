package com.example.app.repository;

import com.example.app.dto.ManufacturerProductCountDTO;
import com.example.app.dto.model.ManufacturerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IManufacturerRepository {
    public Page<ManufacturerProductCountDTO> getManufacturersSortedByProductCount(Pageable pageable);
    public List<ManufacturerProductCountDTO> getManufacturerProductCountsFromList(List<ManufacturerDTO> manufacturerDTOS);

    public Integer getManufacturerCountForUserHandle(String userHandle);
}
