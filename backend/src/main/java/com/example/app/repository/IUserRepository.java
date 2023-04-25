package com.example.app.repository;

import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.UserDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserRepository {
    List<UserReviewCountDTO> getUserReviewCount(List<UserDTO> users);
}
