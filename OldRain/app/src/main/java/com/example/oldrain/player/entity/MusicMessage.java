package com.example.oldrain.player.entity;

public class MusicMessage {
	private String artist;
	private String title;
	private String album;
//	private int year;
	private String year;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	private int duration;
	private String _data;
	private int artist_id;
	private int album_id;
	public int getArtist_id() {
		return artist_id;
	}
	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}
	public int getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
//	public int getYear() {
//		return year;
//	}
//	public void setYear(int year) {
//		this.year = year;
//	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String get_data() {
		return _data;
	}
	public void set_data(String _data) {
		this._data = _data;
	}
//	@Override
//	public String toString() {
//		return "MusicMessage [artist=" + artist + ", title=" + title
//				+ ", album=" + album + ", year=" + year + ", duration="
//				+ duration + ", _data=" + _data + "]";
//	}
	
}
