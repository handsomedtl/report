package com.asiainfo.edata;

import com.asiainfo.edata.dataset.ParseResult;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.eframe.dataset.Dataset;
/**
 * 数据集接口定义
 * @author baowzh
 *
 * @param <E>
 * @param <P>
 */
public interface EDataset<E, P> extends Dataset<E, P> {
    /**
     * 获取数据集配置信息
     * @return
     */
	public EDatasetMeta getMeta();
	/**
	 * 解析并预览数据集
	 * @return
	 * @throws ErrorDatasetDefineException
	 */
	public ParseResult parseAndPreeview() throws ErrorDatasetDefineException;
    

}
