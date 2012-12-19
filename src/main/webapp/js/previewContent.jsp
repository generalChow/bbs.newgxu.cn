<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ page language="java"%>
<%@ page import="cn.newgxu.bbs.common.filter.*"%>
<%
    out.clear();                                    //清空当前的输出内容（空格和换行符）
    out.print("<style type=\"text/css\"><!--\r\nbody{font-size: 14px;color: #333333;background-color: #FFFBE0;}\r\n--></style>");
    out.print("<body>");
    out.print(FilterUtil.ubb(request.getParameter("content")));//将获取的结果输出到响应体
    out.print("</body>");
%>