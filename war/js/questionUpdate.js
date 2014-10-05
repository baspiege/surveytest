///////////////////
// Event handlers
///////////////////

function disableAddButton() {
  this.style.display='none';
  document.getElementById('updateButtonDisabled').style.display='inline';
}

///////////////////
// Set-up Page
///////////////////

function setUpPage() {
  
  // Update submit button
  var addButtonEnabled=document.getElementById("updateButtonEnabled");
  addButtonEnabled.onclick=disableAddButton;
}

setUpPage();