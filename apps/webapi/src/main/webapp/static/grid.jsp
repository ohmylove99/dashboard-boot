<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
<title>My First Grid</title>  

<link rel="stylesheet" type="text/css" media="screen" href="../css/ui.jqgrid-bootstrap-ui.css" />  
<link rel="stylesheet" type="text/css" media="screen" href="../css/ui.jqgrid-bootstrap.css" /> 
<link rel="stylesheet" type="text/css" media="screen" href="../themes/redmond/jquery-ui-custome.css" />  
<link rel="stylesheet" type="text/css" media="screen" href="../css/ui.jqgrid.css" />  
  
<style type="text/css">  
html, body {  
    margin: 0;  
    padding: 0;  
    font-size: 75%;  
}  
</style>  
   
<script src="../js/jquery-1.11.0.min.js" type="text/javascript"></script>  
<script src="../js/i18n/grid.locale-en.js" type="text/javascript"></script>  
<script src="../js/jquery.jqGrid.min.js" type="text/javascript"></script>  
   
<script type="text/javascript">  
  
var data=[  
         {id:1,name:"xiao",birthday:"2012-12-12"},  
         {id:2,name:"xiao",birthday:"2012-12-12"},  
         {id:3,name:"xiao",birthday:"2012-12-12"},  
         {id:4,name:"xiao",birthday:"2012-12-12"},  
         {id:5,name:"xiao",birthday:"2012-12-12"},  
         {id:6,name:"xiao",birthday:"2012-12-12"},  
         {id:7,name:"xiao",birthday:"2012-12-12"},  
         {id:8,name:"xiao",birthday:"2012-12-12"},  
         {id:9,name:"xiao",birthday:"2012-12-12"},  
         {id:10,name:"xiao",birthday:"2012-12-12"},  
];// the native data  
  
$(function(){   
  $("#list").jqGrid({  
    datatype: "local",// specify the data from local   
    data:data,// the data which will be displayed   
    colNames:["ID","NAME", "BIRTHDAY"],// the column header names  
    colModel :[   
      {name:"id", index:"id", width:100},   
      {name:"name", index:"name", width:100},   
      {name:"birthday", index:"birthday", width:100, sortable:false},   
    ],// the column discription  
    pager: "#pager",// specify the pager bar   
    rowNum:10, // sets how many records we want to view in the grid  
    // An array to construct a select box element in the pager   
    // in which we can change the number of the visible rows.  
    rowList:[10,20,30],  
    // add the new column which counts the number of available rows  
    rownumbers:true,   
    // the column according to which the data is to be sorted   
    // when it is initially loaded  
    sortname: "id",     
    sortorder: "desc", // the initial sorting order  
    // view the beginning and ending record number in the grid  
    viewrecords: true,   
    gridview: true,  
    caption: "native grid",  
    width:500,   
    height:200,  
  });   
});   
</script>  
</head>  
<body>  
    <table id="list"></table>   
    <div id="pager"></div>   
</body>  
</html>  
