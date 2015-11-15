package org.khmeracademy;

import java.util.ArrayList;

import org.khmeracademy.service.ArticleService;
import org.khmeracademy.service.model.dto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@Autowired
	ArticleService article;
	
	@RequestMapping(value={"/", "/home", "/index"})
	public String homepage(ModelMap model){
		model.addAttribute("msg", "Welcome to Search Engine with Lucene");
		return "home";
	}
	
	@RequestMapping(value="/createindex", method= RequestMethod.POST)
	public @ResponseBody boolean createIndex(){
		return article.createIndexing();
	}
	
	@RequestMapping(value="/searcharticle", method= RequestMethod.POST)
	public @ResponseBody ArrayList<Article> searchArticle(
			@RequestParam("query") String query,
			@RequestParam("pageIndex") int pageIndex,
			@RequestParam("pageSize") int pageSize
	){
		
		return article.searchArticle(query, pageIndex, pageSize);
	}
	
	
}
