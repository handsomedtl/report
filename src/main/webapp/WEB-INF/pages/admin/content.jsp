<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="/resources/js/jquery-ui-1.12.0/jquery-ui.min.css" />
<script src="/resources/js/jquery-ui-1.12.0/external/jquery/jquery.js"></script>
<script src="/resources/js/jquery-ui-1.12.0/jquery-ui.min.js"></script>
<link rel="stylesheet" href="/resources/js/pggrid/css/pqgrid.min.css">
<script src="/resources/js/pggrid/js/pqgrid.min.js"></script>
<link rel="stylesheet"
	href="/resources/js/jstree/assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/resources/js/jstree/assets/dist/themes/default/style.min.css" />
	<!-- 
<script src="/resources/js/jstree/assets/jquery.address-1.6.js"></script>
 -->
<script src="/resources/js/jstree/assets/vakata.js"></script>
<script src="/resources/js/jstree/assets/dist/jstree.min.js"></script>

<title>右侧内容</title>
</head>
<body>
	<div id="grid_php" style="margin: 5px auto;"></div>
	<div id="jstree2" class="demo" style="margin-top:2em;"></div>
	<script class="ppjs">
    $(function () {
        var colM = [
            { title: "排序列", width: 100, dataIndx: "OrderID" },            
            { title: "客户名", width: 130, dataIndx: "CustomerName" },
            { title: "Product Name", width: 190, dataIndx: "ProductName" },
            { title: "Unit Price", width: 100, dataIndx: "UnitPrice", align: "right" },
            { title: "Quantity", width: 100, dataIndx: "Quantity", align:"right" },            
            { title: "Order Date", width: 100, dataIndx: "OrderDate"},
            { title: "Required Date", width: 100, dataIndx: "RequiredDate" },
            { title: "Shipped Date", width: 100, dataIndx: "ShippedDate" },
            { title: "ShipCountry", width: 100, dataIndx: "ShipCountry" },
            { title: "Freight", width: 100, align: "right", dataIndx: "Freight" },
            { title: "Shipping Name", width: 120, dataIndx: "ShipName" },
            { title: "Shipping Address", width: 180, dataIndx: "ShipAddress" },
            { title: "Shipping City", width: 100, dataIndx: "ShipCity" },
            { title: "Shipping Region", width: 110, dataIndx: "ShipRegion" },
            { title: "Shipping Postal Code", width: 130, dataIndx: "ShipPostalCode" }
        ];
        var dataModel = {
            location: "remote",            
            paging: "remote",
            curPage: 1,
            dataType: "JSON",
            method: "POST",
            //url :"remote.php",
            getUrl: function(ui){
                return {
                    url: "",
                    data:{
                        pq_rpp:this.rPP,
                        pq_curpage:this.curPage
                    }
                }
            },
            getData: function ( dataJSON ) {                
                return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
            }
        }

        var grid1 = $("div#grid_php")
                .pqGrid({ width: 900, height: 400,
            dataModel: dataModel,
            sortable: false,
            colModel: colM,            
            title: "测试表格"
        });
    });
    
    $(function () {
		$('#jstree2').jstree({'plugins':["wholerow","checkbox"], 'core' : {
			'data' : [
				{
					"text" : "Same but with checkboxes",
					"children" : [
						{ "text" : "initially selected", "state" : { "selected" : true } },
						{ "text" : "custom icon URL", "icon" : "http://jstree.com/tree-icon.png" },
						{ "text" : "initially open", "state" : { "opened" : true }, "children" : [ "Another node" ] },
						{ "text" : "custom icon class", "icon" : "glyphicon glyphicon-leaf" }
					]
				},
				"And wholerow selection"
			]
		}});
	});

</script>
</body>
</html>