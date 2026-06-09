package com.newgen.iforms.user;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test {
@SuppressWarnings("unchecked")

public static void main(String[] args) {
 String amount="28888";
 String adminfee="1.5";
 double adminFeeAmount=(Double.parseDouble(amount)*Double.parseDouble(adminfee))/100;
	System.out.println(String.format("%.2f", adminFeeAmount));
}
}