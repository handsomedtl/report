/**
 * datagrid 风格控制
 */

/**
 * style: 
 *  1. 自定义函数 
 *   function(value,row,index){
 *  	return 'background-color:#ffee00;color:red;';
 *		// the function can return predefined css class and inline style
 *		// return {class:'c1',style:'color:red'}
 *  }
 *  
 *  2. 已经定义的函数名  函数签名应该满足 function(value,row,index) 格式
 *  3. 内联样式串  'background-color:#ffee00;color:red;'
 *  4. json 串 "{class:'c1',style:'color:red'}"
 *  
 * value: 单元格值
 * row: 行对象
 * index: 行索引
 */
function datagridCellStyle(style,value,row,index){
	try {
		//自定义函数或则内置函数名
		var styleobj;
		if(typeof style =="string"){
			eval("styleobj = "+ style);
		}else{
			styleobj=style;
		}
        if(typeof styleobj == 'function'){
            return styleobj(value,row,index);
        }else{
        	return styleobj;
        }
    } catch (e) {

    } finally {
    }


    return style;
}



function datagridCell_Bold(value,row,index){
	return "font-weight:bold;"
}

function datagridCell_Italic(value,row,index){
	return "font-style:italic;"
}

function datagridCell_NegativeRed(value,row,index){
	if(1*value<0){
        return 'color:red';
    }
}


/**
 * ================================================
 * 格式化
 * ================================================
 * 
 */

function datagridCellFormat(datatype,format,value,row,index){
	try {
		//自定义函数或则内置函数名
		var fmtobj;
		if(typeof format =="string"){
			eval("fmtobj = "+ format);
		}else{
			fmtobj=format;
		}
        //eval("fmtobj = "+ format);
        if(typeof fmtobj == 'function'){
            return fmtobj(value,row,index);
        }else{
        	return fmtobj;
        }
    } catch (e) {
    } finally {
    }
    
    if(datatype=='date' && value){
    	var date = new Date(value);
        return date.format(format);
    }else if(datatype=='number'||!isNaN(1*value)){
    	var df = new DecimalFormat(format);
        return df.format(value);
    }
    
    return value;
}

function datagridFormat_NegativeRed(value,format){
	var v = 1*value;
	if(!isNaN(v)){
		var df = new DecimalFormat(format);
        return v<0?"<span style='color:red;' >"+df.format(value)+"</span>":df.format(value);
	}
	return value;
}

