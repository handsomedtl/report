$(document).ready(function(){
    var heads = ['A','B','C','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T',
    'U','V','W','X','Y','Z','AA','AB','AC','AD','AE','AF'];
    var columns=[];
    var formatterfun = function(value,row,index){
        if(value && value.name)
        return value.name;
        return value;
    }
    for (var h in heads) {
        columns.push({title:heads[h],width:"80",field:heads[h],formatter:formatterfun});
    }
    $("#grid-model").datagrid({
        singleSelect : true,
        editable:true,
        columns:[columns],
        frozenColumns:[[{title:" ",field:"typeName"}]],
        data:[{typeName:"表头"},{typeName:"数据行"},{typeName:"合计行"}]});
})
