<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Lucene with Spring</title>

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">

</head>
<body>
		<div class="container">
			<h3>${msg }</h3>
			<div class="col-sm-12">
				<div class="row">
					<button id="btncreate" class="btn btn-danger">Create Index</button>
				</div>
				<div class="row">
					<form id="frmsearch">
					<div class="form-group">
						<div class="col-sm-12">
							<div class="col-sm-2"><label class="form-control">Keyword : </label></div>
							<div class="col-sm-8"><input type="text" required class="form-control" id="keyword"/> </div>
							<div class="col-sm-2"><button type="submit" class="btn btn-success">Search</button></div>
						</div>
					</div>
					</form>
				</div>
				<div class="row">
					<table class="table">
						<thead>
							<tr>
								<th>ID</th>
								<th>Title</th>
								<th>Date</th>
							</tr>
						</thead>
						<tbody id="showarticle">
						
						</tbody>
					</table>
					<p class="pull-right" id="vu-pagination"></p>
				</div>
			</div>
		</div>
		
		<script src="assets/jquery-1.11.3.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
		
		<script src="assets/jquery.bootpag.min.js"></script>
		
		<script>
			$(document).ready(function(){
				var totalPage = 0;
				var pageIndex = 1;
				var pageSize = 3;
				
			    $("#frmsearch").submit(function(e){
			    	e.preventDefault();
			    	
			    	searcher(pageIndex, pageSize, true);
			    });
				
			    function searcher(pIndex, pSize, check){
			    	$.post("searcharticle",{
			    		query : $("#keyword").val(),
			    		pageIndex : pIndex,
			    		pageSize : pSize
			    	},function(data){
			    		if(data.length>0){
			    			totalPage = Math.ceil(data[0].totalRecords/pageSize);
			    		}
			    		var str='';
			    		for(var i=0; i<data.length; i++){
			    			str +='<tr>'
				    				+'<td>'+ data[i].id+'</td>'
				    				+'<td>'+ data[i].title+ '</td>'
				    				+'<td>'+ data[i].date + '</td>'
			    				+'</tr>';
			    		}
			    		
			    		$("#showarticle").html(str);
			    		if(check){
			    			pagination(totalPage,1);
			    			check= false;
			    		}
			    		
			    	});
			    }
				
			    function pagination(tPage, pIndex){
			    	$('#vu-pagination').bootpag({
				        total: tPage,
				        page: pIndex,
				        maxVisible: 5,
				        leaps: true,
				        firstLastUse: true,
				        first: 'First',
				        last: 'Last',
				        wrapClass: 'pagination',
				        activeClass: 'active',
				        disabledClass: 'disabled',
				        nextClass: 'next',
				        prevClass: 'prev',
				        lastClass: 'last',
				        firstClass: 'first'
				    }).on("page", function(event, num){
				    	pageIndex = num;
				    	searcher(pageIndex,pageSize,false);
				    }); 
			    }
			    
			    $("#btncreate").click(function(){
			    	$.post("createindex",function(data){
			    		alert(data);
			    	})
			    });
			    
			    
			    
			    
			});
		
		
		
		
		
		</script>

</body>
</html>