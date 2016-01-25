package org.nishkarma.common.util;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.sql.*;

@SuppressWarnings("rawtypes")
@MappedTypes(DateTime.class)
public class DateTimeTypeHandler implements TypeHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement
	 * , int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setTimestamp(i,
					new Timestamp(((DateTime) parameter).getMillis()));
		} else {
			ps.setTimestamp(i, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
	 * java.lang.String)
	 */
	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName);
		if (ts != null) {
			return new DateTime(ts.getTime(), DateTimeZone.UTC);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement,
	 * int)
	 */
	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp ts = cs.getTimestamp(columnIndex);
		if (ts != null) {
			return new DateTime(ts.getTime(), DateTimeZone.UTC);
		} else {
			return null;
		}
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnIndex);
		if (ts != null) {
			return new DateTime(ts.getTime(), DateTimeZone.UTC);
		} else {
			return null;
		}
	}

}