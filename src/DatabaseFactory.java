// enum is a special class used to declare a group of related constants, which refer to the database type in this case
enum DatabaseType {
    MYSQL, ORACLEDB
}

// Single location that knows about concrete DB implementations. So, any database configuration changes will only need to be done here (fulfil F15)
public class DatabaseFactory {
	private static final DatabaseType DATABASE_TYPE = DatabaseType.MYSQL;
	
    public static Database createDB() {
    	switch(DATABASE_TYPE) {
	    	case MYSQL:
	    		return new MySQLDatabase();
	    	case ORACLEDB:
	    		return new OracleDatabase();
			default:
				throw new IllegalArgumentException("Unsupported database type: " + DATABASE_TYPE);
    	}
    }
}
