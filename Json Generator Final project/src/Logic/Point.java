package Logic;

import java.util.Arrays;
import java.util.HashMap;

public class Point {
	private static int counter = 0;
	private int id;
	private String Picture;
	private HashMap<Integer, Neighbor> Neighbors;
	private HashMap<Integer,Optionaltext> OptionalTexts;
	private String nickName = "";

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public HashMap<Integer, Neighbor> getNeighbors() {
		return Neighbors;
	}
	public HashMap<Integer,Optionaltext> getOptionalTexts() {
		return OptionalTexts;
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Point(String pic) {
		id = (++counter);
		Picture = pic;
		Neighbors = new HashMap<>();
		OptionalTexts = new HashMap<>();
		String[] path = Picture.split("\\\\");
		nickName = path[path.length-1];
	}
	public Point(String pic, int ID) { // For Json loader
		this.id = ID;
		if(counter<ID) 
			counter = ID;
		Picture = pic;
		Neighbors = new HashMap<>();
		OptionalTexts = new HashMap<>();
		String[] path = Picture.split("/");
		nickName = path[path.length-1];
	}

	public void AddNeighbor(int Neighbor_Id, float Azimut) {
		if(Neighbors.containsKey(Neighbor_Id))
			throw new IllegalArgumentException("Neighbor allready exists");
		Neighbors.put(Neighbor_Id, new Neighbor(this.id, Neighbor_Id, Azimut));
	}

	public void EditNeighbor(int Neighbor_id, float az) {
		if(!Neighbors.containsKey(Neighbor_id))
			throw new IllegalArgumentException("Neighbor doesn't exists");
		Neighbors.get(Neighbor_id).setAzimut(az);
	}

	public void RemoveNeighbor(int Neighbor_Id) {
		if(!Neighbors.containsKey(Neighbor_Id))
			throw new IllegalArgumentException("Neighbor doesn't exists");
		Neighbors.remove(Neighbor_Id);
	}

	public int AddOptionalText(String text, float dur, float when) {
		Optionaltext temp = new Optionaltext(text, dur, when);
		OptionalTexts.put(temp.getId(), temp);
		return temp.getId();
	}


	public void EditOptionalText(int textId, String text, float dur, float when) {
		if(!OptionalTexts.containsKey(textId))
			throw new IllegalArgumentException("text doesn't exists");
		OptionalTexts.get(textId).setText(text);
		OptionalTexts.get(textId).setDurationInSeconds(dur);
		OptionalTexts.get(textId).setWhenToDisplay(when);
	}

	public void RemoveOptionalText(int id) {
		if(!OptionalTexts.containsKey(id))
			throw new IllegalArgumentException("text doesn't exists");
		OptionalTexts.remove(id);
	}
	public String toJson(boolean ans)
	{
		String newPath;
		String PhonePath = "/storage/emulated/0/Android/data/com.Ariel.VrNavigation/files/Pictures/";
		String[] pic = Picture.split("\\\\");
		String [] pic2 = Picture.split("/");
		String picName = pic[pic.length-1];
		if(pic.length <= 1) {
			picName = pic2[pic2.length-1];
		}
		if(!ans) // phone version
			newPath = PhonePath+picName;
		else newPath = Picture.replaceAll("\\\\", "/");
		String s = "{ \"id\" :" + id + ",\n\"Picture\" : \"" +newPath + "\",\n\"Neighbors\" : \n[";
		for(int i : Neighbors.keySet()) {
			s += Neighbors.get(i).toString() + ",";
		}
		if(Neighbors.size()>0)
			s = s.substring(0, s.length()-1);
		s += "],\n \"OptionalText\" : [";

		for(int i : OptionalTexts.keySet()) {
			s += OptionalTexts.get(i).toString() + ",";
		}
		if(OptionalTexts.size()>0)
			s = s.substring(0, s.length()-1);
		s+= "\n]}";
		return s;
	}
}