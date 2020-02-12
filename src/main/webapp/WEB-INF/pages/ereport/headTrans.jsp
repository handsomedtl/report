<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
</head>
<body>
<div>
<form id="trans" action="trans.jhtml">
  heads: <textarea  name="heads" rows="10" cols="110" ></textarea>
  <br/>
  fields: <textarea name="fields" rows="10" cols="110"></textarea>
  <input type="submit" value="TRANS" />
</form>
</div>
<div>
  transheads: <textarea rows="10" cols="110" >${heads}</textarea>
  <br/>
  transfields: <textarea rows="10" cols="110" >${frozenheads}</textarea>
</div>
</body>
</html>