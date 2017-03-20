<?php include ('header1.php'); ?>
<div class="container">
	<div class="row">
			<div class="col-md-12">
			<br/>
				<h3 class="text-center">My Profile </h3>
					<div class="well"> 
					<div class="col-md-12">
					<span class="pull-right"><button type="button" class="btn btn-primary" style="padding:5px 20px;margin-right:10px;">Edit Profile</button></span>
					</div>
					
					<h5>Personal Information</h5>
						<div class="form-group row">
							<div class="col-md-6">
							<label>First Name</label>
							<input type="text" class="form-control" id="fname" disabled>
							</div>
							<div class="col-md-6">
							<label>Last Name</label>
							<input type="text" class="form-control" id="lname" disabled>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-md-6">
							<label>Email Address</label>
							<input type="email" class="form-control" id="email" disabled>
							</div>
							<div class="col-md-6">
							<label>Contact</label>
							<input type="number" class="form-control" id="contact" disabled>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-md-6">
							<label>VAT/TIN Number</label>
							<input type="text" class="form-control" id="vattin" disabled>
							</div>
							<div class="col-md-6">
							<label>PAN Number</label>
							<input type="text" class="form-control" id="pan" disabled>
							</div>
						</div>
					</div>
					<hr/>
					<div class="well"> 
					<div class="col-md-12">
					<span class="pull-right"><button type="button" class="btn btn-primary" style="padding:5px 20px;margin-right:10px;">Edit Address Information</button></span>
					</div>
					<h5>Address Information</h5>
						<div class="form-group row">
							<div class="col-md-6">
							<label>State</label>
							<input type="text" class="form-control" id="fname" disabled>
							</div>
							<div class="col-md-6">
							<label>City</label>
							<input type="text" class="form-control" id="lname" disabled>
							</div>
						</div>
						<div class="form-group row">
							
							<div class="col-md-12">
							  <label for="comment">Address</label>
								<textarea class="form-control" rows="5" id="comment" disabled></textarea>
							</div>
						</div>
						
					</div>
			</div>
	</div>
</div>
<?php include ('footer1.php'); ?>