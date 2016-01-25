package org.nishkarma.book.model;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

@SuppressWarnings("rawtypes")
public class BookCoverTypeHandler implements TypeHandler {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(i,
					CoverConverter.convertToDatabaseColumn((Cover) parameter));
		} else {
			ps.setTimestamp(i, null);
		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		String cover = rs.getString(columnName);
		if (cover != null) {
			return CoverConverter.convertToEntityAttribute(cover);
		} else {
			return null;
		}
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String cover = cs.getString(columnIndex);
		if (cover != null) {
			return CoverConverter.convertToEntityAttribute(cover);
		} else {
			return null;
		}
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		String cover = rs.getString(columnIndex);
		if (cover != null) {
			return CoverConverter.convertToEntityAttribute(cover);
		} else {
			return null;
		}
	}

}
