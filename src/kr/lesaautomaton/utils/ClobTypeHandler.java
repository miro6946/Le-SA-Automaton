package kr.lesaautomaton.utils;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.DelegatingConnection;

import oracle.sql.CLOB;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;
import com.ibatis.sqlmap.engine.type.TypeHandler;

public class ClobTypeHandler extends BaseTypeHandler implements TypeHandler {

	@Override
	public Object getResult(CallableStatement cstmt, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		Clob clob = cstmt.getClob(columnIndex);
		if(cstmt.wasNull()){
			return null;
		}else{
			return clob.getSubString(1, (int)clob.length());
		}
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		Clob clob = rs.getClob(columnIndex);
		if(rs.wasNull()){
			return null;
		}else{
			return clob.getSubString(1, (int)clob.length());
		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		Clob clob = rs.getClob(columnLabel);
		if(rs.wasNull()){
			return null;
		}else{
			return clob.getSubString(1, (int)clob.length());
		}
	}

	@Override
	public void setParameter(PreparedStatement pstmt, int i, Object param,
			String jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		
		String value = (String)param;
		
		if(value != null){
			
			Connection conn = pstmt.getConnection();
			
			System.out.println(conn.getClass().getName());
			
			if(conn instanceof DelegatingConnection){
				
				System.out.println("--------------------------------------");
				
				Connection temp = (DelegatingConnection)conn;
				
				System.out.println(temp.getClass().getName());
				System.out.println(temp == null);
				
				System.out.println(((DelegatingConnection)conn).getDelegate() == null);
				
				//Connection temp = ((DelegatingConnection)conn).getInnermostDelegate();
				//System.out.println("temp:"+ temp.getClientInfo().toString());
				//conn = ((DelegatingConnection)temp).getDelegate();
				
			}
			
			CLOB clob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);
			clob.putChars(1, value.toCharArray());
			pstmt.setClob(i, clob);
			
			System.out.println("------------------------------------------------------------");
			
		}else{
			pstmt.setString(i, null);
		}
		
	}

	@Override
	public Object valueOf(String s) {
		// TODO Auto-generated method stub
		return s;
	}
	
}
