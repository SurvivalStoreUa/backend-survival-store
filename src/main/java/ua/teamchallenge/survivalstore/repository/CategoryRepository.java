package ua.teamchallenge.survivalstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.teamchallenge.survivalstore.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
