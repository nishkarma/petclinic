package org.nishkarma.book.model;

public class CoverConverter {

	public static String convertToDatabaseColumn(Cover attribute) {
		switch (attribute) {
		case DUST_JACKET:
			return "D";
		case HARDCOVER:
			return "H";
		case PAPERBACK:
			return "P";
		default:
			throw new IllegalArgumentException("Unknown" + attribute);
		}
	}

	public static Cover convertToEntityAttribute(String dbData) {
		switch (dbData) {
		case "D":
			return Cover.DUST_JACKET;
		case "H":
			return Cover.HARDCOVER;
		case "P":
			return Cover.PAPERBACK;
		default:
			throw new IllegalArgumentException("Unknown" + dbData);
		}
	}
}