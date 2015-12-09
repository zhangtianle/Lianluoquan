package com.tianle.service.article;

import net.sf.json.JSONObject;

import com.tianle.model.base.Article;

/**
 * 通过json获得article
 * Comments:
 * @author Kyle
 * @date 2015年5月1日 下午5:58:24
 */
public class RecArticle {
	/**
	 * 将json字符串转化成文章类
	 * @param json 文章的json String
	 * @return Article类
	 */
	public Article getArt(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Article article = (Article) JSONObject
				.toBean(jsonObject, Article.class);
		return article;
	}
	
}
