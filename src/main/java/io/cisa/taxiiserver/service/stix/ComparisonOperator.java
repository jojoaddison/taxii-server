package io.cisa.taxiiserver.service.stix;

public abstract class ComparisonOperator {
	
	public final static String LESS_THAN = "<";
	public final static String GREATER_THAN = ">";
	public final static String EQUAL_TO = "=";
	public final static String NOT_EQUAL_TO = "!=";
	public final static String LESS_THAN_OR_EQUAL_TO="<=";
	public final static String GREATER_THAN_OR_EQUAL_TO = ">=";
	public final static String IN = "IN";	
	public final static String LIKE = "LIKE";
	public final static String MATCHES = "MATCHES";
	public final static String ISSUBSET = "ISSUBSET";
	public final static String ISSUPERSET = "ISSUPERSET";
	public final static String AND = "AND";
	public final static String OR = "OR";
}
