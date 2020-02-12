package com.asiainfo.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiainfo.edata.DatasetFactory;
import com.asiainfo.ereport.excel.ExcelExportHelper;
import com.asiainfo.ewebframe.ui.grid.head.UIGridHeader;
public class ReportData2Excel extends BaseTest {
	@Resource(name = "datasetFactory")
	private DatasetFactory datasetFactory;
	@Autowired
	ExcelExportHelper exl;
	@Test
	public void ExportData() throws IOException {
		UIGridHeader header = new UIGridHeader();
		String hs = "地市,市区、旗县、乡镇,专业线,二级部门,三级部门,四级部门,五级部门,客户经理,号码,低消用户,低消用户,换卡用户,换卡用户,换机用户,换机用户,换机用户,流量体验用户,流量体验用户,流量体验用户,智慧沃家从卡激活用户,智慧沃家从卡激活用户,智慧沃家从卡激活用户,4G网络激活用户,4G网络激活用户,用户状态,网别,套餐名称,套餐编码,终端品牌,终端型号,是否双卡,合约类型,合约生效时间,合约到期时间,融合业务类型,智慧沃家主号账号,智慧沃家从号账号,常用语音基站小区名,常用语音基站所在盟市、旗县、乡镇,常用语音基站对应营业厅编码,常用语音基站对应营业厅名称,常用流量基站小区名,常用流量基站所在盟市、旗县、乡镇,常用流量基站对应营业厅编码,常用流量基站对应营业厅名称,终端是否支持联通3G,终端是否支持联通4G|地市,市区、旗县、乡镇,专业线,二级部门,三级部门,四级部门,五级部门,客户经理,号码,是否为目标用户,低消办理档位,是否为目标用户,当前使用卡类型,是否为目标用户,是否换机,换机产品,是否为目标用户,是否参与,赠送流量包,是否为目标用户,是否参与,赠送流量包,是否为目标用户,是否激活,用户状态,网别,套餐名称,套餐编码,终端品牌,终端型号,是否双卡,合约类型,合约生效时间,合约到期时间,融合业务类型,智慧沃家主号账号,智慧沃家从号账号,常用语音基站小区名,常用语音基站所在盟市、旗县、乡镇,常用语音基站对应营业厅编码,常用语音基站对应营业厅名称,常用流量基站小区名,常用流量基站所在盟市、旗县、乡镇,常用流量基站对应营业厅编码,常用流量基站对应营业厅名称,终端是否支持联通3G,终端是否支持联通4G";
		header.resoleHeader(hs);
		String[] fields = "EPARCHY_CODE,AREA_CODE,DEPART_KIND,depart_name2,depart_name3,depart_name4,DEPART_NAME,CUST_MANAGER_ID,SERIAL_NUMBER,DXYH_SFWMBYH,DXYH_DXBLDW,HKYH_SFWMBYH,HKYH_DQSYKLX,HJYH_SFWMBYH,HJYH_SFHJ,HJYH_HJCP,LLTYYH_SFWMBYH,LLTYYH_SFCY,LLTYYH_ZSLLB,ZHWJCKJHYH_SFWMBYH,ZHWJCKJHYH_SFCY,ZHWJCKJHYH_ZSLLB,G4WLJHYH_SFWMBYH,G4WLJHYH_SFJH,USER_STATE,NET_TYPE_name,PACKAGE_NAME,PACKAGE_CODE,TERMINAL_BRAND,TERMINAL_MODEL,SFSK,CONTRACT_TYPE,CONTRACT_ENTRY,CONTRACT_DUE,FUSION_TYPE,ZHWJZHZH,ZHWJCHZH,CYYYJZXQM,CYYYJZSZD,CYYYJZDYYYT_CODE,CYYYJZDYYYT_NAME,CYLLJZXQM,CYLLJZSZD,CYLLJZDYYYT_CODE,CYLLJZDYYYT_NAME,a.if_lt_3g,a.if_lt_4g".split(",");
		for(int i=0;i<fields.length;i++){
			header.getCol(i).setField(fields[i]);
		}
		//System.out.println(header.getHeaderJson());
		
		System.out.println(header.getHeaderJson());
		System.out.println(header.getFrozenHeaderJson());

	}
}
