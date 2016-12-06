package pu.hui.mybatis.entity;

public class DataStatusEntity {

	private int uid;
	private int type;
	private int status;
	private int bonus_type;
	private int is_require;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBonus_type() {
		return bonus_type;
	}
	public void setBonus_type(int bonus_type) {
		this.bonus_type = bonus_type;
	}
	public int getIs_require() {
		return is_require;
	}
	public void setIs_require(int is_require) {
		this.is_require = is_require;
	}
	
}
