package com.base.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.base.po.MaintainApplys;
import com.base.po.MaintainList;
import com.base.po.Prabaseinfo;
import com.base.utils.SqlConnectionUtils;

@Repository("MaintainApplyDao")
public class MaintainApplyDao
{
	@Autowired
	private SessionFactory sessionfactory;
	//查询所有的基地列表
	public List<Prabaseinfo> find_basename()
	{
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		List<Prabaseinfo> list =new ArrayList<Prabaseinfo>();
		try
		{
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionfactory).getConnection();
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.query_schoolbase()}");
			sp.execute();
			rs=sp.getResultSet();
			while(rs.next())
			{
				Prabaseinfo pra=new Prabaseinfo();
				pra.setId(rs.getString("id"));
				pra.setName(rs.getString("name"));
				list.add(pra);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			SqlConnectionUtils.free(conn, sp, rs);
		}
		return list;
	}
	//插入项目维修申请
	public void insert_maintain(String str)
	{
		System.out.println(str);
		Connection conn = null;
		CallableStatement sp = null;
		try {
			conn = (Connection) SessionFactoryUtils.getDataSource(
					sessionfactory).getConnection();
			sp = (CallableStatement) conn
					.prepareCall("{CALL baseweb.`insert_maintain`(?)}");
			sp.setString(1, str);
			sp.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.free(conn, sp, null);
		}
	}
	//查询完成的校内基地维修申请记录,参数顺序：当前页面记录数，当前页数，排序列，排序顺序，模糊查询的字符串,返回总记录数
	public List<MaintainList> query_maintainapply(int offsets,int page,String str,String str1,String str2)
	{
		List<MaintainList> list=new ArrayList<MaintainList>();
		List<MaintainApplys> ma=new ArrayList<MaintainApplys>();
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		try
		{
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionfactory).getConnection();
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.query_maintainapply(?,?,?,?,?,?)}");
			sp.setInt(1,offsets);
			sp.setInt(2,page);
			sp.setString(3, str);
			sp.setString(4, str1);
			sp.setString(5, str2);
			sp.registerOutParameter(6,java.sql.Types.INTEGER);
			sp.execute(); 
			((MaintainList) list).setRecordsTotal(sp.getInt(6));
			rs=sp.getResultSet();
			while(rs.next())
			{
				MaintainApplys mt=new MaintainApplys();
				mt.setAddress(rs.getString("address"));
				mt.setApply_time(rs.getString("apply_time"));
				mt.setBasename(rs.getString("basename"));
				mt.setFile(rs.getString("file"));
				mt.setId(rs.getInt("id"));
				mt.setMoney(rs.getDouble("money"));
				mt.setPro_name(rs.getString("pro_name"));
				mt.setReason(rs.getString("reason"));
				mt.setUserid(rs.getString("userid"));
				mt.setUsername(rs.getString("username"));
				mt.setActualmoney(rs.getDouble("actualmoney"));
				ma.add(mt);
			}
			((MaintainList) list).setData(ma);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			SqlConnectionUtils.free(conn, sp, rs);
		}
		return list;
	}
	//删除维修基地申请记录，传的值为维修记录id的集合
	public void delete_maintainapply(String str)
	{
		Connection conn = null;
		CallableStatement sp = null;
		try
		{
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionfactory).getConnection();
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.delete_maintainapply(?)}");
			sp.setString(1, str);
			sp.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			SqlConnectionUtils.free(conn, sp, null);
		}
	}
	//增加维修基地记录（已完成的维修）
	public void add_maintain(String pronames,String bids,String usernames,String address,String reasons,String files,String userids,double actuals)
	{
		Connection conn = null;
		CallableStatement sp = null;
		try
		{
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionfactory).getConnection();
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.add_maintainapply(?,?,?,?,?,?,?,?)}");
			sp.setString(1, pronames);
			sp.setString(2, bids);
			sp.setString(3, usernames);
			sp.setString(4, address);
			sp.setString(5, reasons);
			sp.setString(6, files);
			sp.setString(7, userids);
			sp.setDouble(8, actuals);
			sp.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			SqlConnectionUtils.free(conn, sp, null);
		}
	}
	//导出基地维修记录，参数为筛选条件，第一个基地名字，第二个为年份（如没有，则为-1）
	public List<MaintainApplys> export_maintainapply(String bname,int years)
	{
		List<MaintainApplys> ma=new ArrayList<MaintainApplys>();
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		try
		{
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionfactory).getConnection();
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.export_maintainapply(?,?)}");
			sp.setString(1, bname);
			sp.setInt(2,years);
			sp.execute();
			rs=sp.getResultSet();
			while(rs.next())
			{
				MaintainApplys mt=new MaintainApplys();
				mt.setAddress(rs.getString("address"));//
				mt.setApply_time(rs.getString("applytime"));//
				mt.setBasename(rs.getString("basename"));//
				mt.setPro_name(rs.getString("proname"));//
				mt.setReason(rs.getString("reason"));//
				mt.setUsername(rs.getString("username"));//
				mt.setActualmoney(rs.getDouble("moneys"));//
				ma.add(mt);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			SqlConnectionUtils.free(conn, sp, rs);
		}
		return ma;
	}
}
