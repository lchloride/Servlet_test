package example;

import java.util.ArrayList;
import java.util.Date;

public class Anime {
	String anime_name;
	Date start_time;
	String company;
	String writer_name;
	int episode_number;
	String director_name;
	String scriptwriter_name;
	
	
	public String getAnimeName() {
		return anime_name;
	}


	public void setAnimeName(String anime_name) {
		this.anime_name = anime_name;
	}


	public Date getStartTime() {
		return start_time;
	}


	public void setStartTime(Date start_time) {
		this.start_time = start_time;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getWriterName() {
		return writer_name;
	}


	public void setWriterName(String writer_name) {
		this.writer_name = writer_name;
	}


	public int getEpisodeNumber() {
		return episode_number;
	}


	public void setEpisodeNumber(int episode_number) {
		this.episode_number = episode_number;
	}


	public String getDirectorName() {
		return director_name;
	}


	public void setDirectorName(String director_name) {
		this.director_name = director_name;
	}


	public String getScriptwriterName() {
		return scriptwriter_name;
	}


	public void setScriptwriterName(String scriptwriter_name) {
		this.scriptwriter_name = scriptwriter_name;
	}


	public void form(String an, Date st, String co, String wn, int en, String dn, String sn) {
		setAnimeName(an);
		setStartTime(st);
		setCompany(co);
		setWriterName(wn);
		setEpisodeNumber(en);
		setDirectorName(dn);
		setScriptwriterName(sn);
	}
	
	public ArrayList<Object> format() {
		ArrayList<Object> item_list = new ArrayList<Object>();
		item_list.add(anime_name);
		item_list.add(start_time);
		item_list.add(company);
		item_list.add(writer_name);
		item_list.add(episode_number);
		item_list.add(director_name);
		item_list.add(scriptwriter_name);
		return item_list;
	}
}
