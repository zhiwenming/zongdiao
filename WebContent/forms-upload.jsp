<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<title>Pike Admin - Free Bootstrap 4 Admin Template</title>
		<meta name="description" content="Free Bootstrap 4 Admin Theme | Pike Admin">
		

		<!-- Favicon -->
		<link rel="shortcut icon" href="assets/images/favicon.ico">

		<!-- Switchery css -->
		<link href="assets/plugins/switchery/switchery.min.css" rel="stylesheet" />
		
		<!-- Bootstrap CSS -->
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- Font Awesome CSS -->
		<link href="assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		
		<!-- Custom CSS -->
		<link href="assets/css/style.css" rel="stylesheet" type="text/css" />

		<!-- Modernizr -->
		<script src="assets/js/modernizr.min.js"></script>

		<!-- jQuery -->
		<script src="assets/js/jquery.min.js"></script>

		<!-- Moment -->
		<script src="assets/js/moment.min.js"></script>
		
		<!-- BEGIN CSS for this page -->
		<link href="assets/plugins/jquery.filer/css/jquery.filer.css" rel="stylesheet" />
		<link href="assets/plugins/jquery.filer/css/themes/jquery.filer-dragdropbox-theme.css" rel="stylesheet" />
		<!-- END CSS for this page -->
				
</head>

<body class="adminbody">

<div id="main">

	<!-- top bar navigation -->
	<div class="headerbar">

		<!-- LOGO -->
        <div class="headerbar-left">
			<a  class="logo"><img alt="logo" src="assets/images/logo.png" /> <span>Admin</span></a>
        </div>

        <nav class="navbar-custom">

                    <ul class="list-inline float-right mb-0">
        					<li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle nav-user" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <img src="assets/images/avatars/admin.png" alt="Profile image" class="avatar-rounded">
                            </a>
                            </li>

                    </ul>

                    <ul class="list-inline menu-left mb-0">
                        <li class="float-left">
                            <button class="button-menu-mobile open-left">
								<i class="fa fa-fw fa-bars"></i>
                            </button>
                        </li>                        
                    </ul>

        </nav>

	</div>
	<!-- End Navigation -->
	
 
	<!-- Left Sidebar -->
	<div class="left main-sidebar">
	
		<div class="sidebar-inner leftscroll">

			<div id="sidebar-menu">
        
			<ul>

					<li class="submenu">
						<a href="index.html"><i class="fa fa-fw fa-bars"></i><span> Dashboard </span> </a>
                    </li>

					
					<li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-table"></i> <span> 输出表格 </span> <span class="menu-arrow"></span></a>
							<ul class="list-unstyled">
								
								<li class="active"><a href="tables-datatable.html">当日装表格展示</a></li>
								<li class="active"><a href="xiu-datatable.html">当日修表格展示</a></li>
							</ul>
                    </li>
										
             

					<li class="submenu">
                        <a href="#" class="active"><i class="fa fa-fw fa-file-text-o"></i> <span> 上传表格 </span> <span class="menu-arrow"></span></a>
                            <ul class="list-unstyled">
                                
								<li class="active"><a href="forms-upload.jsp">上传下载</a></li>
							
                            </ul>
                    </li>
                    
                    
				
                    
					
              

				
				

			
					
            </ul>

            <div class="clearfix"></div>

			</div>
        
			<div class="clearfix"></div>

		</div>

	</div>
	<!-- End Sidebar -->


    <div class="content-page">
	
		<!-- Start content -->
        <div class="content">
            
			<div class="container-fluid">

					
			<div class="row">
					<div class="col-xl-12">
							<div class="breadcrumb-holder">
                                    <h1 class="main-title float-left">上传下载</h1>
                                    <ol class="breadcrumb float-right">
										<li class="breadcrumb-item">Home</li>
										<li class="breadcrumb-item active">上传下载</li>
                                    </ol>
                                    <div class="clearfix"></div>
                            </div>
					</div>
			</div>
            <!-- end row -->


			
			<div class="row">
			
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-xl-6">						
						<div class="card mb-3">
							<div class="card-header">
								<h3><i class="fa fa-file"></i> 上传表格</h3>
								可多选可单选
							</div>
								
							<div class="card-body">
																
										<input type="file" name="files[]" id="filer_example1" multiple="multiple">
										
							</div>														
						</div><!-- end card-->					
                    </div>

						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-xl-6">						
						<div class="card mb-3">
							<div class="card-header">
								<h3><i class="fa fa-file"></i> 导出结果表</h3>
								导出前请预览
							</div>
								
							<div class="card-body" style=" display: flex">
								
								<!--  <input type="file" name="files[]" id="filer_example2" multiple="multiple">-->
								<form action="/zongdiao/Export" method="post"><button class="btn btn-danger"   type="submit">导出当修装</button></form>
								<form action="/zongdiao/ExportDangRiZhuang" method="post">	<button class="btn btn-primary"  type="submit"> 导出当日装</button></form>
								
							</div>														
						</div><!-- end card-->					
                    </div>
					
					
					
					
			</div>





            </div>
			<!-- END container-fluid -->

		</div>
		<!-- END content -->

    </div>
	<!-- END content-page -->
    
	<footer class="footer">
		<span class="text-right">
		Copyright <a target="_blank" href="#">Pike Admin</a>
		</span>
		<span class="float-right">
		More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>
		</span>
	</footer>

</div>
<!-- END main -->

<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>

<script src="assets/js/detect.js"></script>
<script src="assets/js/fastclick.js"></script>
<script src="assets/js/jquery.blockUI.js"></script>
<script src="assets/js/jquery.nicescroll.js"></script>
<script src="assets/js/jquery.scrollTo.min.js"></script>
<script src="assets/plugins/switchery/switchery.min.js"></script>

