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
			<a href="index.html" class="logo"><img alt="logo" src="assets/images/logo.png" /> <span>Admin</span></a>
        </div>

        <nav class="navbar-custom">

                    <ul class="list-inline float-right mb-0">

						<li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <i class="fa fa-fw fa-question-circle"></i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-arrow-success dropdown-lg">
                                <!-- item-->
                                <div class="dropdown-item noti-title">
                                    <h5><small>Help and Support</small></h5>
                                </div>

                                <!-- item-->
                                <a target="_blank" href="#" class="dropdown-item notify-item">                                    
                                    <p class="notify-details ml-0">
                                        <b>Do you want custom development to integrate this theme?</b>
                                        <span>Contact Us</span>
                                    </p>
                                </a>

                                <!-- item-->
                                <a target="_blank" href="#" class="dropdown-item notify-item">                                    
                                    <p class="notify-details ml-0">
                                        <b>Do you want PHP version of the theme that save dozens of hours of work?</b>
                                        <span>Try Pike Admin PRO</span>
                                    </p>
                                </a>                               

                                <!-- All-->
                                <a title="Clcik to visit Pike Admin Website" target="_blank" href="#" class="dropdown-item notify-item notify-all">
                                    <i class="fa fa-link"></i> Visit Pike Admin Website
                                </a>

                            </div>
                        </li>
						
                        <li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <i class="fa fa-fw fa-envelope-o"></i><span class="notif-bullet"></span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-arrow-success dropdown-lg">
                                <!-- item-->
                                <div class="dropdown-item noti-title">
                                    <h5><small><span class="label label-danger pull-xs-right">12</span>Contact Messages</small></h5>
                                </div>

                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">                                    
                                    <p class="notify-details ml-0">
                                        <b>Jokn Doe</b>
                                        <span>New message received</span>
                                        <small class="text-muted">2 minutes ago</small>
                                    </p>
                                </a>

                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">                                    
                                    <p class="notify-details ml-0">
                                        <b>Michael Jackson</b>
                                        <span>New message received</span>
                                        <small class="text-muted">15 minutes ago</small>
                                    </p>
                                </a>

                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">                                    
                                    <p class="notify-details ml-0">
                                        <b>Foxy Johnes</b>
                                        <span>New message received</span>
                                        <small class="text-muted">Yesterday, 13:30</small>
                                    </p>
                                </a>

                                <!-- All-->
                                <a href="#" class="dropdown-item notify-item notify-all">
                                    View All
                                </a>

                            </div>
                        </li>
                        
						<li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <i class="fa fa-fw fa-bell-o"></i><span class="notif-bullet"></span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-lg">
								<!-- item-->
                                <div class="dropdown-item noti-title">
                                    <h5><small><span class="label label-danger pull-xs-right">5</span>Allerts</small></h5>
                                </div>
								
                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">
                                    <div class="notify-icon bg-faded">
                                        <img src="assets/images/avatars/avatar2.png" alt="img" class="rounded-circle img-fluid">
                                    </div>
                                    <p class="notify-details">
                                        <b>John Doe</b>
                                        <span>User registration</span>
                                        <small class="text-muted">3 minutes ago</small>
                                    </p>
                                </a>

                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">
                                    <div class="notify-icon bg-faded">
                                        <img src="assets/images/avatars/avatar3.png" alt="img" class="rounded-circle img-fluid">
                                    </div>
                                    <p class="notify-details">
                                        <b>Michael Cox</b>
                                        <span>Task 2 completed</span>
                                        <small class="text-muted">12 minutes ago</small>
                                    </p>
                                </a>

                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">
                                    <div class="notify-icon bg-faded">
                                        <img src="assets/images/avatars/avatar4.png" alt="img" class="rounded-circle img-fluid">
                                    </div>
                                    <p class="notify-details">
                                        <b>Michelle Dolores</b>
                                        <span>New job completed</span>
                                        <small class="text-muted">35 minutes ago</small>
                                    </p>
                                </a>

                                <!-- All-->
                                <a href="#" class="dropdown-item notify-item notify-all">
                                    View All Allerts
                                </a>

                            </div>
                        </li>

                        <li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle nav-user" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <img src="assets/images/avatars/admin.png" alt="Profile image" class="avatar-rounded">
                            </a>
                            <div class="dropdown-menu dropdown-menu-right profile-dropdown">
                                <!-- item-->
                                <div class="dropdown-item noti-title">
                                    <h5 class="text-overflow"><small>Hello, admin</small> </h5>
                                </div>

                                <!-- item-->
                                <a href="pro-profile.html" class="dropdown-item notify-item">
                                    <i class="fa fa-user"></i> <span>Profile</span>
                                </a>

                                <!-- item-->
                                <a href="#" class="dropdown-item notify-item">
                                    <i class="fa fa-power-off"></i> <span>Logout</span>
                                </a>

								<!-- item-->
                                <a target="_blank" href="#" class="dropdown-item notify-item">
                                    <i class="fa fa-external-link"></i> <span>Pike Admin</span>
                                </a>
                            </div>
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
                        <a href="charts.html"><i class="fa fa-fw fa-area-chart"></i><span> Charts </span> </a>
                    </li>
					
					<li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-table"></i> <span> Tables </span> <span class="menu-arrow"></span></a>
							<ul class="list-unstyled">
								<li><a href="tables-basic.html">Basic Tables</a></li>
								<li><a href="tables-datatable.html">Data Tables</a></li>
							</ul>
                    </li>
										
                    <li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-tv"></i> <span> User Interface </span> <span class="menu-arrow"></span></a>
                            <ul class="list-unstyled">
                                <li><a href="ui-alerts.html">Alerts</a></li>
                                <li><a href="ui-buttons.html">Buttons</a></li>
                                <li><a href="ui-cards.html">Cards</a></li>
                                <li><a href="ui-carousel.html">Carousel</a></li>
                                <li><a href="ui-collapse.html">Collapse</a></li>
                                <li><a href="ui-icons.html">Icons</a></li>
                                <li><a href="ui-modals.html">Modals</a></li>
                                <li><a href="ui-tooltips.html">Tooltips and Popovers</a></li>
                            </ul>
                    </li>

					<li class="submenu">
                        <a href="#" ><i class="fa fa-fw fa-file-text-o"></i> <span> Forms </span> <span class="menu-arrow"></span></a>
                            <ul class="list-unstyled">
                                <li><a href="forms-general.html">General Elements</a></li>
								<li><a href="forms-select2.html">Select2</a></li>
                                <li><a href="forms-validation.html">Form Validation</a></li>
                                <li><a href="forms-text-editor.html">Text Editors</a></li>
								<li><a href="forms-upload.html">Multiple File Upload</a></li>
								<li><a href="forms-datetime-picker.html">Date and Time Picker</a></li>
								<li><a href="forms-color-picker.html">Color Picker</a></li>
                            </ul>
                    </li>
					
                    <li class="submenu">
						<a href="#"><i class="fa fa-fw fa-th"></i> <span> Plugins </span> <span class="menu-arrow"></span></a>
                            <ul class="list-unstyled">
                                <li><a href="star-rating.html">Star Rating</a></li>
								<li><a href="range-sliders.html">Range Sliders</a></li>
								<li><a href="tree-view.html">Tree View</a></li>
								<li><a href="sweetalert.html">SweetAlert</a></li>
								<li><a href="calendar.html">Calendar</a></li>
								<li><a href="gmaps.html">GMaps</a></li>
								<li><a href="counter-up.html">Counter-Up</a></li>
                            </ul>
                    </li>

					<li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-image"></i> <span> Images and Galleries </span> <span class="menu-arrow"></span></a>
							<ul class="list-unstyled">
								<li><a href="media-fancybox.html"><span class="label radius-circle bg-danger float-right">cool</span> Fancybox </a></li>								
								<li><a href="media-masonry.html">Masonry</a></li>
								<li><a href="media-lightbox.html">Lightbox</a></li>
								<li><a href="media-owl-carousel.html">Owl Carousel</a></li>
								<li><a href="media-image-magnifier.html">Image Magnifier</a></li>
								
							</ul>
                    </li>
					
                    <li class="submenu">
                        <a href="#"><span class="label radius-circle bg-danger float-right">20</span><i class="fa fa-fw fa-copy"></i><span> Example Pages </span></a>
                            <ul class="list-unstyled">								
                                <li><a href="page-pricing-tables.html">Pricing Tables</a></li>
                                <li><a target="_blank" href="page-coming-soon.html">Countdown</a></li>								
                                <li><a href="page-invoice.html">Invoice</a></li>                        
								<li><a href="page-login.html">Login / Register</a></li>								
								<li><a href="page-blank.html">Blank Page</a></li>
                            </ul>
                    </li>

					<li class="submenu">
                        <a href="#"><span class="label radius-circle bg-primary float-right">9</span><i class="fa fa-fw fa-indent"></i><span> Menu Levels </span></a>
                            <ul>
								<li>
                                    <a href="#"><span>Second Level</span></a>
                                </li>
                                <li class="submenu">
                                    <a href="#"><span>Third Level</span> <span class="menu-arrow"></span> </a>
                                        <ul style="">
                                            <li><a href="#"><span>Third Level Item</span></a></li>
                                            <li><a href="#"><span>Third Level Item</span></a></li>
                                        </ul>
                                </li>                                
                            </ul>
                    </li>

					<li class="submenu">
                       <!--  <a class="pro" href="#"class="active"><i class="fa fa-fw fa-star"></i><span> Pike Admin PRO </span> <span class="menu-arrow"></span></a> -->
                           <a  href="#"class="active"><i class="fa fa-fw fa-star"></i><span> Ipran </span> <span class="menu-arrow"></span></a>
                            <ul class="list-unstyled">								
                                <li><a target="_blank" href="#">Admin PRO features</a></li>
                                <!-- 修改上传的连接 -->
								<li class="active"><a href="/zhurenli/IpranUpload">Ipran 上传表格</a></li>
								<li><a href="pro-profile.html">My Profile</a></li>
                                <li><a href="pro-users.html">Users</a></li>
                                <li><a href="pro-articles.html">Articles</a></li>
                                <li><a href="pro-categories.html">Categories</a></li>
								<li><a href="pro-pages.html">Pages</a></li>								
                                <li><a href="pro-contact-messages.html">Contact Messages</a></li>
								<li><a href="pro-slider.html">Slider</a></li>
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
                                    <h1 class="main-title float-left">Ipran 上传表格</h1>
                                    <ol class="breadcrumb float-right">
										<li class="breadcrumb-item">Home</li>
										<li class="breadcrumb-item active">Ipran 上传表格</li>
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
								<h3><i class="fa fa-file"></i><label id="one" style="display:none">${bacakTOaPP}</label></h3>
								上传文件或者选择删除
							</div>
								
							<div class="card-body">
																
										<input type="file" name="files[]" id="filer_example1" multiple="multiple">
										
							</div>														
						</div><!-- end card-->					
                    </div>

					
				<!--  	
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-xl-6">						
						<div class="card mb-3">
							<div class="card-header">
								<h3><i class="fa fa-file"></i> Example 2</h3>
								 Maximum 3 files, all files together must have maximal 3MB and the extensions must be matched in the array ['jpg', 'png', 'gif'].
							</div>
								
							<div class="card-body">
								
								<input type="file" name="files[]" id="filer_example2" multiple="multiple">
								
							</div>														
						</div>-->  <!-- end card-->					
                  <!--   </div>
					-->
					
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
		More Templates <a href="http://www.cssmoban.com/" target="_blank" title="æ¨¡æ¿ä¹å®¶">æ¨¡æ¿ä¹å®¶</a> - Collect from <a href="http://www.cssmoban.com/" title="ç½é¡µæ¨¡æ¿" target="_blank">ç½é¡µæ¨¡æ¿</a>
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
	var filelist = JSON.parse(document.getElementById("one").innerText);
   //alert(JSON.parse(document.getElementById("one").innerText)[0]["name"]);
   //alert("aaa");
   var fileslist= [
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
		},
		{
			name: "2",
			size: 145,
			type: "xlsx"
			//,
			//file: "assets/images/sample-image-2.jpg"
		}
	];
 
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
        changeInput: '<div class="jFiler-input-dragDrop"><div class="jFiler-input-inner"><div class="jFiler-input-icon"><i class="icon-jfi-cloud-up-o"></i></div><div class="jFiler-input-text"><h3>上传 & 删除 文件  可多选 只允许XLSX文件</h3> <span style="display:inline-block; margin: 15px 0">or</span></div><a class="jFiler-input-choose-btn btn btn-custom">选择文件</a></div></div>',
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
                                            <li><span class="jFiler-item-others">{{fi-icon}}{{fi-name | limitTo: 25}}</span></li>\
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
        	//上传文件
           // url: "assets/plugins/jquery.filer/php/upload.php",
            url: "/zhurenli/IpranUpload",
            data: null,
            test:"aaaa",
            type: 'POST',
            enctype: 'multipart/form-data',
            beforeSend: function(){},
            success: function(data, el){
            	//alert("data:"+JSON.parse(data)[0]["name"]);
            	//var tieta = JSON.parse(bacakTOaPP);
            	// 
            	
                var parent = el.find(".jFiler-jProgressBar").parent();
        
            	
                el.find(".jFiler-jProgressBar").fadeOut("slow", function(){
                
                	//var tieta= ${bacakTOaPP};
                	//alert("tieta:"+tieta)
                	//if(${bacakTOaPP}!=null){
                		//alert("tieta:"+${bacakTOaPP})
                	//}else{
                		
                	//}
                	
                    $("<div class=\"jFiler-item-others text-success\"><i class=\"fa fa-plus\"></i> "+JSON.parse(data)[0]['name']+" Success </div>").hide().appendTo(parent).fadeIn("slow");
               	 //location.reload();
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
       
        //alert("aaa"),
       // tieta: ${bacakTOaPP},
       //tieta:"aaaa",
       files:filelist,
		files2: [
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
			},
			{
				name: "2",
				size: 145,
				type: "xlsx"
				//,
				//file: "assets/images/sample-image-2.jpg"
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
        	//alert("tieta:"+"ggg");
            var file = file.name;
            //传送文件
            //$.post('assets/plugins/jquery.filer/php/remove_file.php', {file: file});
             $.post('/zhurenli/IpranUpload', {file: file});
            //删除以后页面刷新一次
           // location.reload();
           // alert("aaa");
          
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