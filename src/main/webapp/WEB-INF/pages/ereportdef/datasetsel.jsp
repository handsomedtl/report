<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="asiainfo" uri="/asiainfo-tags"%>

<script type="text/javascript">
	function collapseAll() {
		$('.easyui-treegrid').treegrid('collapseAll');
	}
</script>

	<div class="container">
	
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<form class="search">
					<div class="col-lg-5 col-md-5 col-sm-5 col-xs-12">
                                <div class="form-group  win-search">
                                    <label class="control-label col-md-5 col-sm-5 col-xs-12">数据集分类:</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <asiainfo:select cssclass="form-control col-md-8 col-sm-8 col-xs-6" id="typeseldataset" name="typeseldataset" emid="td_s_category">
						</asiainfo:select>
                                    </div>
                                </div>
                            </div>
                    <div class="col-lg-5 col-md-5 col-sm-5 col-xs-12">
                                <div class="form-group  win-search">
                                    <label class="control-label col-md-5 col-sm-5 col-xs-12">数据集名称：</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <input class="form-control col-md-8 col-sm-8 col-xs-8"  type="text" id="name" />
                                    </div>
                                </div>
                            </div>
                                
					
					<button class="btn btn-success select-btn-r"  onclick="javascript:query();return false;">查询</button>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div style="margin: 10px 0;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="collapseAll()">全部收起</a>
				</div>
				<table id='datasetsel' class="easyui-treegrid" style="height: 300px"
					data-options="
                url: 'datasettree.jhtml',
                method: 'get',
                rownumbers: true,
                idField: 'id',
                treeField: 'text',
                fitColumns:true,
                collapsible: true
            ">
					<thead>
						<tr>
							<th data-options="field:'text'" width="100" align="left">数据集名称</th>
							<th data-options="field:'remark'" width="100" align="center">说明</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>


