<!DOCTYPE html>
<html lang="en">

<head>
     <!-- Required meta tags -->
     <meta charset="utf-8">
     <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
     <title>Gestion stock</title>
     <!-- plugins:css -->
     <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/mdi/css/materialdesignicons.min.css">
     <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/vendors/css/vendor.bundle.base.css">
     <!-- endinject -->
     <!-- Plugin css for this page -->
     <!-- End plugin css for this page -->
     <!-- inject:css -->
     <!-- endinject -->
     <!-- Layout styles -->
     <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
     <!-- End layout styles -->
     <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon.ico" />
</head>

<body>
     <div class="container-scroller">

          <!-- partial:partials/_navbar.html -->
          <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
               <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
                    <a class="navbar-brand brand-logo" href="index.html"><img src="assets/images/logo.svg"
                              alt="logo" /></a>
                    <a class="navbar-brand brand-logo-mini" href="index.html"><img src="assets/images/logo-mini.svg"
                              alt="logo" /></a>
               </div>
               <div class="navbar-menu-wrapper d-flex align-items-stretch">
                    <button class="navbar-toggler navbar-toggler align-self-center" type="button"
                         data-toggle="minimize">
                         <span class="mdi mdi-menu"></span>
                    </button>
                    <div class="search-field d-none d-md-block">
                         <form class="d-flex align-items-center h-100" action="#">
                              <div class="input-group">
                                   <div class="input-group-prepend bg-transparent">
                                        <i class="input-group-text border-0 mdi mdi-magnify"></i>
                                   </div>
                                   <input type="text" class="form-control bg-transparent border-0"
                                        placeholder="Search projects">
                              </div>
                         </form>
                    </div>
                    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
                         data-toggle="offcanvas">
                         <span class="mdi mdi-menu"></span>
                    </button>
               </div>
          </nav>
          <div class="container-fluid page-body-wrapper">

               <nav class="sidebar sidebar-offcanvas" id="sidebar">
                    <ul class="nav">
                         <li class="nav-item nav-profile">
                              <a href="#" class="nav-link">

                                   <div class="nav-profile-text d-flex flex-column">
                                        <span class="font-weight-bold mb-2">Directeur</span>
                                        <span class="text-secondary text-small">Project Manager</span>
                                   </div>
                              </a>
                         </li>
                         <li class="nav-item">
                              <a class="nav-link" data-bs-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
                                   <span class="menu-title">Gestion stock</span>
                                   <i class="menu-arrow"></i>
                              </a>
                              <div class="collapse" id="ui-basic">
                                   <ul class="nav flex-column sub-menu">
                                        <li class="nav-item"> <a class="nav-link" href="AllIn">Entree</a></li>
                                        <li class="nav-item"> <a class="nav-link" href="AllOut">Sortie</a></li>
                                        <li class="nav-item"> <a class="nav-link" href="EtatStock">Etat de stock</a></li>
                                   </ul>
                              </div>
                         </li>
                    </ul>
               </nav>
               <div class="main-panel container">

               <%
                    //out.println("message");
                    if(request.getAttribute("erreur")!=null){
                    %>
                    <div class="alert alert-danger" role="alert">
                         <%=request.getAttribute("erreur")%>
                    </div>
               <% }
               %>
                
               <%  
                    if(request.getAttribute("message")!=null){
                    %>
                    <div class="alert alert-success" role="alert">
                         <%=request.getAttribute("message")%>
                    </div>
               <% }
               %>
                    <!--content-->
                    