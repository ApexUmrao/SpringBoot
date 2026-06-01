/** ******************************************************************
 *    NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 *    Group                                     : Abu Dhabi Commercial Bank(ADCB)
 *    Product / Project                  	    : DMS REVAMP PROJECT
 *    Module                                  	: DOCUMENT EXTRACTION UTILITY
 *    File Name                               	: DocumentDetails.java
 *    Author                                    : Shivanshu Umrao
 *    Date written                          	: 14/Mar/2026
 *    (DD/MM/YYYY)
 *    Description                            	: Contains the methods for handling of DownloadDocument service
 *  CHANGE HISTORY
 ***********************************************************************************************
 * Date              ChangeID                  Change By                    Change Description (Bug No. (If Any))
 * (DD/MM/YYYY)
 *  07/03/2026       CHG00001                   Shivanshu Umrao              Contains the methods for parsing a XML file.
 *
 *********************************************************************************************** */
package com.newgen.connection;

public class XMLParser {

    private String parseString;
    private String copyString;
    private int IndexOfPrevSrch;

    public XMLParser() {
    }

    public XMLParser(String parseThisString) {
        copyString = new String(parseThisString);
        parseString = toUpperCase(copyString, 0, 0);
    }

    public void setInputXML(String ParseThisString) {
        if (ParseThisString != null) {
            copyString = new String(ParseThisString);
            parseString = toUpperCase(copyString, 0, 0);
            IndexOfPrevSrch = 0;
        } else {
            parseString = null;
            copyString = null;
            IndexOfPrevSrch = 0;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getServiceName
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : nothing
     *  Output Parameters                : nothing
     *  Return Values                    : nothing
     *  Description                      : getServiceName

     ************************************************************************/
    public String getServiceName() {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getServiceName");
        try {
            return new String(copyString.substring(parseString.indexOf(
                    toUpperCase(
                            "<Option>", 0, 0))
                    + (new String(toUpperCase("<Option>",
                            0, 0))).length(),
                    parseString.indexOf(toUpperCase(
                            "</Option>", 0, 0))));
        } catch (StringIndexOutOfBoundsException lExcp) {
            System.out.println(lExceptionId + " : " + lExcp.toString());
            throw lExcp;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getServiceName
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : chr
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getServiceName

     ************************************************************************/
    public String getServiceName(char chr) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getServiceName");
        try {
            if (chr == 'A') {
                return new String(copyString.substring(parseString.indexOf(
                        "<AdminOption>".toUpperCase())
                        + (new String("<AdminOption>".
                                toUpperCase())).length(),
                        parseString.indexOf(
                                "</AdminOption>".toUpperCase())));
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "NoServiceFound";
        }
    }

    
    /********************************************************************
     *  Function Name                    : validateXML
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : nothing
     *  Output Parameters                : nothing
     *  Return Values                    : boolean
     *  Description                      : validateXML

     ************************************************************************/
    public boolean validateXML() {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.validateXML");
        try {
            return parseString.indexOf("<?xml version=\"1.0\"?>".toUpperCase())
                    != -1;
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return false;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      :getValueOf

     ************************************************************************/
    public String getValueOf(String valueOf) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getValueOf");
        try {
            return new String(copyString.substring(parseString.indexOf("<"
                    + toUpperCase(valueOf, 0, 0) + ">") + valueOf.length() + 2,
                    parseString.
                            indexOf("</"
                                    + toUpperCase(valueOf, 0, 0) + ">")));
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf,type
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getValueOf

     ************************************************************************/
    public String getValueOf(String valueOf, String type) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getValueOf");
        try {
            if (type.equalsIgnoreCase("Binary")) {
                int startPos = copyString.indexOf("<" + valueOf + ">");
                if (startPos == -1) {
                    return "";
                } else {
                    int endPos = copyString.lastIndexOf("</" + valueOf + ">");
                    startPos += (new String("<" + valueOf + ">")).length();
                    return copyString.substring(startPos, endPos);
                }
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf,fromlast
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getValueOf

     ************************************************************************/
    public String getValueOf(String valueOf, boolean fromlast) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getValueOf");
        try {
            if (fromlast) {
                return new String(copyString.substring(parseString.indexOf("<"
                        + toUpperCase(valueOf, 0, 0) + ">") + valueOf.length()
                        + 2,
                        parseString.lastIndexOf("</"
                                + toUpperCase(valueOf, 0, 0)
                                + ">")));
            } else {
                return new String(copyString.substring(parseString.indexOf("<"
                        + toUpperCase(valueOf, 0, 0) + ">") + valueOf.length()
                        + 2,
                        parseString.indexOf("</"
                                + toUpperCase(valueOf, 0, 0) + ">")));
            }
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf,end
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getValueOf

     ************************************************************************/
    public String getValueOf(String valueOf, int start, int end) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getValueOf");
        try {
            if (start >= 0) {
                int endIndex = parseString.indexOf("</"
                        + toUpperCase(valueOf, 0, 0)
                        + ">", start);
                if (endIndex > start && (end == 0 || end >= endIndex)) {
                    return new String(copyString.substring(parseString.indexOf(
                            "<"
                            + toUpperCase(valueOf, 0, 0) + ">", start)
                            + valueOf.length() + 2,
                            endIndex));
                }
            }
            return "";
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getStartIndex
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : tag,start,end
     *  Output Parameters                : nothing
     *  Return Values                    : int
     *  Description                      : getStartIndex

     ************************************************************************/
    public int getStartIndex(String tag, int start, int end) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getStartIndex");
        try {
            if (start >= 0) {
                int startIndex = parseString.indexOf("<"
                        + toUpperCase(tag, 0, 0) + ">",
                        start);
                if (startIndex >= start && (end == 0 || end >= startIndex)) {
                    return startIndex + tag.length() + 2;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return -1;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getEndIndex
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : tag,start,end
     *  Output Parameters                : nothing
     *  Return Values                    : int
     *  Description                      : getEndIndex

     ************************************************************************/
    public int getEndIndex(String tag, int start, int end) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getEndIndex");
        try {
            if (start >= 0) {
                int endIndex = parseString.indexOf("</" + toUpperCase(tag, 0, 0)
                        + ">",
                        start);
                if (endIndex > start && (end == 0 || end >= endIndex)) {
                    return endIndex;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return -1;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getTagStartIndex
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : tag,start,end
     *  Output Parameters                : nothing
     *  Return Values                    : int
     *  Description                      : getTagStartIndex

     ************************************************************************/
    public int getTagStartIndex(String tag, int start, int end) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getTagStartIndex");
        try {
            if (start >= 0) {
                int startIndex = parseString.indexOf("<"
                        + toUpperCase(tag, 0, 0) + ">",
                        start);
                if (startIndex >= start && (end == 0 || end >= startIndex)) {
                    return startIndex;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return -1;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getTagEndIndex
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : tag,start,end
     *  Output Parameters                : nothing
     *  Return Values                    : int
     *  Description                      : getTagEndIndex

     ************************************************************************/
    public int getTagEndIndex(String tag, int start, int end) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getTagEndIndex");
        try {
            if (start >= 0) {
                int endIndex = parseString.indexOf("</" + toUpperCase(tag, 0, 0)
                        + ">",
                        start);
                if (endIndex > start && (end == 0 || end >= endIndex)) {
                    return endIndex + tag.length() + 3;
                }
            }
            return -1;
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return -1;
        }
    }

    
    /********************************************************************
     *  Function Name                    : getFirstValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getFirstValueOf

     ************************************************************************/
    public String getFirstValueOf(String valueOf) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getFirstValueOf");
        try {
            IndexOfPrevSrch = parseString.indexOf("<" + toUpperCase(valueOf, 0, 0) + ">");
            return new String(copyString.substring(IndexOfPrevSrch
                    + valueOf.length()
                    + 2,
                    parseString.indexOf("</"
                            + toUpperCase(valueOf, 0, 0) + ">")));
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getFirstValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf,start
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      :getFirstValueOf

     ************************************************************************/
    public String getFirstValueOf(String valueOf, int start) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getFirstValueOf");
        try {
            IndexOfPrevSrch = parseString.indexOf("<"
                    + toUpperCase(valueOf, 0, 0)
                    + ">", start);
            return new String(copyString.substring(IndexOfPrevSrch
                    + valueOf.length()
                    + 2,
                    parseString.indexOf("</"
                            + toUpperCase(valueOf, 0, 0) + ">", start)));
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getNextValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getNextValueOf

     ************************************************************************/
    public String getNextValueOf(String valueOf) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getNextValueOf");
        try {
            IndexOfPrevSrch = parseString.indexOf("<"
                    + toUpperCase(valueOf, 0, 0)
                    + ">",
                    IndexOfPrevSrch
                    + valueOf.length()
                    + 2);
            return new String(copyString.substring(IndexOfPrevSrch
                    + valueOf.length()
                    + 2,
                    parseString.indexOf("</"
                            + toUpperCase(valueOf, 0, 0) + ">",
                            IndexOfPrevSrch)));
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : getNoOfFields
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : tag
     *  Output Parameters                : nothing
     *  Return Values                    : int
     *  Description                      : getNoOfFields

     ************************************************************************/
    public int getNoOfFields(String tag) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getNoOfFields");
        int noOfFields = 0;
        int beginPos = 0;
        try {
            for (tag = toUpperCase(tag, 0, 0) + ">";
                    parseString.indexOf("<" + tag, beginPos) != -1;
                    beginPos += tag.length() + 2) {
                noOfFields++;
                beginPos = parseString.indexOf("</" + tag, beginPos);
                if (beginPos == -1) {
                    break;
                }
            }

        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
        }
        return noOfFields;
    }

    
    /********************************************************************
     *  Function Name                    : getNoOfFields
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : tag,startPos,endPos
     *  Output Parameters                : nothing
     *  Return Values                    : int
     *  Description                      : getNoOfFields

     ************************************************************************/
    public int getNoOfFields(String tag, int startPos, int endPos) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getNoOfFields");
        int noOfFields = 0;
        int beginPos = startPos;
        try {
            for (tag = toUpperCase(tag, 0, 0) + ">";
                    parseString.indexOf("<" + tag, beginPos) != -1
                    && (beginPos < endPos || endPos == 0);) {
                beginPos = parseString.indexOf("</" + tag, beginPos)
                        + tag.length() + 2;
                if (beginPos != -1 && (beginPos <= endPos || endPos == 0)) {
                    noOfFields++;
                }
            }

        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
        }
        return noOfFields;
    }

    
    /********************************************************************
     *  Function Name                    : convertToSQLString
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : strName
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : convertToSQLString

     ************************************************************************/
    public String convertToSQLString(String strName) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.convertToSQLString");
        try {
            for (int count = strName.indexOf("["); count != -1;
                    count = strName.indexOf("[", count + 2)) {
                strName = strName.substring(0, count) + "[[]"
                        + strName.substring(count + 1, strName.length());

            }
        } catch (Exception lobjExcp) {
            System.out.println(lExceptionId + " : " + lobjExcp.toString());
        }
        try {
            for (int count = strName.indexOf("_"); count != -1;
                    count = strName.indexOf("_", count + 2)) {
                strName = strName.substring(0, count) + "[_]"
                        + strName.substring(count + 1, strName.length());

            }
        } catch (Exception lobjExcp1) {
            System.out.println(lExceptionId + " : " + lobjExcp1.toString());
        }
        try {
            for (int count = strName.indexOf("%"); count != -1;
                    count = strName.indexOf("%", count + 2)) {
                strName = strName.substring(0, count) + "[%]"
                        + strName.substring(count + 1, strName.length());

            }
        } catch (Exception lobjExcp2) {
            System.out.println(lExceptionId + " : " + lobjExcp2.toString());
        }
        strName = strName.replace('?', '_');
        return strName;
    }

    
    /********************************************************************
     *  Function Name                    : getValueOf
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf,type,from,end
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : getValueOf

     ************************************************************************/
    public String getValueOf(String valueOf, String type, int from, int end) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.getNoOfFields");
        try {
            if (type.equalsIgnoreCase("Binary")) {
                int startPos = copyString.indexOf("<" + valueOf + ">", from);
                if (startPos == -1) {
                    return "";
                }
                int endPos = copyString.indexOf("</" + valueOf + ">", from);
                if (endPos > end) {
                    return "";
                } else {
                    startPos += (new String("<" + valueOf + ">")).length();
                    return copyString.substring(startPos, endPos);
                }
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException lStringIOBExcp) {
            System.out.println(lExceptionId + " : " + lStringIOBExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : toUpperCase
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : valueOf,begin,end
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : toUpperCase

     ************************************************************************/
    public String toUpperCase(String valueOf, int begin, int end) throws
            StringIndexOutOfBoundsException {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.toUpperCase");
        String returnStr = "";
        try {
            int count = valueOf.length();
            char strChar[] = new char[count];
            valueOf.getChars(0, count, strChar, 0);
            while (count-- > 0) {
                strChar[count] = Character.toUpperCase(strChar[count]);
            }
            returnStr = new String(strChar);
        } catch (ArrayIndexOutOfBoundsException lobjArrayIOBExcp) {
            System.out.println(lExceptionId + " : " + lobjArrayIOBExcp.toString());
        }
        return returnStr;
    }

    
    /********************************************************************
     *  Function Name                    : changeValue
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : ParseString,TagName,NewValue
     *  Output Parameters                : nothing
     *  Return Values                    : String
     *  Description                      : changeValue

     ************************************************************************/
    public String changeValue(String ParseString, String TagName,
            String NewValue) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.changeValue");
        try {
            String ParseStringTmp = ParseString.toUpperCase();
            String StrTag = (new String("<" + TagName + ">")).toUpperCase();
            int StartIndex = ParseStringTmp.indexOf(StrTag) + StrTag.length();
            int EndIndex = ParseStringTmp.indexOf((new String("</" + TagName + ">")).toUpperCase());
            String RetStr = ParseString.substring(0, StartIndex);
            RetStr = RetStr + NewValue + ParseString.substring(EndIndex);
            return RetStr;
        } catch (Exception lobjExcp) {
            System.out.println(lExceptionId + " : " + lobjExcp.toString());
            return "";
        }
    }

    
    /********************************************************************
     *  Function Name                    : changeValue
     *  Date Written                     : 03-Mar-2025
     *  Author                           : KS.SivaShankar@DIB.AE
     *  Input Parameters                 : TagName,NewValue
     *  Output Parameters                : nothing
     *  Return Values                    : void
     *  Description                      : changeValue

     ************************************************************************/
    public void changeValue(String TagName, String NewValue) {
        String lExceptionId = new String("com.newgen.srvr.xml.XMLParser.changeValue");
        try {
            String StrTag = ("<" + TagName + ">").toUpperCase();
            int StartIndex = parseString.indexOf(StrTag);
            if (StartIndex > -1) {
                StartIndex += StrTag.length();
                int EndIndex = parseString.indexOf(("</" + TagName + ">").
                        toUpperCase());
                String RetStr = copyString.substring(0, StartIndex);
                copyString = RetStr + NewValue + copyString.substring(EndIndex);
            } else {
                int EndIndex = StartIndex = parseString.lastIndexOf("</");
                String RetStr = copyString.substring(0, StartIndex);
                copyString = RetStr + "<" + TagName + ">" + NewValue + "</"
                        + TagName
                        + ">" + copyString.substring(EndIndex);
            }
            parseString = toUpperCase(copyString, 0, 0);
        } catch (Exception lobjExcp) {
            System.out.println(lExceptionId + " : " + lobjExcp.toString());
        }
    }

    public String toString() {
        return copyString;
    }
}
