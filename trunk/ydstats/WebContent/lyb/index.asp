<%
'#################################################################
'# �������ƣ���ң���Ա� V1.0
'# ��Ȩ��ʽ��������
'# ���ߣ���ң����
'# ��ʾ������֧�֣�http://www.buyok.net/xybook
'# �������䣺buyok@buyok.net
'# ����ʱ�䣺2005-4-8
'# ����������������ң���Ӷ��������������������а�Ȩ��
'# ������;�������ʹ�á����ɴ����������뱣����������еİ�Ȩ��Ϣ��
'#################################################################

if request("keywords")<>"" then
if checktxt(request("keywords"))<>request("keywords") then
	response.write "<script language='javascript'>"
	response.write "alert('������������ؼ��к��зǷ��ַ���������������룡');"
	response.write "location.href='javascript:history.go(-1)';"							
	response.write "</script>"	
	response.end
end if
end if
%>
<!--#include file="book_conn.asp"-->



<HTML><HEAD>
<TITLE><%=sitename%></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta name="description" content="<%=sitename%>">
<meta name="keywords" content="<%=sitename%>">
<link rel="stylesheet" href="../font.css" type="text/css">

</HEAD>
<center>
  <!--#include file="book_top1.asp"-->
  <table width="980" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td colspan="2" height="5"></td>
    </tr>
    <tr bgcolor="ECECEC">
      <td colspan="2" height="10"></td>
    </tr>
    <tr>
      <td width="230" bgcolor="ECECEC" height="450" valign="top"><!--#include file="left.asp" --></td>
      <td align="center" valign="top">
        <table width="674" border="0" cellpadding="0" cellspacing="0">
          <tr>            </tr>
          <tr>
            <td><img src="IMAGES/lyb.gif" width="698" height="57"></td>
          </tr>
          <tr>
            <td align="center"><table width="90%"  border="0" cellspacing="2" cellpadding="2">
                <tr>
                  <td height="25" valign="bottom">&nbsp; </td>
                </tr>
                <tr>
                  <td>�����װ������ѣ����ȷǳ���ӭ�������а����������޹�˾��ϣ��ͨ����վ���������и�����˽⣬������Ҫ�˽�������Ϣ�����߶Ա���˾��ʲô��������飬�����������ǣ����ǽ������ǵĹ������Ը��ƣ�ͬʱлл���Ա���վ�Ĺ�����֧�֡� ���������ǹ�˾�и�����������������д���µķ�����Ϣ������һ�����ڵ�һʱ�����������⡣</td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="90%" border=0 cellspacing=0 cellpadding=0 align=center bgcolor="F6F6F8">
                <tr>
                  <td align=center height=25>&nbsp;</td>
                </tr>
                <tr>
                  <td align=center height=25><a href=book_write.asp><img border=0 src=images/write.gif title="��Ҫд����"></a> &nbsp;&nbsp;&nbsp;&nbsp; <a href=index.asp><img border=0 src=images/read.gif title="���Ա���ҳ"></a> </td>
                </tr>
                <tr>
                  <td>
                    <%
set rs=Server.CreateObject("ADODB.RecordSet")
sql="select * from Feedback where online='1' "
keywords=request("keywords")
if keywords<>"" then sql=sql+ " and Comments like '%"&keywords&"%' "
sql=sql + "order by top desc,Postdate desc"
rs.open sql,conn,1
if not (rs.eof and rs.bof) then			'���������ʱ������ʾ���ԡ����е�if�뵹����6�е�end if���Ӧ

if pages=0 or pages="" then pages=10		'ÿҳ��������
rs.pageSize = pages	'ÿҳ��¼��
allPages = rs.pageCount	'��ҳ��
page = Request("page")	'�������ȡ�õ�ǰҳ
'if�ǻ����ĳ�����

If not isNumeric(page) then page=1

