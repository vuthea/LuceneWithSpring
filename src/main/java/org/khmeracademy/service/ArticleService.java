package org.khmeracademy.service;

import java.util.ArrayList;

import org.khmeracademy.service.model.dto.Article;

public interface ArticleService {
	public boolean createIndexing();
	public ArrayList<Article> searchArticle(String query, int pageIndex, int pageSize);
}
