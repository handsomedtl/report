<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="asiainfo" uri="/asiainfo-tags"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<form class="search">
					<div class="col-lg-5 col-md-5 col-sm-5 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-5 col-sm-5 col-xs-12">报表分类</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <asiainfo:select cssclass="form-control col-md-8 col-sm-8 col-xs-6" id="rptcategory" name="rptcategory" emid="report_category">
						</asiainfo:select>
                                    </div>
                                </div>
                            </div>
                    <div class="col-lg-5 col-md-5 col-sm-5 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-md-5 col-sm-5 col-xs-12">报表名称</label>
                                    <div class="col-md-7 col-sm-7 col-xs-12">
                                        <input class="form-control col-md-8 col-sm-8 col-xs-8"  type="text" id="rptname" />
                                    </div>
                                </div>
                            </div>				
                </form>
			</div>
		</div>
	</div>


