package com.article.service.repositories;

import com.article.service.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findByTitleStartingWithIgnoreCase(String prefix);
}

