<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

          <!-- Sidebar user panel (optional) -->
          <!-- <div class="user-panel">
            <div class="pull-left image">
              <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
              <p>Komal Industries</p>
              Status
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div> -->

          <!-- search form (Optional) -->
          <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
              <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
          </form>
          <!-- /.search form -->

          <!-- Sidebar Menu -->
          <ul class="sidebar-menu">
            <li><a href="dashboard"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
             <li><a href="order"><i class="fa fa-shopping-cart"></i> <span>Orders Management</span></a></li>
              <li><a href="user"><i class="fa fa-users"></i> <span>Users Management</span></a></li>
           <li><a href="wproductdetails"><i class="fa fa-wrench" aria-hidden="true"></i><span>Product Management</span></a></li>
           <li class="treeview">
           		<a href="#"><i class="fa fa-exchange"></i> <span>Inventory Management</span><i class="fa fa-angle-left pull-right"></i></a>
              		<ul class="treeview-menu">
              			<li><a href="wproductinventorydetails?displaymode=0"><i class="fa fa-building-o"></i><span>Stock Management</span></a></li>
              			<li><a href="wproductrefillhistory"><i class="fa fa-clock-o"></i><span>Stock Refill History</span></a></li>
              		</ul>
            </li>
          <!--    <li class="treeview">
              <a href="order"><i class="fa fa-link"></i> <span>Orders Management</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li><a href="order">Orders History</a></li>
              </ul>
            </li> 
            
			<li class="treeview">
              <a href="user"><i class="fa fa-link"></i> <span>Users Management</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li><a href="user">User</a></li>
              </ul>
            </li>  -->
            <li><a href="enquiry"><i class="fa fa-envelope-o"></i> <span>Enquiries</span></a></li>
            <li><a href="customer-messaging"><i class="fa fa-envelope-o"></i> <span>Unregistered Customer <br/>Messaging</span></a></li>
			<li class="treeview">
              <a href="#"><i class="fa fa-life-ring"></i> <span>Masters</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li><a href="configuration"><i class="fa fa-cog"></i><span>Configuration Master</span></a></li>
                 <li><a href="categorymaster"><i class="fa fa-tags"></i><span>Category Master</span></a></li>
                 <li><a href="subcategorymaster"><i class="fa fa-tags"></i><span>SubCategory Master</span></a></li>
                <li><a href="packagingInfoMaster"><i class="fa fa-suitcase"></i><span>Packaging Info Master</span></a></li>
                <li><a href="locationdetails"><i class="fa fa-location-arrow"></i><span>Location Master</span></a></li>
                <li><a href="hsnDetails"><i class="fa fa-location-arrow"></i><span>HSN Master</span></a></li>
                <li><a href="courier-master"><i class="fa fa-location-arrow"></i><span>Courier Master</span></a></li>
                <li><a href="transportation-master"><i class="fa fa-location-arrow"></i><span>Transportation Master</span></a></li>
              </ul>
            </li>
			<!-- <li class="treeview">
              <a href="#"><i class="fa fa-link"></i> <span>Reports</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li><a href="#">Report One</a></li>
                <li><a href="#">Report Two</a></li>
              </ul>
            </li> -->
          </ul><!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
      </aside>