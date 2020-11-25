package model;

public class UpFile {
	public String name;
	public String size;
	public String type;
	public String file;
	public String zhuanye;
	public int id;
	
	
	public String getZhuanye() {
		return zhuanye;
	}
	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "UpFile [name=" + name + ", size=" + size + ", type=" + type + ", file=" + file + ", zhuanye=" + zhuanye
				+ ", id=" + id + "]";
	}
	public void setId(long long1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
