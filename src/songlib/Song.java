package songlib;

public class Song implements Comparable<Song>{
	private String name;
	
	private String artist;
	
	private String album;
	
	private String year;

	 public Song(String name, String artist, String album, String year) {
	      this.name = name;
	      this.artist = artist;
	      this.album = album;
	      this.year = year;
	   }
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public boolean equals(Object s){
		
		if(s==null)
			return false;
		Song song = (Song)s;
		//return false;
		return name.equals(song.name) && artist.equals(song.artist);
	}
	
	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		return name.toLowerCase().compareTo(o.name.toLowerCase());
	}
	
	@Override
	public String toString() {
		return name;
	}
}
