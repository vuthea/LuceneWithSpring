package org.khmeracademy.service.model.dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.khmeracademy.service.ArticleService;
import org.khmeracademy.service.model.dto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDAO implements ArticleService{
	
	@Autowired
	private DataSource ds;
	private Connection cnn;
	final String indexPath = "D:\\index_search";
	Analyzer analyzer;
	boolean created = true;
	public boolean createIndexing() {
		
		try{
			cnn = ds.getConnection();
			
			analyzer = new StandardAnalyzer();
			
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			IndexWriterConfig conf= new IndexWriterConfig(analyzer);
			
			if(created){
				conf.setOpenMode(OpenMode.CREATE);
				created= false;
			}else{
				conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			IndexWriter writer = new IndexWriter(dir, conf);
			
			
			String sql = "SELECT id, title, date from articles";
			PreparedStatement ps = cnn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()){
				int id = rs.getInt("id");
				String title = rs.getString("title");
				Date time= rs.getDate("date");
				String date = buildDate(time);
				addIndexingWriter(writer, id, title, date);
			}
			
			rs.close();
			writer.close();
			
			
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			
			return false;
		}finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addIndexingWriter(IndexWriter writer, int id, String title, String date) throws IOException{
		Document doc= new Document();
		doc.add(new IntField("id", id, Field.Store.YES));
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new StringField("date", date, Field.Store.YES));
		
		writer.addDocument(doc);
	}
	
	public ArrayList<Article> searchArticle(String queryString, int pageIndex,
			int pageSize) {
		
		ArrayList<Article> articles= new ArrayList<Article>();
		Article article= null;
		IndexReader reader;
		IndexSearcher searcher;
		QueryParser parser;
		
		try{
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			searcher = new IndexSearcher(reader);
			analyzer = new StandardAnalyzer();
			parser = new QueryParser("title", analyzer);
			
			TopScoreDocCollector collector = TopScoreDocCollector.create(pageIndex * pageSize);
			Query query = parser.parse(queryString);
			
			int startIndex = (pageIndex -1)* pageSize;
			searcher.search(query, collector);
			ScoreDoc[] hits= collector.topDocs(startIndex, pageSize).scoreDocs;

			for(int i=0; i< hits.length; i++){
				Document doc= searcher.doc(hits[i].doc);
				article = new Article();
				article.setId(Integer.parseInt(doc.get("id")));	
				article.setTitle(doc.get("title"));
				
				article.setDate(doc.get("date"));
				article.setTotalRecords(collector.getTotalHits());
				articles.add(article);
				
			}
			reader.close();
			analyzer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return articles;
	}
	
	public String buildDate(Date time){
		return DateTools.dateToString(time, Resolution.DAY);
	}
	
	
}