if isEmpty(page) or Cint(page) < 1 then
page = 1
elseif Cint(page) >= allPages then
page = allPages 
end if
rs.AbsolutePage = page			'ת��ĳҳͷ��
	Do While Not rs.eof and pages>0
	UserName=rs("UserName")		'�û���
	pic=rs("pic")			'ͷ��
	face=rs("face")			'����
	Comments=rs("Comments")		'����
	bad1=split(bad,"/")		'�����໰
	for t=0 to ubound(bad1)
	Comments=replace(Comments,bad1(t),"***")
	next
	Replay=rs("Replay")		'�ظ�
	Usermail=rs("Usermail")		'�ʼ�
	url=rs("url")			'��ҳ
	I=I+1				'���
	temp=RS.RecordCount-(page-1)*rs.pageSize-I+1
	%>
                    <table width="100%" border="0" align="center" cellPadding="3" cellSpacing="1" style="word-break:break-all">
                      <tr>
                        <td vAlign="top" bgColor="#f7f7f7" rowSpan="2" align=center>
                          <table border=0 width=85%>
                            <tr>
                              <td align=center><img src=images/face/pic<%=pic%>.gif border=0></td>
                            </tr>
                            <tr>
                              <td>������<%=UserName%><br>
                        ���ԣ�<%=left(rs("ip"),(len(rs("ip"))-2))+"**"%><br>
                        �ʼ���<a href=mailto:<%=Usermail%>><img src=images/mail.gif border=0 alt="<%=Usermail%>"></a><br>
                        ��ҳ��<a href="<%=URL%>" target='_blank'><img src=images/home.gif border=0 alt="<%=URL%>"></a></td>
                            </tr>
                        </table></td>
                        <td bgColor="F6F6F8"><%if rs("top")<>"1" then response.write "[NO."&temp&"]"%>
                            <img border=0 src=images/face/face<%=face%>.gif> �����ڣ�<%=cstr(rs("Postdate"))%></td>
                      </tr>
                      <tr>
                        <td vAlign="top" bgColor="#ebebeb" onMouseOver="bgColor='#FFffff'" onMouseOut="bgColor='#ebebeb'">
                          <%
	'�Ƿ��������������е�html�ַ�
	if html=0 then
	response.write replace(server.htmlencode(Comments),vbCRLF,"<BR>")
	else
	response.write replace(Comments,vbCRLF,"<BR>")
	end if
	%>
                          <br>
                          <br>
                          <%if rs("Replay")<>"" then%>
                          <table cellSpacing="1" cellPadding="3" width="90%" align="center" bgColor="darkgray" border="0">
                            <tr>
                              <td vAlign="top" bgColor="F6F6F8"> <font color=<%=huifucolor%>><%=huifutishi%>��<br>
                                    <%=Replay%></font> </td>
                            </tr>
                          </table>
                          <br>
                          <%end if%>
                        </td>
                      </tr>
                    </table>
                    <table cellSpacing="0" cellPadding="0"  align="center" bgColor="#FFFFFF" border="0">
                      <TR>
                        <TD height=8> </TD>
                      </TR>
                    </TABLE>
                    <%
pages = pages - 1
rs.movenext
if rs.eof then exit do
loop

response.write "<table border=0 align=center><tr><td><form action='index.asp' method='post'>�ܼ�����"&RS.RecordCount&"�� "
if page = 1 then
response.write "<font color=darkgray>��ҳ ǰҳ</font>"
else
response.write "<a href=index.asp?page=1>��ҳ</a> <a href=index.asp?keywords="&keywords&"&page="&page-1&">ǰҳ</a>"
end if
if page = allpages then
response.write "<font color=darkgray> ��ҳ ĩҳ</font>"
else
response.write " <a href=index.asp?keywords="&keywords&"&page="&page+1&">��ҳ</a> <a href=index.asp?keywords="&keywords&"&page="&allpages&">ĩҳ</a>"
end if
response.write " ��"&page&"ҳ ��"&allpages&"ҳ &nbsp; ת���� "
response.write "<select name='page'>"
for i=1 to allpages
response.write "<option value="&i&">"&i&"</option>"
next
response.write "</select> ҳ <input type=submit name=go value='Go'><input type=hidden name=keywords value='"&keywords&"'></form></td><td align=right>"
response.write "<form action='index.asp' method='post'><input title='�����ʲô����' type=text name=keywords value='"&keywords&"' size=10 maxlength=20><input type=submit value='����' title='������'></form></td></tr></table>"


else
response.write "<table cellSpacing=0 cellPadding=0  align=center bgColor=#FFFFFF border=0><TR><TD height=120 align=center>"
if keywords="" then response.write "��ʱû������" else response.write "��Ǹ��û���ҵ���Ҫ�鵽������<br><br><a href='javascript:history.go(-1)'>������һҳ</a>" end if
response.write "</TD></TR></TABLE>"
end if
%>
                  </td>
                </tr>
            </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="1" bgcolor="#CCCCCC"></td>
  </tr>
</table>
<!--#include file="book_down.asp"-->
</center>

</html>