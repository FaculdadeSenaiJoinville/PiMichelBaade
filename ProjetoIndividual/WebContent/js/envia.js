function ValidaDados(){
	
	console.log(senhaembase64);
	//alert(document.frmLogin.senha.value);
	var senhaembase64 = btoa(document.frmLogin.senha.value);
	console.log(senhaembase64);
	document.frmLogin.senha.value = senhaembase64;
	//alert(document.frmLogin.senha.value);
	return true;
	
}