<!-- App js -->
<script src="assets/js/pikeadmin.js"></script>

<!-- BEGIN Java Script for this page -->
<script src="assets/plugins/jquery.filer/js/jquery.filer.min.js"></script>
<script>
$(document).ready(function(){

	'use-strict';

    //Example 2
    $('#filer_example2').filer({
        limit: 3,
        maxSize: 3,
        extensions: ['jpg', 'jpeg', 'png', 'gif', 'psd'],
        changeInput: true,
        showThumbs: true,
        addMore: true
    });

	//Example 1
    $("#filer_example1").filer({
        limit: null,
        maxSize: null,
        extensions: null,
        changeInput: '<div class="jFiler-input-dragDrop"><div class="jFiler-input-inner"><div class="jFiler-input-icon"><i class="icon-jfi-cloud-up-o"></i></div><div class="jFiler-input-text"><h3>Drag & Drop files here</h3> <span style="display:inline-block; margin: 15px 0">or</span></div><a class="jFiler-input-choose-btn btn btn-custom">Browse Files</a></div></div>',
        showThumbs: true,
        theme: "dragdropbox",
        templates: {
            box: '<ul class="jFiler-items-list jFiler-items-grid"></ul>',
            item: '<li class="jFiler-item">\
                        <div class="jFiler-item-container">\
                            <div class="jFiler-item-inner">\
                                <div class="jFiler-item-thumb">\
                                    <div class="jFiler-item-status"></div>\
                                    <div class="jFiler-item-info">\
                                        <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
                                        <span class="jFiler-item-others">{{fi-size2}}</span>\
                                    </div>\
                                    {{fi-image}}\
                                </div>\
                                <div class="jFiler-item-assets jFiler-row">\
                                    <ul class="list-inline pull-left">\
                                        <li>{{fi-progressBar}}</li>\
                                    </ul>\
                                    <ul class="list-inline pull-right">\
                                        <li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
                                    </ul>\
                                </div>\
                            </div>\
                        </div>\
                    </li>',
            itemAppend: '<li class="jFiler-item">\
                            <div class="jFiler-item-container">\
                                <div class="jFiler-item-inner">\
                                    <div class="jFiler-item-thumb">\
                                        <div class="jFiler-item-status"></div>\
                                        <div class="jFiler-item-info">\
                                            <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
                                            <span class="jFiler-item-others">{{fi-size2}}</span>\
                                        </div>\
                                        {{fi-image}}\
                                    </div>\
                                    <div class="jFiler-item-assets jFiler-row">\
                                        <ul class="list-inline pull-left">\
                                            <li><span class="jFiler-item-others">{{fi-icon}}</span></li>\
                                        </ul>\
                                        <ul class="list-inline pull-right">\
                                            <li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
                                        </ul>\
                                    </div>\
                                </div>\
                            </div>\
                        </li>',
            progressBar: '<div class="bar"></div>',
            itemAppendToEnd: false,
            removeConfirmation: true,
            _selectors: {
                list: '.jFiler-items-list',
                item: '.jFiler-item',
                progressBar: '.bar',
                remove: '.jFiler-item-trash-action'
            }
        },
        dragDrop: {
            dragEnter: null,
            dragLeave: null,
            drop: null,
        },
        uploadFile: {
            url: "assets/plugins/jquery.filer/php/upload.php",
            data: null,
            type: 'POST',
            enctype: 'multipart/form-data',
            beforeSend: function(){},
            success: function(data, el){
                var parent = el.find(".jFiler-jProgressBar").parent();
                el.find(".jFiler-jProgressBar").fadeOut("slow", function(){
                    $("<div class=\"jFiler-item-others text-success\"><i class=\"fa fa-plus\"></i> Success</div>").hide().appendTo(parent).fadeIn("slow");
                });
            },
            error: function(el){
                var parent = el.find(".jFiler-jProgressBar").parent();
                el.find(".jFiler-jProgressBar").fadeOut("slow", function(){
                    $("<div class=\"jFiler-item-others text-error\"><i class=\"fa fa-minus\"></i> Error</div>").hide().appendTo(parent).fadeIn("slow");
                });
            },
            statusCode: null,
            onProgress: null,
            onComplete: null
        },
		files: [
			{
				name: "1.jpg",
				size: 145,
				type: "image/jpg",
				file: "assets/images/sample-image-1.jpg"
			},
			{
				name: "2.jpg",
				size: 145,
				type: "image/jpg",
				file: "assets/images/sample-image-2.jpg"
			}
		],
        addMore: false,
        clipBoardPaste: true,
        excludeName: null,
        beforeRender: null,
        afterRender: null,
        beforeShow: null,
        beforeSelect: null,
        onSelect: null,
        afterShow: null,
        onRemove: function(itemEl, file, id, listEl, boxEl, newInputEl, inputEl){
            var file = file.name;
            $.post('assets/plugins/jquery.filer/php/remove_file.php', {file: file});
        },
        onEmpty: null,
        options: null,
        captions: {
            button: "Choose Files",
            feedback: "Choose files To Upload",
            feedback2: "files were chosen",
            drop: "Drop file here to Upload",
            removeConfirmation: "Are you sure you want to remove this file?",
            errors: {
                filesLimit: "Only {{fi-limit}} files are allowed to be uploaded.",
                filesType: "Only Images are allowed to be uploaded.",
                filesSize: "{{fi-name}} is too large! Please upload file up to {{fi-maxSize}} MB.",
                filesSizeAll: "Files you've choosed are too large! Please upload files up to {{fi-maxSize}} MB."
            }
        }
    });
});
</script>
<!-- END Java Script for this page -->

</body>
</html>