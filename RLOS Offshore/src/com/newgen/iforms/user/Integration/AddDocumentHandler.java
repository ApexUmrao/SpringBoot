package com.newgen.iforms.user.Integration;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class AddDocumentHandler extends DefaultHandler {

	private static final String TAG_STATUS = "STATUS";
	private static final String TAG_DOCUMENTINDEX = "DOCUMENTINDEX";
	private static final String TAG_DOCUMENTNAME = "DOCUMENTNAME";
	private static final String TAG_ISINDEX = "ISINDEX";
	private static final String TAG_NOOFPAGES = "NOOFPAGES";
	private static final String TAG_DOCUMENTSIZE = "DOCUMENTSIZE";
	private static final String TAG_CREATIONDATETIME = "CREATIONDATETIME";
	private static final String TAG_OWNERINDEX = "OWNERINDEX";
	private static final String TAG_OWNER = "OWNER";
	private static final String TAG_REVISEDDATETIME = "REVISEDDATETIME";
	private static final String TAG_CREATEDBYAPPNAME = "CREATEDBYAPPNAME";

	private StringBuffer sb = null;

	private String status;
	private final StringBuilder tempVal = new StringBuilder();

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		tempVal.setLength(0);
		if (TAG_STATUS.equalsIgnoreCase(qName)) {
			sb = new StringBuffer();
		}
	}

	public void characters(char ch[], int start, int length) {
		tempVal.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) {
		if (TAG_STATUS.equalsIgnoreCase(qName)) {
			status = tempVal.toString();
			sb.append("<Record><ErrorCode>");
			sb.append(status);
			sb.append("</ErrorCode><ErrorDesc>TEMPLATE GENERATED SUCCESSFULLY.</ErrorDesc>");
		} else if (TAG_DOCUMENTINDEX.equalsIgnoreCase(qName)) {
			sb.append("<DocumentIndex>");
			sb.append(tempVal.toString());
			sb.append("</DocumentIndex>");
		} else if (TAG_DOCUMENTNAME.equalsIgnoreCase(qName)) {
			sb.append("<DocumentName>");
			sb.append(tempVal.toString());
			sb.append("</DocumentName>");
		} else if (TAG_ISINDEX.equalsIgnoreCase(qName)) {
			sb.append("<ISIndex>");
			sb.append(tempVal.toString());
			sb.append("</ISIndex>");
		} else if (TAG_NOOFPAGES.equalsIgnoreCase(qName)) {
			sb.append("<NoOfPages>1</NoOfPages><AnnotationFlag></AnnotationFlag><DocumentType>N</DocumentType>");
		} else if (TAG_CREATEDBYAPPNAME.equalsIgnoreCase(qName)) {
			sb.append("<CreatedByAppName>");
			sb.append(tempVal.toString());
			sb.append("</CreatedByAppName>");
		} else if (TAG_CREATIONDATETIME.equalsIgnoreCase(qName)) {
			sb.append("<CreationDateTime>");
			sb.append(tempVal.toString());
			sb.append(
					"</CreationDateTime><VersionFlag>N</VersionFlag><DocumentVersionNo>1.0</DocumentVersionNo><CheckoutStatus>N</CheckoutStatus><CheckoutBy></CheckoutBy><Comment></Comment>");
		} else if (TAG_OWNERINDEX.equalsIgnoreCase(qName)) {
			sb.append("<OwnerIndex>");
			sb.append(tempVal.toString());
			sb.append("</OwnerIndex>");
		} else if (TAG_DOCUMENTSIZE.equalsIgnoreCase(qName)) {
			sb.append("<DocumentSize>");
			sb.append(tempVal.toString());
			sb.append("</DocumentSize>");
		} else if (TAG_OWNER.equalsIgnoreCase(qName)) {
			sb.append("<Owner>");
			sb.append(tempVal.toString());
			sb.append("</Owner>");
		} else if (TAG_REVISEDDATETIME.equalsIgnoreCase(qName)) {
			sb.append("<RevisedDateTime>");
			sb.append(tempVal.toString());
			sb.append("</RevisedDateTime>");
		}
	}

	public String getOutputString() {
		if (sb != null) {
			sb.append("</Record>");
			return sb.toString();
		} else
			return null;
	}

	public String getStatus() {
		return status;
	}
}