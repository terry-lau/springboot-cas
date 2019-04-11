package com.fr.record;

import com.fr.data.core.db.DBUtils;
import com.fr.data.core.db.dialect.Dialect;
import com.fr.data.core.db.dialect.DialectFactory;
import com.fr.data.core.db.dml.DML;
import com.fr.data.core.db.dml.Select;
import com.fr.data.dao.JdbcOperator;
import com.fr.data.dao.ResultSetExtractor;

public class JdbcTemplate {
	private com.fr.data.impl.Connection databaseConnection;
	private Dialect dialect = null;

	public JdbcTemplate(com.fr.data.impl.Connection paramConnection) {
		this.databaseConnection = paramConnection;
	}

	private Dialect getDialect(java.sql.Connection paramConnection) {
		if (this.dialect == null) {
			synchronized (this) {
				this.dialect = DialectFactory.generateDialect(paramConnection);
			}
		}
		return this.dialect;
	}

	/* Error */
	public void execute(String paramString) throws Exception {
	}

	public Object query(String paramString,
			ResultSetExtractor<?> paramResultSetExtractor) throws Exception {
		java.sql.Connection localConnection = this.databaseConnection
				.createConnection();
		try {
			Object localObject1 = JdbcOperator.query(localConnection,
					paramString, paramResultSetExtractor);
			return localObject1;
		} finally {
			DBUtils.closeConnection(localConnection);
		}
	}

	public Object executeQuery(Select paramSelect,
			ResultSetExtractor<?> paramResultSetExtractor) throws Exception {
		java.sql.Connection localConnection = this.databaseConnection
				.createConnection();
		try {
			Dialect localDialect = getDialect(localConnection);
			Object localObject1 = JdbcOperator.executeQuery(localConnection,
					localDialect, paramSelect, paramResultSetExtractor);
			return localObject1;
		} finally {
			DBUtils.closeConnection(localConnection);
		}
	}

	public void execute(DML paramDML) throws Exception {
	}
}
