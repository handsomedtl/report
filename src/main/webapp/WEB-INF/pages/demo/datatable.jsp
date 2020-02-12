<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../public/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 通过自定义标签加载系统使用的css 和js 资源 如果是自定义资源请自己写全路径 -->
<asiainfo:resource type="Bootstrap,datatables,jquery"></asiainfo:resource>
<script src="${ctx}/table-builder/datatable.jhtml?tableid=mytable"></script>
<%-- <script src="${ctx}/resources/gentelella/js/datatables/js/test2.js"></script>
 --%>
<script type="text/javascript">
$(function(){
	$(".fa-collapse").each(function(){
		//alert($(this));
		
		var t =$(this).attr("collapse");
		$(this).on('click',function(){
			$(this).toggleClass('fa-angle-double-up').toggleClass('fa-angle-double-down');
			$("#"+t).slideToggle("slow");
			
		})
		
	})
})
</script>
</head>
<body style="background: #fff;">
	<div class="container">
		<div class="main_container right_col">			
            <form id = "form1" class="form-horizontal form-label-left">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="search clearfix">
                            <div class="form-up">
                                <a href="javascript:void()">
                                    <i class="fa fa-collapse fa-angle-double-up" collapse="search-form"></i>
                                    <!--<i class="fa fa-angle-double-up"></i>--> <!--点击后向上-->
                                </a>
                            </div>
                            <div id="search-form">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">element_id：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">普通下拉：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control">
                                            <option>Choose option</option>
                                            <option>Option one</option>
                                            <option>Option two</option>
                                            <option>Option three</option>
                                            <option>Option four</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group m-b0">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">选择下拉列</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <select class="select2_group form-control">
                                            <optgroup label="Alaskan/Hawaiian Time Zone">
                                                <option value="AK">Alaska</option>
                                                <option value="HI">Hawaii</option>
                                            </optgroup>
                                            <optgroup label="Pacific Time Zone">
                                                <option value="CA">California</option>
                                                <option value="NV">Nevada</option>
                                                <option value="OR">Oregon</option>
                                                <option value="WA">Washington</option>
                                            </optgroup>
                                            
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">查询文本框：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <div class="input-group input-mb">
                                            <input class="form-control" type="text">
                                            <div class="input-group-btn">                        
                                                <span class="btn-search">
                                                    <a href="#"><i class="fa fa-search"></i></a>
                                                </span>                                                  
                                            </div>
                                            <div class="input-group-btn"> 
                                                <span class="btn-del">
                                                    <a href="#"><i class="fa fa-times-circle"></i></a>
                                                </span>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-8 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-2 col-sm-4 col-xs-12">日 期：</label>
                                    <div class="col-md-5 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                    <div class="col-md-5 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div> 
                            
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-lg-1 col-md-1 col-md3-1 col-sm-2 col-xs-12">element_id：</label>
                                    <div class="col-lg-11 col-md-11 col-md3-11  col-sm-10 col-xs-12">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                                   
                            <button class="btn btn-success select-btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                            <button type="button" class="btn btn-info f-r">清 空</button>
                        </div>
                        </div>
                    </div>
                </div>
                
                
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="search clearfix">
                            <div class="form-up">
                                <a href="javascript:void()">
                                    <i class="fa fa-collapse fa-angle-double-up" collapse="search-form"></i>
                                    <!--<i class="fa fa-angle-double-up"></i>--> <!--点击后向上-->
                                </a>
                            </div>
                            <div id="search-form">
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">element_id：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">普通下拉：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control">
                                            <option>Choose option</option>
                                            <option>Option one</option>
                                            <option>Option two</option>
                                            <option>Option three</option>
                                            <option>Option four</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group m-b0">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">选择下拉列</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <select class="select2_group form-control">
                                            <optgroup label="Alaskan/Hawaiian Time Zone">
                                                <option value="AK">Alaska</option>
                                                <option value="HI">Hawaii</option>
                                            </optgroup>
                                            <optgroup label="Pacific Time Zone">
                                                <option value="CA">California</option>
                                                <option value="NV">Nevada</option>
                                                <option value="OR">Oregon</option>
                                                <option value="WA">Washington</option>
                                            </optgroup>
                                            <optgroup label="Mountain Time Zone">
                                                <option value="AZ">Arizona</option>
                                                <option value="CO">Colorado</option>
                                                <option value="ID">Idaho</option>
                                                <option value="MT">Montana</option>
                                            </optgroup>
                                            <optgroup label="Central Time Zone">
                                                <option value="AL">Alabama</option>
                                                <option value="AR">Arkansas</option>
                                                <option value="IL">Illinois</option>
                                                <option value="IA">Iowa</option>
                                                <option value="KS">Kansas</option>
                                                <option value="KY">Kentucky</option>
                                                <option value="LA">Louisiana</option>
                                            </optgroup>
                                            <optgroup label="Eastern Time Zone">
                                                <option value="CT">Connecticut</option>
                                                <option value="DE">Delaware</option>
                                                <option value="FL">Florida</option>
                                                <option value="GA">Georgia</option>
                                                <option value="IN">Indiana</option>
                                                <option value="ME">Maine</option>
                                            </optgroup>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">查询文本框：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <div class="input-group input-mb">
                                            <input class="form-control" type="text">
                                            <div class="input-group-btn">                        
                                                <span class="btn-search">
                                                    <a href="#"><i class="fa fa-search"></i></a>
                                                </span>                                                  
                                            </div>
                                            <div class="input-group-btn"> 
                                                <span class="btn-del">
                                                    <a href="#"><i class="fa fa-times-circle"></i></a>
                                                </span>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">日 期：</label>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>        
                            <button class="btn btn-success select-btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                            <button type="button" class="btn btn-info f-r">清 空</button>
                        </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="search clearfix">
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">element_id：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">普通下拉：</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <select class="form-control">
                                            <option>Choose option</option>
                                            <option>Option one</option>
                                            <option>Option two</option>
                                            <option>Option three</option>
                                            <option>Option four</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="form-group m-b0">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">选择下拉列</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <select class="select2_group form-control">
                                            <optgroup label="Alaskan/Hawaiian Time Zone">
                                                <option value="AK">Alaska</option>
                                                <option value="HI">Hawaii</option>
                                            </optgroup>
                                            <optgroup label="Pacific Time Zone">
                                                <option value="CA">California</option>
                                                <option value="NV">Nevada</option>
                                                <option value="OR">Oregon</option>
                                                <option value="WA">Washington</option>
                                            </optgroup>
                                            <optgroup label="Mountain Time Zone">
                                                <option value="AZ">Arizona</option>
                                                <option value="CO">Colorado</option>
                                                <option value="ID">Idaho</option>
                                                <option value="MT">Montana</option>
                                            </optgroup>
                                            <optgroup label="Central Time Zone">
                                                <option value="AL">Alabama</option>
                                                <option value="AR">Arkansas</option>
                                                <option value="IL">Illinois</option>
                                                <option value="IA">Iowa</option>
                                                <option value="KS">Kansas</option>
                                                <option value="KY">Kentucky</option>
                                                <option value="LA">Louisiana</option>
                                            </optgroup>
                                            <optgroup label="Eastern Time Zone">
                                                <option value="CT">Connecticut</option>
                                                <option value="DE">Delaware</option>
                                                <option value="FL">Florida</option>
                                                <option value="GA">Georgia</option>
                                                <option value="IN">Indiana</option>
                                                <option value="ME">Maine</option>
                                            </optgroup>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-4 col-xs-12">查询文本框：</label>
                                    <div class="col-md-9 col-sm-8 col-xs-12">
                                        <div class="input-group input-mb">
                                            <input class="form-control" type="text">
                                            <div class="input-group-btn">                        
                                                <span class="btn-search">
                                                    <a href="#"><i class="fa fa-search"></i></a>
                                                </span>                                                  
                                            </div>
                                            <div class="input-group-btn"> 
                                                <span class="btn-del">
                                                    <a href="#"><i class="fa fa-times-circle"></i></a>
                                                </span>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2"></div>
                            <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">日 期：</label>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <button class="btn btn-success select-btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                            <button type="button" class="btn btn-info f-r">清 空</button>
                        </div>
                     </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="search clearfix">
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">element_id：</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">普通下拉：</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <select class="form-control">
                                            <option>Choose option</option>
                                            <option>Option one</option>
                                            <option>Option two</option>
                                            <option>Option three</option>
                                            <option>Option four</option>
                                        </select>
                                    </div>
                                </div>
                            </div>        
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">查询文本框：</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <div class="input-group input-mb">
                                            <input class="form-control" type="text">
                                            <div class="input-group-btn">                        
                                                <span class="btn-search">
                                                    <a href="#"><i class="fa fa-search"></i></a>
                                                </span>                                                  
                                            </div>
                                            <div class="input-group-btn"> 
                                                <span class="btn-del">
                                                    <a href="#"><i class="fa fa-times-circle"></i></a>
                                                </span>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">日 期：</label>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                    <div class="col-md-1 col-sm-1">
                                    </div>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <button class="btn btn-success select-btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                            <button type="button" class="btn btn-info f-r">清 空</button>
                            <button type="button" class="btn btn-dark f-r">不可用</button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="search clearfix">
                            <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">element_id：</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">普通下拉：</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <select class="form-control">
                                            <option>Choose option</option>
                                            <option>Option one</option>
                                            <option>Option two</option>
                                            <option>Option three</option>
                                            <option>Option four</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2 hidden-sm hidden-xs group-center">
                                <div class="form-group">
                                    <button type="button" class="btn btn-info">清 空</button>
                                </div>
                            </div>     
                            <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">查询文本框：</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <div class="input-group input-mb">
                                            <input class="form-control" type="text">
                                            <div class="input-group-btn">                        
                                                <span class="btn-search">
                                                    <a href="#"><i class="fa fa-search"></i></a>
                                                </span>                                                  
                                            </div>
                                            <div class="input-group-btn"> 
                                                <span class="btn-del">
                                                    <a href="#"><i class="fa fa-times-circle"></i></a>
                                                </span>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">日 期：</label>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                    <div class="col-md-1 col-sm-1">
                                    </div>
                                    <div class="col-md-4 col-sm-4 col-xs-6 form-group">
                                        <input name="element_id" class="form-control" type="text" placeholder="Default Input">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2 hidden-sm hidden-xs">
                                <div class="form-group  group-center">
                                    <button class="btn btn-success" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>            	
                                </div>
                            </div>
                            <div class="hidden-lg hidden-md">            
                                <button class="btn btn-success select-btn-r" type="button"  onclick="javascript:$('#mytable').DataTable().refreshtable();">执行查询</button>
                                <button type="button" class="btn btn-info f-r">清 空</button>
                            </div> 
                        </div>
                    </div>
                </div>
                <div class="row">                    
                    <!--上传附件列表 begin-->                    
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix">
                        <ul class="enclosure-list">
                        	<li>
                            	<span>附件标题附件标题附件标题附件标题附件标题附件标题附件标题</span>
                                <a href="#"><i class="glyphicon glyphicon-trash file-del-ico"></i></a>
                                <a href="#"><i class="glyphicon glyphicon-download-alt file-download-ico"></i></a>
                            </li>
                            <li>
                            	<span>附件标题</span>
                                <a href="#"><i class="glyphicon glyphicon-trash file-del-ico"></i></a>
                                <a href="#"><i class="glyphicon glyphicon-download-alt file-download-ico"></i></a>
                            </li>
                            <li>
                            	<span>附件标题</span>
                                <a href="#"><i class="glyphicon glyphicon-trash file-del-ico"></i></a>
                                <a href="#"><i class="glyphicon glyphicon-download-alt file-download-ico"></i></a>
                            </li>
                            
                        </ul>
                    </div>                        
                    <!--上传附件列表 end-->                    
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="toolico-box">
                            <ul class="nav navbar-right ico-toolbtn">
                                <li>
                                    <a href="#" title="新增">
                                        <i class="glyphicon glyphicon-plus"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#" title="修改">
                                        <i class="glyphicon glyphicon-edit"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#" title="删除">
                                        <i class="glyphicon glyphicon-trash"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#" title="导入">
                                        <i class="glyphicon glyphicon-import"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#" title="导出">
                                        <i class="glyphicon glyphicon-export"></i>
                                    </a>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_content font14"> 
                            <!--<div class="DTTT_container">
                                <a href="javascript:importfile();"
                                    class="DTTT_button DTTT_button_update">新增</a> <a
                                    href="javascript:importfile();"
                                    class="DTTT_button DTTT_button_update">修改</a> <a
                                    href="javascript:importfile();"
                                    class="DTTT_button DTTT_button_update">导入</a> <a href="#"
                                    class="DTTT_button DTTT_button_update">删除</a> <a
                                    href="javascript:exportmember();"
                                    class="DTTT_button DTTT_button_copy">导出</a>
                            </div> -->
                           <%--  <asiainfo:table id="mytable" paging="true" actionUrl="paginge.jhtml"  cssclass="table table-striped responsive-utilities jambo_table"  formid = "form1"
                                keyField="id" checkbox="true" width="true" hasChild="true" childFunc="getChild"> 
                                <asiainfo:td title="element_id" field="element_id" width="120"></asiainfo:td>
                                <asiainfo:td title="element_type" field="element_type" render="gettype(data);" width="200"></asiainfo:td>
                                <asiainfo:td title="element_name" field="element_name" width="20" cclass="text"></asiainfo:td>
                                <asiainfo:td title="element_lable" field="element_lable" width="150"></asiainfo:td>
                                <asiainfo:td title="element_category" field="element_category" width="150"></asiainfo:td>
                                <asiainfo:td title="start_date" field="start_date" width="100"></asiainfo:td>
                                <asiainfo:td title="end_date" field="end_date" width="130"></asiainfo:td>
                                <asiainfo:td title="end_date" field="element_help" width="130"></asiainfo:td>
                            </asiainfo:table>  --%>
                    	 	<table id="mytable" ></table> 
                        </div>
                    </div>
                </div>           
            </form>
	</div>
</div>	
</body> 
<script type="text/javascript">
	var gettype = function(data) {
		if (data == 'tree') {
			return "树形组件";
		} else if (data == 'select') {
			return "下拉组件";
		}else if(data == 'custom'){
			return "数据源组件";
		}
		else {
			return "";
		}

	};
	/*
	function getChild(data){
		var info;
		$.ajax({
			url:"getChild.jhtml",
			type: "post", 
			async: false,
			data: data, 
	        dataType: "html",
	        success: function (data) {  
	            info = data;
	        },  
	        error: function (XMLHttpRequest, textStatus, errorThrown) { 
	        	info = errorThrown;
	        }  
		}); 
		return info;
	}
	*/
</script>
</html>
