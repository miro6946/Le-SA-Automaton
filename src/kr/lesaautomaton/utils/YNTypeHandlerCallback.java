package kr.lesaautomaton.utils;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class YNTypeHandlerCallback implements TypeHandlerCallback {
	
	private static final String TRUE_VALUE = "Y";
	private static final String FALSE_VALUE = "N";

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		// TODO Auto-generated method stub
		String s = getter.getString();
		if(s.equals(TRUE_VALUE)){
			
			return new Boolean(true);
			
		}else if(s.equals(FALSE_VALUE)){
			
			return new Boolean(false);
			
		}else{
			
			throw new SQLException("Unexpected value "+ s +" found where "+ TRUE_VALUE +" or "+ FALSE_VALUE +" was expected.");
			
		}
	}

	@Override
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		// TODO Auto-generated method stub
		setter.setString(((Boolean)parameter).booleanValue() ? TRUE_VALUE : FALSE_VALUE);
	}

	@Override
	public Object valueOf(String s) {
		// TODO Auto-generated method stub
		if(s.equals(TRUE_VALUE)){
			
			return new Boolean(true);
			
		}else if(s.equals(FALSE_VALUE)){
			
			return new Boolean(false);
			
		}else{
			
			return null;
			
		}
	}

}
