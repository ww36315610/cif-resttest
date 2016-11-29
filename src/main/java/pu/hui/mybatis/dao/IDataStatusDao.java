package pu.hui.mybatis.dao;

import org.springframework.stereotype.Repository;

import pu.hui.mybatis.entity.DataStatusEntity;
@Repository(value="idataStatusDao")
public interface IDataStatusDao {
	
	public DataStatusEntity getDataStatus(DataStatusEntity dataStatus);
}
