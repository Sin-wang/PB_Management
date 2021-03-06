package com.base.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.base.dao.LandInfoDao;
import com.base.po.BaseInfo;
import com.base.po.ExportBase;
import com.base.po.LandInfo;
import com.base.po.Land_base;
import com.base.utils.SqlConnectionUtils;

@Repository("landInfoDao")
public class LandInfoDaoImpl implements LandInfoDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void updateSpareValue(int lid,int spareValue) {
		Session session=sessionFactory.openSession();		
		Transaction tx=null;
		
		try {
			 tx=session.beginTransaction();
	    	 LandInfo li=(LandInfo) session.get(LandInfo.class,lid);
	    	 li.setSpareValue(lid);
	    	 session.update(li);
	    	 tx.commit();
	    	
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();// �ع����񣬳����ѯ���
			}
			System.out.println(e);
		}finally{
			session.close();
		}

	}
	
	public List<Land_base> getView(String lid)
	{
		Session session=sessionFactory.openSession();		
		String hql="from Land_base where lid=?";		
		List<Land_base> li=null;
		
		try {
	    	 Query query=session.createQuery(hql);
	    	 query.setString(0, lid);
	    	 li=query.list();
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			session.close();
		}
		return li;
	}
	
	public List<Land_base> getlandbase(int bid)
	{
		Session session=sessionFactory.openSession();		
		String hql="from Land_base where bid=?";		
		List<Land_base> li=null;
		
		try {
	    	 Query query=session.createQuery(hql);
	    	 query.setInteger(0, bid);
	    	 li=query.list();
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			session.close();
		}
		return li;
	}

	@Override
	public List<LandInfo> getLandInfo(String lid) {
		Session session=sessionFactory.openSession();		
		String hql="from LandInfo where lid=?";		
		List<LandInfo> li=null;
		
		try {
	    	 Query query=session.createQuery(hql);
	    	 query.setString(0, lid);
	    	 li=query.list();
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			session.close();
		}
		return li;
	}

	

	@Override
	public List<LandInfo> getLandInfos(int bid) {
		Session session=sessionFactory.openSession();		
		String hql="from LandInfo where bid=?";		
		List<LandInfo> list=null;
		
		 try {
	    	 Query query=session.createQuery(hql);
	    	 query.setInteger(0, bid);
	    	 list=query.list();
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			session.close();
		}		
		return list;
	}
	
	public void doInfo(LandInfo li) {
		Session session=sessionFactory.openSession();		
		Transaction tx=null;
		
		try {
			 tx=session.beginTransaction();	    	
	    	 session.save(li);
	    	 tx.commit();
	    	
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();// �ع����񣬳����ѯ���
			}
			System.out.println(e);
		}finally{
			session.close();
		}

	}
	public List<String> deletelandimg(int bid)
	{
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		Session session=sessionFactory.openSession();
		List<String> list=new ArrayList<String>();
		try
		{
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.`findresource`(?)}");
			sp.setInt(1, bid);
			sp.execute();   //ִ�д洢���
			rs=sp.getResultSet();  //��ý��
			while(rs.next())
			{
				String str=rs.getString("img");
				list.add(str);
			}

		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			SqlConnectionUtils.free(conn, sp, rs);			
		}
		return list;

	}

	@Override
	public int delLayout_info(int bid)
	{		
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		int flag=0;
		try {
		    conn = (Connection) SessionFactoryUtils.getDataSource(
			    sessionFactory).getConnection();
		    sp = (CallableStatement) conn
			    .prepareCall("{call baseweb.`delete_land`(?,?)}");
		    sp.setInt(1, bid);	   
		    sp.registerOutParameter(2, java.sql.Types.INTEGER);
		    sp.execute();
		    flag = sp.getInt(2);
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    SqlConnectionUtils.free(conn, sp, rs);
		}
		return flag;
	}
	
	public int doLayout_info(String landinfoStr,String layoutStr,int bid)
	{		
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		int flag=0;
		try {
		    conn = (Connection) SessionFactoryUtils.getDataSource(
			    sessionFactory).getConnection();
		    sp = (CallableStatement) conn
			    .prepareCall("{CALL baseweb.insert_land(?,?,?,?)}");
		    sp.setString(1, landinfoStr);
		    sp.setString(2, layoutStr);
		    sp.setInt(3,bid);
		    sp.registerOutParameter(4, java.sql.Types.INTEGER);
		    sp.execute();
		    flag = sp.getInt(4);
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    SqlConnectionUtils.free(conn, sp, rs);
		}
		return flag;
	}

}
