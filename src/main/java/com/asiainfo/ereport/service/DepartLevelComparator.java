package com.asiainfo.ereport.service;

public interface DepartLevelComparator {
	/**
	 * 比较departid 对应的部门级别和departlevel
	 * 
	 * @param departid
	 * @param departlevel
	 * @return
	 */
	public boolean isHigherLevelDept(String departid, Integer referencedepartlevel);

	/**
	 * 比较departid 对应的部门级别和departlevel
	 * 
	 * @param departid
	 * @param departlevel
	 * @return
	 */
	public boolean isLowerLevelDept(String departid, Integer referencedepartlevel);

	/**
	 * 
	 * @param referencedeptid
	 * @param deptid
	 * @return
	 */
	public boolean isHigherLevelDept( String deptid,String referencedeptid);

	/**
	 * 
	 * @param referencedeptid
	 * @param deptid
	 * @return
	 */
	public boolean isLowerLevelDept( String deptid,String referencedeptid);
}
